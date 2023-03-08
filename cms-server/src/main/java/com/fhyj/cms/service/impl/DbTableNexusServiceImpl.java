package com.fhyj.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.entity.DbTableNexus;
import com.fhyj.cms.service.DbTableNexusService;
import com.fhyj.cms.mapper.DbTableNexusMapper;
import org.springframework.stereotype.Service;

/**
* @author djdgt
* @description 针对表【db_table_nexus】的数据库操作Service实现
* @createDate 2023-03-07 15:09:45
*/
@Service
public class DbTableNexusServiceImpl extends ServiceImpl<DbTableNexusMapper, DbTableNexus>
    implements DbTableNexusService{

    @Override
    public ActionResult listCustom(Integer mouldId, Integer tableId) {
        return null;
    }

    @Override
    public ActionResult listTableData(String nexusTable, String nexusField) {
        return null;
    }
}




