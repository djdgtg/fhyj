package com.fhyj.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fhyj.cms.entity.BaseDic;

/**
* @author djdgt
* @description 针对表【base_dic(字典表)】的数据库操作Mapper
* @createDate 2023-03-07 15:09:45
* @Entity generator.entity.BaseDic
*/
public interface BaseDicMapper extends BaseMapper<BaseDic> {

    int checkUniquenessByTypeOrName(BaseDic baseDic);

    int checkUniqueness(BaseDic baseDic);
}




