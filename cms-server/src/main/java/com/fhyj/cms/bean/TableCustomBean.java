package com.fhyj.cms.bean;

import com.fhyj.cms.entity.DbTable;
import com.fhyj.cms.entity.DbTableNexus;
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
public class TableCustomBean extends DbTable {

    private String mouldName;//模型名

    private String mouldTypeName;//模型类型名

    private Integer mouldType;

    private List<DbTableNexus> tableNexuses;

    private String statusName;

    private String dataStepTypeName;

    private Integer parentMenuId;


}
