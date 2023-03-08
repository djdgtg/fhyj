package com.fhyj.cms.bean;

/**
 * @author qinc
 * @date 2018/12/17
 */
public class TreeNode {
    private Integer id;

    private String text;

    private String state;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
