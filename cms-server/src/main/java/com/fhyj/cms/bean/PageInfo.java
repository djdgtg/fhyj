package com.fhyj.cms.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Getter
@Setter
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = -1404774159374478734L;

    private Long total;

    private List<T> rows;


}
