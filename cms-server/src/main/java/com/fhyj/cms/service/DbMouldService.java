package com.fhyj.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fhyj.cms.entity.DbMould;

/**
* @author djdgt
* @description 针对表【db_mould(模型表)】的数据库操作Service
* @createDate 2023-03-07 15:09:45
*/
public interface DbMouldService extends IService<DbMould> {

    boolean checkUniqueness(DbMould dbMould);
}
