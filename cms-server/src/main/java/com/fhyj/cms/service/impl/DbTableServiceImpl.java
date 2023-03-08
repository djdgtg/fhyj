package com.fhyj.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhyj.cms.bean.*;
import com.fhyj.cms.entity.DbTable;
import com.fhyj.cms.service.DbTableService;
import com.fhyj.cms.mapper.DbTableMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author djdgt
* @description 针对表【db_table(模型数据库表)】的数据库操作Service实现
* @createDate 2023-03-07 15:09:45
*/
@Service
public class DbTableServiceImpl extends ServiceImpl<DbTableMapper, DbTable>
    implements DbTableService{

    @Override
    public ActionResult listCustom(TableManageSearchBean searchBean) {
        return null;
    }

    @Override
    public ActionResult getSelfDataList(TableManageSearchBean searchBean, List<CustomQueryBean> queryList) {
        return null;
    }

    @Override
    public ActionResult updateStatus(String tableName, String status) {
        return null;
    }

    @Override
    public ActionResult updateEditableField(Integer id, String field, String value, String tableName, String mouldId, HttpServletRequest request) {
        return null;
    }

    @Override
    public ActionResult getSelfDbInfo(Integer id, String tableName) {
        return null;
    }

    @Override
    public boolean checkUniqueness(TableCustomBean customBean) {
        return false;
    }

    @Override
    public ActionResult addRes(TableManageSearchBean searchBean, QueryList queryList, HttpServletRequest request) {
        return null;
    }

    @Override
    public ActionResult updateRes(TableManageSearchBean searchBean, QueryList queryList, HttpServletRequest request) {
        return null;
    }

    @Override
    public ActionResult delResBatch(TableManageSearchBean searchBean, HttpServletRequest request) {
        return null;
    }
}




