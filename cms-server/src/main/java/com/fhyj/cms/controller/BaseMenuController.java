package com.fhyj.cms.controller;

import com.fhyj.cms.aspect.LogAnnotation;
import com.fhyj.cms.aspect.LogType;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.entity.BaseManager;
import com.fhyj.cms.entity.BaseMenu;
import com.fhyj.cms.service.BaseMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Controller
@RequestMapping("/menu")
public class BaseMenuController {

    @Autowired
    private BaseMenuService menuService;

    @RequestMapping("/getRolesMenus")
    @ResponseBody
    public ActionResult getRolesMenus(HttpServletRequest request) {
        // 获取登录用户
        BaseManager loginUser = (BaseManager) request.getSession().getAttribute("USER_VALUE_OBJECT");
        // 执行查询
        String htmlMenu = menuService.getHtmlMenu(loginUser.getId());
        return ActionResult.ok(htmlMenu);
    }

    @RequestMapping("/listCustom")
    @ResponseBody
    public ActionResult listCustom() {
        return menuService.listCustom();
    }

    @RequestMapping("/add")
    @ResponseBody
    public ActionResult add(BaseMenu baseMenu) {
        menuService.save(baseMenu);
        return ActionResult.ok();
    }

    @RequestMapping("/update")
    @ResponseBody
    @LogAnnotation(type = LogType.OPERATION_3, detail = "更新菜单")
    public ActionResult update(BaseMenu baseMenu) {
        menuService.updateById(baseMenu);
        return ActionResult.ok();
    }

    @RequestMapping("/del")
    @ResponseBody
    @LogAnnotation(type = LogType.OPERATION_4, detail = "删除菜单")
    public ActionResult del(Integer menuId) {
        menuService.removeById(menuId);
        return ActionResult.ok();
    }

}
