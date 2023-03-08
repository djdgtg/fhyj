package com.fhyj.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.Option;
import com.fhyj.cms.bean.OptionSearchBean;
import com.fhyj.cms.entity.BaseClass;
import com.fhyj.cms.entity.BaseManagerRole;
import com.fhyj.cms.entity.BaseMenu;
import com.fhyj.cms.entity.BaseRoleMenu;
import com.fhyj.cms.mapper.BaseClassMapper;
import com.fhyj.cms.mapper.BaseManagerRoleMapper;
import com.fhyj.cms.mapper.BaseMenuMapper;
import com.fhyj.cms.mapper.BaseRoleMenuMapper;
import com.fhyj.cms.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private BaseMenuMapper menusMapper;

    @Autowired
    private BaseManagerRoleMapper managerRoleMapper;

    @Autowired
    private BaseRoleMenuMapper roleMenuMapper;

    @Autowired
    private BaseClassMapper classMapper;

    @Override
    public ActionResult getMenuTreeOptionsByRole(OptionSearchBean optionSearchBean) {
        int roleId = optionSearchBean.getRoleId();
        int userId = optionSearchBean.getUserId();
        List<BaseMenu> menuslist = menusMapper.selectList(
                new LambdaQueryWrapper<BaseMenu>().isNotNull(BaseMenu::getId).orderByAsc(BaseMenu::getParentId));
        List<Option> menuTreeList = new ArrayList<>();
        if (menuslist != null && menuslist.size() > 0) {
            List<Integer> menuIds = new ArrayList<>();
            if (roleId > 0 || userId > 0) {
                List<Integer> roleIds = null;
                if (roleId > 0) {
                    roleIds = new ArrayList<>();
                    roleIds.add(roleId);
                } else {
                    List<BaseManagerRole> mgrolelist = managerRoleMapper.selectList(
                            new LambdaQueryWrapper<BaseManagerRole>().eq(BaseManagerRole::getUserId, userId));
                    if (mgrolelist != null && mgrolelist.size() > 0) {
                        roleIds = new ArrayList<>(mgrolelist.size());
                        int i = 0;
                        for (BaseManagerRole managerRole : mgrolelist) {
                            roleIds.add(i, managerRole.getRoleId());
                        }
                    }
                }
                List<BaseRoleMenu> rolemenulist = roleMenuMapper.selectList(new LambdaQueryWrapper<BaseRoleMenu>()
                        .in(BaseRoleMenu::getRoleId, roleIds));
                if (rolemenulist != null && rolemenulist.size() > 0) {
                    for (BaseRoleMenu roleMenu : rolemenulist)
                        menuIds.add(roleMenu.getMenuId());
                }
            }
            menuTreeList = setMenusChildren(0, menuslist, menuIds);
        }
        return ActionResult.ok(menuTreeList);
    }

    private List<Option> setMenusChildren(int parentOption, List<BaseMenu> list, List<Integer> rolemenuarr) {
        List<Option> menusChildren = new ArrayList<Option>();
        Option option;
        for (BaseMenu menu : list) {
            Integer menuId = menu.getId();// 获取菜单的id
            Integer parentId = menu.getParentId();// 获取菜单的父id
            if (parentId == parentOption) {
                option = new Option();
                option.setId(menu.getId());
                option.setText(menu.getName());
                option.setSelected(rolemenuarr.contains(menuId));
                menusChildren.add(option);

                List<Option> iterateMenu = setMenusChildren(menuId, list, rolemenuarr);
                if (iterateMenu.size() > 0) {
                    option.setChildren(iterateMenu);
                }
            }
        }
        return menusChildren;
    }

    @Override
    public ActionResult getClassTreeOptionsByParent(OptionSearchBean optionsSearchBean) {
        List<Option> clsTreeList = new ArrayList<>();
        int parentId = optionsSearchBean.getParentId();
        if (parentId < 0) {
            parentId = 0;
        }
        List<BaseClass> classlist = classMapper.selectList(new LambdaQueryWrapper<BaseClass>().orderByAsc(BaseClass::getParentId));
        if (classlist != null) {
            clsTreeList = setClsChildren(parentId, classlist);
        }
        if (optionsSearchBean.isWithNone()) {
            Option option = new Option();
            option.setId(-1);
            option.setText("--请选择--");
            clsTreeList.add(0, option);
        }
        return ActionResult.ok(clsTreeList);
    }

    private List<Option> setClsChildren(int parentId, List<BaseClass> list) {
        List<Option> clsChildren = new ArrayList<>();
        for (BaseClass cls : list) {
            Integer clsid = cls.getId();// 获取分类的id
            Integer pId = cls.getParentId();// 获取分类的父id
            if (pId == parentId) {
                Option option = new Option();
                option.setId(clsid);
                option.setText(cls.getName());

                List<Option> iterateCls = setClsChildren(clsid, list);
                if (iterateCls.size() > 0) {
                    option.setChildren(iterateCls);
                }
                clsChildren.add(option);
            }
        }
        return clsChildren;
    }

}
