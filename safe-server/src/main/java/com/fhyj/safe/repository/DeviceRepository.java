package com.fhyj.safe.repository;

import com.fhyj.safe.entity.Device;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/2/16 14:40
 */
public interface DeviceRepository extends ElasticsearchRepository<Device, Integer> {

    List<Device> findByDeviceNumberEqualsOrDeviceNameLike(String deviceNumber, String deviceName);

    @Query("{\n" +
            "  \"multi_match\": {\n" +
            "    \"query\": \"?0\",\n" +
            "    \"fields\": [\n" +
            "      \"deviceNumber\",\n" +
            "      \"deviceName\",\n" +
            "      \"description\"\n" +
            "    ]\n" +
            "  }\n" +
            "}")
    List<Device> find(String keyword);

    @Highlight(
            fields = {
                    @HighlightField(name = "deviceNumber"),
                    @HighlightField(name = "deviceName"),
                    @HighlightField(name = "description")
            },
            parameters = @HighlightParameters(preTags = "<span style='color:red'>", postTags = "</span>")
    )
    @Query("{\n" +
            "  \"multi_match\": {\n" +
            "    \"query\": \"?0\",\n" +
            "    \"fields\": [\n" +
            "      \"deviceNumber\",\n" +
            "      \"deviceName\",\n" +
            "      \"description\"\n" +
            "    ]\n" +
            "  }\n" +
            "}")
    SearchHits<Device> search(String keyword);

    @Highlight(
            fields = {
                    @HighlightField(name = "deviceNumber"),
                    @HighlightField(name = "deviceName"),
                    @HighlightField(name = "description")
            },
            parameters = @HighlightParameters(preTags = "<span style='color:red'>", postTags = "</span>")
    )
    @Query("{\n" +
            "  \"multi_match\": {\n" +
            "    \"query\": \"?0\",\n" +
            "    \"fields\": [\n" +
            "      \"deviceNumber\",\n" +
            "      \"deviceName\",\n" +
            "      \"description\"\n" +
            "    ]\n" +
            "  }\n" +
            "}")
    SearchPage<Device> searchPage(String keyword, Pageable pageable);

}
