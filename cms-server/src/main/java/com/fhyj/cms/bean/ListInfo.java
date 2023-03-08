package com.fhyj.cms.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Getter
@Setter
public class ListInfo<T> {

    private int length = 0;

    private List<T> list = new ArrayList<>();
}
