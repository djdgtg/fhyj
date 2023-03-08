package com.fhyj.cms.bean;

import java.util.ArrayList;
import java.util.List;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
public class TreeBean {

    private String text;

    private Integer id;

    private Integer parentId;

    private List<TreeBean> nodes;

    private List<TreeBean> children;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TreeBean> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeBean> nodes) {
        this.nodes = nodes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<TreeBean> getChildren() {
        return children;
    }

    public void setChildren(List<TreeBean> children) {
        this.children = children;
    }

    //多级菜单查询方法
    public static List<TreeBean> iterateMenus(List<TreeBean> menuVoList, Integer pid) {
        List<TreeBean> result = new ArrayList<>();
        for (TreeBean menuVo : menuVoList) {
            Integer menuId = menuVo.getId();//获取菜单的id
            Integer parentId = menuVo.getParentId();//获取菜单的父id
            if (parentId.equals(pid)) {
                List<TreeBean> iterateMenu = iterateMenus(menuVoList, menuId);
                if (iterateMenu.size() > 0) {
                    menuVo.setNodes(iterateMenu);
                    menuVo.setChildren(iterateMenu);
                }
                result.add(menuVo);
            }
        }
        return result;
    }

    public static void iteraterMenus(List<TreeBean> parentClassList, List<TreeBean> childClassList) {
        for (TreeBean menuVo : parentClassList) {
            List<TreeBean> iterateMenus = TreeBean.iterateMenus(childClassList, menuVo.getId());
            if (iterateMenus != null && iterateMenus.size() > 0) {
                menuVo.setNodes(iterateMenus);
                menuVo.setChildren(iterateMenus);
            }
        }
    }
}
