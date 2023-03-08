package com.fhyj.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.entity.BaseRoleMenu;
import com.fhyj.cms.service.BaseRoleMenuService;
import com.fhyj.cms.mapper.BaseRoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author djdgt
* @description 针对表【base_role_menu(角色菜单表)】的数据库操作Service实现
* @createDate 2023-03-07 15:09:45
*/
@Service
public class BaseRoleMenuServiceImpl extends ServiceImpl<BaseRoleMenuMapper, BaseRoleMenu>
    implements BaseRoleMenuService{

    @Override
    public ActionResult setRoleMenus(Integer roleId, String menuIds) {
        return null;
    }

    @Override
    public ActionResult listTree(Integer roleId) {
        return null;
    }
}




