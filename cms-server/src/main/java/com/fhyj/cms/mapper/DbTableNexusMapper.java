package com.fhyj.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fhyj.cms.entity.DbTableNexus;

import java.util.LinkedHashMap;
import java.util.List;

/**
* @author djdgt
* @description 针对表【db_table_nexus】的数据库操作Mapper
* @createDate 2023-03-07 15:09:45
* @Entity generator.entity.DbTableNexus
*/
public interface DbTableNexusMapper extends BaseMapper<DbTableNexus> {

    List<LinkedHashMap<String, Object>> getResList(String dbSql);
}




