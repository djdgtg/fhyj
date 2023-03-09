package com.fhyj.cms.controller;

import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.entity.DbMould;
import com.fhyj.cms.service.DbMouldService;
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
@RequestMapping("/mould")
public class DbMouldController {

    @Autowired
    private DbMouldService mouldService;

    @RequestMapping("/list")
    @ResponseBody
    public ActionResult list() {
        return ActionResult.ok(mouldService.list());
    }

    @RequestMapping("/add")
    @ResponseBody
    public ActionResult add(DbMould dbMould) {
        if (mouldService.checkUniqueness(dbMould)) {
            return ActionResult.build(400, "该模型已存在,请输入其他模型名!");
        }
        mouldService.save(dbMould);
        return ActionResult.ok();

    }

    @RequestMapping("/update")
    @ResponseBody
    public ActionResult update(DbMould dbMould) {
        if (mouldService.checkUniqueness(dbMould)) {
            return ActionResult.build(400, "该模型已存在,请输入其他模型名!");
        }
        mouldService.updateById(dbMould);
        return ActionResult.ok();
    }

    @RequestMapping("/delBatch")
    @ResponseBody
    public ActionResult delBatch(String mouldIds) {
        List<String> ids = Arrays.asList(mouldIds.split(","));
        mouldService.removeBatchByIds(ids);
        return ActionResult.ok();
    }

}
