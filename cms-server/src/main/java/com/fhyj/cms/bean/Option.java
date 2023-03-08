package com.fhyj.cms.bean;

import java.util.List;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
public class Option {

    private int id;

    private String text;

    private boolean selected;

    private Object tag;

    private List<Option> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public List<Option> getChildren() {
        return children;
    }

    public void setChildren(List<Option> children) {
        this.children = children;
    }
}
