package com.fhyj.cms.controller;

import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.OptionSearchBean;
import com.fhyj.cms.service.OptionService;
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
@RequestMapping("/option")
public class OptionController {

    @Autowired
    private OptionService optionService;

    @RequestMapping("/getMenuTreeOptionsByRole")
    @ResponseBody
    public ActionResult getMenuTreeOptionsByRole(OptionSearchBean optionsSearchBean) {
        return optionService.getMenuTreeOptionsByRole(optionsSearchBean);
    }

    @RequestMapping("/getClassTreeOptionsByParent")
    @ResponseBody
    public ActionResult getClassTreeOptionsByParent(OptionSearchBean optionsSearchBean) {
        return optionService.getClassTreeOptionsByParent(optionsSearchBean);
    }

}

