package com.fhyj.safe.controller;

import com.alibaba.excel.EasyExcel;
import com.fhyj.common.easyexcel.GeneralAnalysisEventListener;
import com.fhyj.safe.entity.Device;
import com.fhyj.safe.repository.DeviceRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/2/14 18:03
 */
@RestController
@RequestMapping("device")
@AllArgsConstructor
public class DeviceController {

    private final DeviceRepository deviceRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final MinioClient minioClient;

    @GetMapping("info/{id}")
    public Device info(@PathVariable Integer id) throws Throwable {
        if (id > 10) {
            throw new IllegalArgumentException("参数大于10");
        }
        return deviceRepository.findById(id).orElseThrow((Supplier<Throwable>) () -> new RuntimeException("当前设备未找到"));
    }

    @PostMapping("save")
    public Device save(@RequestBody Device device) {
        return deviceRepository.save(device);
    }

    @GetMapping("find")
    public List<Device> find(String deviceNumber, String deviceName) {
        return deviceRepository.findByDeviceNumberEqualsOrDeviceNameLike(deviceNumber, deviceName);
    }

    @GetMapping("export")
    public void export(String keyword, HttpServletResponse response) throws IOException {
        List<Device> list = deviceRepository.find(keyword);
        response.setContentType("application/octet-stream; charset=UTF-8");
        EasyExcel.write(response.getOutputStream()).sheet().doWrite(list);
    }

    @GetMapping("import")
    public void excelImport(MultipartFile file) throws IOException {
        EasyExcel
                .read(file.getInputStream())
                .registerReadListener(new GeneralAnalysisEventListener<Device>(deviceRepository::saveAll,
                        excelErrorMessages -> {
                            String filePath = "";
                            EasyExcel.write(filePath).sheet().doWrite(excelErrorMessages);
                            try {
                                minioClient.putObject(PutObjectArgs.builder().bucket("test").object(filePath).build());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }))
                .sheet().doRead();
    }

    @GetMapping("search")
    public SearchHits<Device> search(String keyword) {
        return deviceRepository.search(keyword);
    }

    @GetMapping("search/page/{page}/{size}")
    public SearchPage<Device> search(@PathVariable int page, @PathVariable int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createTime")));
        return deviceRepository.searchPage(keyword, pageable);
    }

    @GetMapping("page/{page}/{size}")
    public Page<Device> page(@PathVariable int page, @PathVariable int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.hasLength(keyword)) {
            //查询的字段
            boolQueryBuilder
                    .should(QueryBuilders.matchQuery("deviceName", keyword))
                    .should(QueryBuilders.matchQuery("deviceNumber", keyword))
                    .should(QueryBuilders.matchQuery("description", keyword));
        }
        //构建高亮查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                //排序
                .withSorts(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withQuery(boolQueryBuilder)
                //分页
                .withPageable(pageable)
                .withHighlightFields(
                        new HighlightBuilder.Field("deviceName"),
                        new HighlightBuilder.Field("deviceNumber"),
                        new HighlightBuilder.Field("description")
                )
                //高亮
                .withHighlightBuilder(new HighlightBuilder().preTags("<em>").postTags("</em>"))
                .build();
        //查询
        SearchHits<Device> search = elasticsearchRestTemplate.search(searchQuery, Device.class);
        //得到查询返回的内容
        List<SearchHit<Device>> searchHits = search.getSearchHits();
        //设置一个最后需要返回的实体类集合
        List<Device> posts = new ArrayList<>();
        //遍历返回的内容进行处理
        for (SearchHit<Device> searchHit : searchHits) {
            //高亮的内容
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            //将高亮的内容填充到content中
            searchHit.getContent().setDeviceName(highlightFields.get("deviceName") == null ? searchHit.getContent().getDeviceName() : highlightFields.get("deviceName").get(0));
            searchHit.getContent().setDeviceNumber(highlightFields.get("deviceNumber") == null ? searchHit.getContent().getDeviceName() : highlightFields.get("deviceNumber").get(0));
            searchHit.getContent().setDescription(highlightFields.get("description") == null ? searchHit.getContent().getDescription() : highlightFields.get("description").get(0));
            //放到实体类中
            posts.add(searchHit.getContent());
        }
        return new PageImpl<>(posts, pageable, search.getTotalHits());
    }
}
