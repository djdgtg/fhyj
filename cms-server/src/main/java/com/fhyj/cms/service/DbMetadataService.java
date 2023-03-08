package com.fhyj.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.MetadataManageSearchBean;
import com.fhyj.cms.entity.DbMetadata;

import java.util.List;

/**
* @author djdgt
* @description 针对表【db_metadata(元数据表)】的数据库操作Service
* @createDate 2023-03-07 15:09:45
*/
public interface DbMetadataService extends IService<DbMetadata> {

    boolean checkUniqueness(DbMetadata metadata);

    List<DbMetadata> getMetadataByField(MetadataManageSearchBean searchBean);

    ActionResult listCustom(String mouldId);

    ActionResult listByTableName(String tableName);
}
