package com.fhyj.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fhyj.cms.bean.ClassCustomBean;
import com.fhyj.cms.entity.BaseClass;

import java.util.List;

/**
* @author djdgt
* @description 针对表【base_class(分类表)】的数据库操作Mapper
* @createDate 2023-03-07 15:09:45
* @Entity generator.entity.BaseClass
*/
public interface BaseClassMapper extends BaseMapper<BaseClass> {

    List<ClassCustomBean> listCustom();

    int checkUniqueness(BaseClass baseClass);
}




