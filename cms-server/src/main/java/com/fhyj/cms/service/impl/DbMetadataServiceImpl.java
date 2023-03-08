package com.fhyj.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.MetadataManageSearchBean;
import com.fhyj.cms.entity.DbMetadata;
import com.fhyj.cms.service.DbMetadataService;
import com.fhyj.cms.mapper.DbMetadataMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author djdgt
* @description 针对表【db_metadata(元数据表)】的数据库操作Service实现
* @createDate 2023-03-07 15:09:45
*/
@Service
public class DbMetadataServiceImpl extends ServiceImpl<DbMetadataMapper, DbMetadata>
    implements DbMetadataService{

    @Override
    public boolean checkUniqueness(DbMetadata metadata) {
        return false;
    }

    @Override
    public List<DbMetadata> getMetadataByField(MetadataManageSearchBean searchBean) {
        return null;
    }

    @Override
    public ActionResult listCustom(String mouldId) {
        return null;
    }

    @Override
    public ActionResult listByTableName(String tableName) {
        return null;
    }
}




