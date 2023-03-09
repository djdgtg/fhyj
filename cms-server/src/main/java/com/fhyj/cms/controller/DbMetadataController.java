package com.fhyj.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.MetadataManageSearchBean;
import com.fhyj.cms.entity.DbMetadata;
import com.fhyj.cms.service.DbMetadataService;
import com.fhyj.cms.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Controller
@RequestMapping("/metadata")
public class DbMetadataController {

    @Autowired
    private DbMetadataService metadataService;

    @Autowired
    private PersonalService personalService;

    @RequestMapping("/listCustom")
    @ResponseBody
    public ActionResult listCustom(String mouldId) {
        return metadataService.listCustom(mouldId);
    }

    @RequestMapping("/list")
    @ResponseBody
    public ActionResult list(Integer mouldId) {
        return ActionResult.ok(metadataService.list(new LambdaQueryWrapper<DbMetadata>().eq(DbMetadata::getMouldId, mouldId)));
    }

    @RequestMapping("/add")
    @ResponseBody
    public ActionResult add(DbMetadata metadata) {
        if (metadataService.checkUniqueness(metadata)) {
            return ActionResult.build(400, "该元数据已存在！");
        }
        metadataService.save(metadata);
        return ActionResult.ok();
    }

    @RequestMapping("/update")
    @ResponseBody
    public ActionResult update(DbMetadata metadata) {
        if (metadataService.checkUniqueness(metadata)) {
            return ActionResult.build(400, "该元数据已存在！");
        }
        metadataService.updateById(metadata);
        return ActionResult.ok();
    }

    @RequestMapping("/delBatch")
    @ResponseBody
    public ActionResult delBatch(String metadataIds) {
        List<String> ids = Arrays.asList(metadataIds.split(","));
        metadataService.removeBatchByIds(ids);
        return ActionResult.ok();
    }

    @RequestMapping("/listByTableName")
    @ResponseBody
    public ActionResult listByDbName(String tableName) {
        return metadataService.listByTableName(tableName);
    }

    @RequestMapping("/getMetadataByField")
    @ResponseBody
    public ActionResult getMetadataByField(MetadataManageSearchBean searchBean, String tableChName,
                                           String type, HttpServletRequest request, HttpServletResponse response) {
        List<DbMetadata> list = metadataService.getMetadataByField(searchBean);
        //如果是下载导入模板的请求 则输出一个文件过去
        if (StringUtils.hasLength(searchBean.getFieldName()) && searchBean.getFieldName().equals("ImportAndExportShow")) {
            personalService.exportModelExcel(list, response, request, tableChName, type, searchBean.getDatabaseName(), searchBean.getMouldId());
            return null;
        }
        return ActionResult.ok(list);
    }
}
