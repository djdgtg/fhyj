package com.fhyj.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.entity.BaseRoleMenu;

/**
 * @author djdgt
 * @description 针对表【base_role_menu(角色菜单表)】的数据库操作Service
 * @createDate 2023-03-07 15:09:45
 */
public interface BaseRoleMenuService extends IService<BaseRoleMenu> {

    ActionResult setRoleMenus(Integer roleId, String menuIds);

    ActionResult listTree(Integer roleId);
}
