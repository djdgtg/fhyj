package com.fhyj.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fhyj.cms.bean.*;
import com.fhyj.cms.entity.DbTable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author djdgt
* @description 针对表【db_table(模型数据库表)】的数据库操作Service
* @createDate 2023-03-07 15:09:45
*/
public interface DbTableService extends IService<DbTable> {

    ActionResult listCustom(TableManageSearchBean searchBean);

    ActionResult getSelfDataList(TableManageSearchBean searchBean, List<CustomQueryBean> queryList);

    ActionResult updateStatus(String tableName, String status);

    ActionResult updateEditableField(Integer id, String field, String value, String tableName, String mouldId, HttpServletRequest request);

    ActionResult getSelfDbInfo(Integer id, String tableName);

    boolean checkUniqueness(TableCustomBean customBean);

    ActionResult addRes(TableManageSearchBean searchBean, QueryList queryList, HttpServletRequest request);

    ActionResult updateRes(TableManageSearchBean searchBean, QueryList queryList, HttpServletRequest request);

    ActionResult delResBatch(TableManageSearchBean searchBean, HttpServletRequest request);
}
