package com.fhyj.cms.controller;

import com.fhyj.cms.aspect.LogAnnotation;
import com.fhyj.cms.aspect.LogType;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.entity.BaseRole;
import com.fhyj.cms.service.BaseRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Controller
@RequestMapping("/roles")
public class BaseRoleController {

    @Autowired
    private BaseRoleService roleService;

    @RequestMapping("/list")
    @ResponseBody
    @LogAnnotation(type = LogType.OPERATION_5, detail = "查询角色")
    public List<BaseRole> list() {
        return roleService.list();
    }

    @RequestMapping("/add")
    @ResponseBody
    @LogAnnotation(type = LogType.OPERATION_2, detail = "新增角色")
    public ActionResult add(BaseRole baseRole) {
        if (!roleService.checkUniqueness(baseRole)) {
            roleService.save(baseRole);
            return ActionResult.ok();
        } else {
            return ActionResult.build(400, "该角色已存在！");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    @LogAnnotation(type = LogType.OPERATION_3, detail = "更新角色")
    public ActionResult update(BaseRole baseRole) {
        if (!roleService.checkUniqueness(baseRole)) {
            roleService.updateById(baseRole);
            return ActionResult.ok();
        } else {
            return ActionResult.build(400, "该角色已存在！");
        }
    }

    @RequestMapping("/delBatch")
    @ResponseBody
    @LogAnnotation(type = LogType.OPERATION_2, detail = "删除角色")
    public ActionResult delBatch(String roleIds) {
        List<String> ids = Arrays.asList(roleIds.split(","));
        roleService.removeBatchByIds(ids);
        return ActionResult.ok();
    }

}
