package com.fhyj.cms.controller;

import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.service.BaseRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Controller
@RequestMapping("/role/menu")
public class BaseRoleMenuController {

    @Autowired
    private BaseRoleMenuService roleMenuService;

    @RequestMapping("/list")
    @ResponseBody
    public ActionResult list(Integer roleId) {
        return roleMenuService.listTree(roleId);
    }

    @RequestMapping("/setRoleMenus")
    @ResponseBody
    public ActionResult setRoleMenus(Integer roleId, String menuIds) {
        return roleMenuService.setRoleMenus(roleId, menuIds);
    }

}
