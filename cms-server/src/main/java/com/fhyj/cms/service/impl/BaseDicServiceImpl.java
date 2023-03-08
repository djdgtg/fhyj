package com.fhyj.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhyj.cms.entity.BaseDic;
import com.fhyj.cms.service.BaseDicService;
import com.fhyj.cms.mapper.BaseDicMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
* @author djdgt
* @description 针对表【base_dic(字典表)】的数据库操作Service实现
* @createDate 2023-03-07 15:09:45
*/
@Service
@AllArgsConstructor
public class BaseDicServiceImpl extends ServiceImpl<BaseDicMapper, BaseDic>
    implements BaseDicService{

    private final BaseDicMapper dicMapper;

    @Override
    public boolean checkUniquenessByTypeOrName(BaseDic baseDic) {
        return dicMapper.checkUniquenessByTypeOrName(baseDic)>0;
    }

    @Override
    public boolean checkUniqueness(BaseDic baseDic) {
        return dicMapper.checkUniqueness(baseDic)>0;
    }
}




