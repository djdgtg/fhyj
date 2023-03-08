package com.fhyj.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.entity.DbTableNexus;

/**
* @author djdgt
* @description 针对表【db_table_nexus】的数据库操作Service
* @createDate 2023-03-07 15:09:45
*/
public interface DbTableNexusService extends IService<DbTableNexus> {

    ActionResult listCustom(Integer mouldId, Integer tableId);

    ActionResult listTableData(String nexusTable, String nexusField);
}
