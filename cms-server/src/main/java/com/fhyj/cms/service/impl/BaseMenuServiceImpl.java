package com.fhyj.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.MenuCustomBean;
import com.fhyj.cms.entity.BaseMenu;
import com.fhyj.cms.mapper.BaseMenuMapper;
import com.fhyj.cms.service.BaseMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author djdgt
 * @description 针对表【base_menu(菜单表)】的数据库操作Service实现
 * @createDate 2023-03-07 15:09:45
 */
@Service
@AllArgsConstructor
public class BaseMenuServiceImpl extends ServiceImpl<BaseMenuMapper, BaseMenu>
        implements BaseMenuService {

    @Override
    public String getHtmlMenu(Integer userId) {
        List<MenuCustomBean> menuList = getBaseMapper().getRolesMenus(userId);//角色菜单列表
        List<MenuCustomBean> userMenu = new ArrayList<>();
        for (MenuCustomBean menusCustomBean : menuList) {
            if (menusCustomBean.getParentId() == 0) {
                userMenu.add(menusCustomBean);
            }
        }
        for (MenuCustomBean menusCustomBean : userMenu) {
            //递归生成菜单树
            menusCustomBean.setChildren(getChild(menusCustomBean.getId(), menuList));
        }
        //遍历菜单树,拼接html
        StringBuilder htmlMenu = new StringBuilder("<ul id='mainnav-menu' class='list-group'><li class='list-header'>导航菜单</li>");
        for (MenuCustomBean menu : userMenu) {
            if (menu.getChildren() == null) {
                htmlMenu.append("<li><a href=#><i></i><span class='menu-title'><strong>").append(menu.getName()).append("</strong></span></a></li>");
            } else {
                //递归获取子菜单树的html
                htmlMenu.append(getMenus(menu));
            }
        }
        htmlMenu.append("</ul>");
        return htmlMenu.toString();
    }

    private String getMenus(MenuCustomBean menuCustomBean) {
        StringBuilder html = new StringBuilder("<li><a href='#'><span class='menu-title'><strong>" + menuCustomBean.getName() + "</strong></span><i class='arrow'></i></a><ul class='collapse'>");
        List<MenuCustomBean> children = menuCustomBean.getChildren();
        for (MenuCustomBean menus : children) {
            if (menus.getChildren() == null) {
                if (menus.getPath() != null && !menus.getPath().equals("")) {
                    html.append("<li><a href='").append(menus.getPath()).append("' target='");
                } else {
                    html.append("<li><a href='#' target='");
                }
                if (menus.getIsOutLink()) {
                    html.append("_blank'>");
                } else {
                    html.append("_iframe'>");
                }
                html.append(menus.getName()).append("</a></li>");
            } else {
                html.append(getMenus(menus));
            }
        }
        html.append("</ul></li>");
        return html.toString();
    }

    private List<MenuCustomBean> getChild(Integer menuId, List<MenuCustomBean> menuList) {
        List<MenuCustomBean> children = new ArrayList<>();
        for (MenuCustomBean menusCustomBean : menuList) {
            if (menusCustomBean.getParentId() != null) {
                if (menusCustomBean.getParentId().equals(menuId)) {
                    children.add(menusCustomBean);
                }
            }
        }
        for (MenuCustomBean menusCustomBean : children) {
            if (!StringUtils.hasLength(menusCustomBean.getPath())) {
                menusCustomBean.setChildren(getChild(menusCustomBean.getId(), menuList));
            }
        }
        if (children.size() == 0) {
            return null;
        }
        return children;
    }

    @Override
    public ActionResult listCustom() {
        return null;
    }
}




