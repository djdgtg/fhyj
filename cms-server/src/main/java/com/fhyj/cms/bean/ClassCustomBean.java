package com.fhyj.cms.bean;

import com.fhyj.cms.entity.BaseClass;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Getter
@Setter
public class ClassCustomBean extends BaseClass {
    private List<ClassCustomBean> children;
    private String parentName;

}