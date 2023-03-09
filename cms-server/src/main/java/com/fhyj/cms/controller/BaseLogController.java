package com.fhyj.cms.controller;

import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.CustomSearchBean;
import com.fhyj.cms.bean.LogManageSearchBean;
import com.fhyj.cms.service.BaseLogService;
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
@RequestMapping("log")
public class BaseLogController {

    @Autowired
    private BaseLogService baseLogService;

    @RequestMapping("/list")
    @ResponseBody
    public ActionResult list(LogManageSearchBean logManageSearchBean, CustomSearchBean customSearchBean) {
        return baseLogService.logs(logManageSearchBean, customSearchBean);
    }
}
