package com.fhyj.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.CustomSearchBean;
import com.fhyj.cms.bean.LogManageSearchBean;
import com.fhyj.cms.entity.BaseLog;

/**
 * @author djdgt
 * @description 针对表【base_log(日志表)】的数据库操作Service
 * @createDate 2023-03-07 15:09:45
 */
public interface BaseLogService extends IService<BaseLog> {

    ActionResult logs(LogManageSearchBean logManageSearchBean, CustomSearchBean customSearchBean);
}
