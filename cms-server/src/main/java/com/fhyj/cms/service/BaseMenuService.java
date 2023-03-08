package com.fhyj.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.entity.BaseMenu;

/**
* @author djdgt
* @description 针对表【base_menu(菜单表)】的数据库操作Service
* @createDate 2023-03-07 15:09:45
*/
public interface BaseMenuService extends IService<BaseMenu> {

    String getHtmlMenu(Integer userId);

    ActionResult listCustom();
}
