package com.fhyj.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.QueryList;
import com.fhyj.cms.bean.TableCustomBean;
import com.fhyj.cms.bean.TableManageSearchBean;
import com.fhyj.cms.entity.DbTable;
import com.fhyj.cms.service.DbTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Controller
@RequestMapping("/table")
public class DbTableController {

    @Autowired
    private DbTableService tableService;

    @RequestMapping("/listCustom")
    @ResponseBody
    public ActionResult listCustom(TableManageSearchBean searchBean) {
        return tableService.listCustom(searchBean);
    }

    @RequestMapping("/list")
    @ResponseBody
    public ActionResult list(String mouldId) {
        return ActionResult.ok(tableService.list(new LambdaQueryWrapper<DbTable>().eq(DbTable::getMouldId, mouldId)));
    }

    @RequestMapping("/add")
    @ResponseBody
    public ActionResult add(TableCustomBean customBean) {
        if (tableService.checkUniqueness(customBean)) {
            return ActionResult.build(400, "该资源库已存在！");
        }
        tableService.save(customBean);
        return ActionResult.ok();
    }

    @RequestMapping("/update")
    @ResponseBody
    public ActionResult update(TableCustomBean customBean) {
        if (tableService.checkUniqueness(customBean)) {
            return ActionResult.build(400, "该资源库已存在！");
        }
        tableService.updateById(customBean);
        return ActionResult.ok();
    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    public ActionResult updateStatus(String tableName, String status) {
        return tableService.updateStatus(tableName, status);
    }

    @RequestMapping("/getSelfDataList")
    @ResponseBody
    public ActionResult getSelfDataList(TableManageSearchBean searchBean, QueryList queryList) {
        return tableService.getSelfDataList(searchBean, queryList.getQueryList());
    }

    @RequestMapping("/updateEditableField")
    @ResponseBody
    public ActionResult updateEditableField(Integer id, String field, String value,
                                            String tableName, String mouldId, HttpServletRequest request) {
        return tableService.updateEditableField(id, field, value, tableName, mouldId, request);
    }

    @RequestMapping("/getSelfDbInfo")
    @ResponseBody
    public ActionResult getSelfDbInfo(Integer id, String tableName) {
        return tableService.getSelfDbInfo(id, tableName);
    }

    @RequestMapping("/addRes")
    @ResponseBody
    public ActionResult addRes(TableManageSearchBean searchBean, QueryList queryList, HttpServletRequest request) throws Exception {
        return tableService.addRes(searchBean, queryList, request);
    }

    @RequestMapping("/updateRes")
    @ResponseBody
    public ActionResult updateRes(TableManageSearchBean searchBean, QueryList queryList, HttpServletRequest request) throws Exception {
        return tableService.updateRes(searchBean, queryList, request);
    }


    @RequestMapping("/delResBatch")
    @ResponseBody
    public ActionResult delResBatch(TableManageSearchBean searchBean, HttpServletRequest request) {
        return tableService.delResBatch(searchBean, request);
    }

    @RequestMapping("/delBatch")
    @ResponseBody
    public ActionResult delBatch(String tableIds) {
        List<String> ids = Arrays.asList(tableIds.split(","));
        tableService.removeBatchByIds(ids);
        return ActionResult.ok();
    }

}
