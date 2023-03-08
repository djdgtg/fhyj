package com.fhyj.cms.bean;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
public class OptionSearchBean {

    private int userId;

    private int roleId;

    private int parentId;

    private boolean withNone;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public boolean isWithNone() {
        return withNone;
    }

    public void setWithNone(boolean withNone) {
        this.withNone = withNone;
    }

}
