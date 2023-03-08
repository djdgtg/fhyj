package com.fhyj.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fhyj.cms.entity.BaseDic;

/**
* @author djdgt
* @description 针对表【base_dic(字典表)】的数据库操作Service
* @createDate 2023-03-07 15:09:45
*/
public interface BaseDicService extends IService<BaseDic> {

    boolean checkUniquenessByTypeOrName(BaseDic baseDic);

    boolean checkUniqueness(BaseDic baseDic);
}
