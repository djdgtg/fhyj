package com.fhyj.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fhyj.cms.entity.BaseRole;

/**
* @author djdgt
* @description 针对表【base_role(角色表)】的数据库操作Service
* @createDate 2023-03-07 15:09:45
*/
public interface BaseRoleService extends IService<BaseRole> {

    boolean checkUniqueness(BaseRole baseRole);
}
