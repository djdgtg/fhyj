package com.fhyj.cms.bean;

import com.fhyj.cms.entity.BaseMenu;
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
public class MenuCustomBean extends BaseMenu {

    private List<MenuCustomBean> children;

    private String parentMenuName;

}
