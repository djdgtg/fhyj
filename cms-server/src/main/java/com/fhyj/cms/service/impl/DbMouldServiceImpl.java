package com.fhyj.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhyj.cms.entity.DbMould;
import com.fhyj.cms.service.DbMouldService;
import com.fhyj.cms.mapper.DbMouldMapper;
import org.springframework.stereotype.Service;

/**
* @author djdgt
* @description 针对表【db_mould(模型表)】的数据库操作Service实现
* @createDate 2023-03-07 15:09:45
*/
@Service
public class DbMouldServiceImpl extends ServiceImpl<DbMouldMapper, DbMould>
    implements DbMouldService{

    @Override
    public boolean checkUniqueness(DbMould dbMould) {
        return false;
    }
}




