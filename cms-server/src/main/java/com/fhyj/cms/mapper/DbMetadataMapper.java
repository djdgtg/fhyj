package com.fhyj.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fhyj.cms.bean.MetadataCustomBean;
import com.fhyj.cms.entity.DbMetadata;

import java.util.List;

/**
* @author djdgt
* @description 针对表【db_metadata(元数据表)】的数据库操作Mapper
* @createDate 2023-03-07 15:09:45
* @Entity generator.entity.DbMetadata
*/
public interface DbMetadataMapper extends BaseMapper<DbMetadata> {

    List<MetadataCustomBean> getMetadataCustomBean(Integer mouldId);
}




