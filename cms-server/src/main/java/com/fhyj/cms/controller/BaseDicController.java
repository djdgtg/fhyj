package com.fhyj.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.entity.BaseDic;
import com.fhyj.cms.service.BaseDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Controller
@RequestMapping("/dic")
public class BaseDicController {

    @Autowired
    private BaseDicService dicService;

    @RequestMapping("/list")
    @ResponseBody
    public ActionResult list(BaseDic baseDic) {
        LambdaQueryWrapper<BaseDic> wrapper = new LambdaQueryWrapper<>();
        if (baseDic.getIsDicType() != null) {
            wrapper.eq(BaseDic::getIsDicType, baseDic.getIsDicType());
        }
        if (baseDic.getName() != null) {
            wrapper.eq(BaseDic::getName, baseDic.getName());
        }
        if (baseDic.getType() != null) {
            wrapper.eq(BaseDic::getType, baseDic.getType());
        }
        if (baseDic.getValue() != null) {
            wrapper.eq(BaseDic::getValue, baseDic.getType());
        }
        if (baseDic.getId() != null) {
            wrapper.eq(BaseDic::getId, baseDic.getId());
        }
        return ActionResult.ok(dicService.list(wrapper));
    }

    @RequestMapping("/add")
    @ResponseBody
    public ActionResult add(BaseDic baseDic) {
        if (dicService.checkUniquenessByTypeOrName(baseDic)) {
            return ActionResult.build(400, "该字典已存在！");
        }
        dicService.save(baseDic);
        return ActionResult.ok();
    }

    @RequestMapping("/update")
    @ResponseBody
    public ActionResult update(BaseDic baseDic) {
        if (dicService.checkUniqueness(baseDic)) {
            return ActionResult.build(400, "该字典已存在！");
        }
        dicService.updateById(baseDic);
        return ActionResult.ok();
    }

    @RequestMapping("/del")
    @ResponseBody
    public ActionResult del(Integer id) {
        dicService.removeById(id);
        return ActionResult.ok();
    }

    @RequestMapping("/delBatch")
    @ResponseBody
    public ActionResult delBatch(String dicIds) {
        List<String> ids = Arrays.asList(dicIds.split(","));
        dicService.removeBatchByIds(ids);
        return ActionResult.ok();
    }

}
