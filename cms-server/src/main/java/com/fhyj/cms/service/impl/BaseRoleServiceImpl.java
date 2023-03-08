package com.fhyj.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhyj.cms.entity.BaseRole;
import com.fhyj.cms.service.BaseRoleService;
import com.fhyj.cms.mapper.BaseRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author djdgt
* @description 针对表【base_role(角色表)】的数据库操作Service实现
* @createDate 2023-03-07 15:09:45
*/
@Service
public class BaseRoleServiceImpl extends ServiceImpl<BaseRoleMapper, BaseRole>
    implements BaseRoleService{

    @Override
    public boolean checkUniqueness(BaseRole baseRole) {
        return false;
    }
}




