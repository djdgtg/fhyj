package com.fhyj.cms.service;

import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.OptionSearchBean;

/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
public interface OptionService {

    ActionResult getMenuTreeOptionsByRole(OptionSearchBean optionSearchBean);

    ActionResult getClassTreeOptionsByParent(OptionSearchBean optionSearchBean);

}
