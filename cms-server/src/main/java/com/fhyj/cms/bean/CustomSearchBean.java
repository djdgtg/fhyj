package com.fhyj.cms.bean;

import java.util.List;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
public class CustomSearchBean {

    private Integer start;

    private Integer length;

    private List<Order> order = null;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

}
