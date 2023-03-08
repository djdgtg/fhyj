package com.fhyj.cms.controller;

import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.QueryList;
import com.fhyj.cms.bean.TableManageSearchBean;
import com.fhyj.cms.service.PersonalService;
import com.fhyj.cms.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Controller
@RequestMapping("/personal")
public class PersonalController {

    @Autowired
    private PersonalService personalService;

    @RequestMapping("/uploadFile")
    @ResponseBody
    public ActionResult uploadFile(MultipartFile file) {
        return personalService.uploadFile(file);
    }

    @RequestMapping("/uploadKindEditor")
    @ResponseBody
    public String uploadKindEditor(MultipartFile file) {
        HashMap<String, Object> result = personalService.uploadKindEditor(file);
        return JsonUtils.objectToJson(result);
    }

    @RequestMapping("/importFile")
    @ResponseBody
    public ActionResult importFile(MultipartFile file, TableManageSearchBean searchBean, HttpServletRequest request) {
        return personalService.importFile(file, searchBean, request);
    }

    @RequestMapping("/updateResByFiledBatch")
    @ResponseBody
    public ActionResult updateResByFiledBatch(TableManageSearchBean searchBean, String fieldValue, String fieldName, HttpServletRequest request) {
        return personalService.updateResByFiledBatch(searchBean, fieldValue, fieldName, request);
    }


    @RequestMapping("/checkunique")
    @ResponseBody
    public String checkunique(String columnName, Integer id, Integer index, QueryList queryList, String tableName) {
        String value = queryList.getQueryList().get(index).getNameValue();
        boolean result = personalService.checkUnique(columnName, value, id, tableName);
        HashMap<String, Object> map = new HashMap<>();
        map.put("valid", !result);
        return JsonUtils.objectToJson(map);
    }

    @RequestMapping("/getFileServerUrl")
    @ResponseBody
    public String getFileServerUrl() {
        return personalService.getFileServerUrl();
    }
}
