package com.fhyj.cms.service;

import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.TableManageSearchBean;
import com.fhyj.cms.entity.DbMetadata;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
public interface PersonalService {

    ActionResult uploadFile(MultipartFile uploadFile);

    ActionResult importFile(MultipartFile file, TableManageSearchBean searchBean, HttpServletRequest request);

    void exportModelExcel(List<DbMetadata> list, HttpServletResponse response, HttpServletRequest request,
                          String tableChName, String type, String databaseName, String mouldId);

    ActionResult updateResByFiledBatch(TableManageSearchBean searchBean, String fieldValue, String fieldName, HttpServletRequest request);

    boolean checkUnique(String columnName, String value, Integer id, String tableName);

    HashMap<String, Object> uploadKindEditor(MultipartFile file);

    String getFileServerUrl();

}
