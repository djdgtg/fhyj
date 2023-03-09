package com.fhyj.cms.controller;

import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.entity.BaseClass;
import com.fhyj.cms.service.BaseClassService;
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
@RequestMapping("/class")
public class BaseClassController {

    @Autowired
    private BaseClassService classService;


    @RequestMapping("/listCustom")
    @ResponseBody
    public ActionResult listCustom() {
        return classService.listCustom();
    }

    @RequestMapping("/add")
    @ResponseBody
    public ActionResult add(BaseClass baseClass) {
        if (classService.checkUniqueness(baseClass)) {
            ActionResult.build(400, "该分类已存在！");
        }
        classService.save(baseClass);
        return ActionResult.ok();
    }

    @RequestMapping("/update")
    @ResponseBody
    public ActionResult update(BaseClass baseClass) {
        classService.updateById(baseClass);
        return ActionResult.ok();
    }

    @RequestMapping("/del")
    @ResponseBody
    public ActionResult del(Integer id) {
        classService.removeById(id);
        return ActionResult.ok();
    }

    @RequestMapping("/treeList")
    @ResponseBody
    public ActionResult treeList() {
        return classService.treeList();
    }

}
