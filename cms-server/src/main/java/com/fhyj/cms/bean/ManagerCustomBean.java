package com.fhyj.cms.bean;


import com.fhyj.cms.entity.BaseManager;
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
public class ManagerCustomBean extends BaseManager {

    private List<Integer> roleIds;

    private String roleIdsStr;

    private List<Integer> ids;

    private Integer roleId;

    private String roleName;

}
