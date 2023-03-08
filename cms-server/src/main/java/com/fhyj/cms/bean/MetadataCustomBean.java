package com.fhyj.cms.bean;

import com.fhyj.cms.entity.DbMetadata;
import lombok.Getter;
import lombok.Setter;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Getter
@Setter
public class MetadataCustomBean extends DbMetadata {

    private String columnTypeName;//字段类型中文名

    private String columnSourceName;//字段来源中文名

    private String metaType;

    public MetadataCustomBean(Integer metaId, String columnName, String columnCName, Integer columnType, Integer columnSource, String columnTypeRule, String dataType, Integer columnLength, Integer formShow, Integer gridShow, Integer sorts, Integer mouldId, String defaultValue, Integer searchShow, String validExp, String metaType) {
        this.setId(metaId);
        this.setColumnName(columnName);
        this.setColumnChName(columnCName);
        this.setColumnType(columnType);
        this.setColumnSource(columnSource);
        this.setColumnTypeRule(columnTypeRule);
        this.setDataType(dataType);
        this.setColumnLength(columnLength);
        this.setFormShow(formShow);
        this.setGridShow(gridShow);
        this.setSorts(sorts);
        this.setMouldId(mouldId);
        this.setDefaultValue(defaultValue);
        this.setSearchShow(searchShow);
        this.setValidexp(validExp);
        this.setMetaType(metaType);
    }

}
