package com.fhyj.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.entity.BaseClass;

/**
* @author djdgt
* @description 针对表【base_class(分类表)】的数据库操作Service
* @createDate 2023-03-07 15:09:45
*/
public interface BaseClassService extends IService<BaseClass> {

    ActionResult listCustom();

    boolean checkUniqueness(BaseClass baseClass);

    ActionResult treeList();
}
