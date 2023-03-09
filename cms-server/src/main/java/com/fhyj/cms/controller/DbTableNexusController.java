package com.fhyj.cms.controller;

import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.service.DbTableNexusService;
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
@RequestMapping("/table/nexus")
public class DbTableNexusController {

    @Autowired
    private DbTableNexusService tableNexusService;

    @RequestMapping("/listTableData")
    @ResponseBody
    public ActionResult listTableData(String nexusTable, String nexusField) {
        return tableNexusService.listTableData(nexusTable, nexusField);
    }

    @RequestMapping("/listCustom")
    @ResponseBody
    public ActionResult listCustom(Integer mouldId, Integer tableId) {
        return tableNexusService.listCustom(mouldId, tableId);
    }

}
