package com.fhyj.cms.bean;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
public class MetadataManageSearchBean {

    private String mouldId;

    private String metadataId;

    private String metadataIds;

    private String databaseName;

    private String databaseId;

    private String fieldName;

    private int fieldValue;

    public String getMouldId() {
        return mouldId;
    }

    public void setMouldId(String mouldId) {
        this.mouldId = mouldId;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getMetadataIds() {
        return metadataIds;
    }

    public void setMetadataIds(String metadataIds) {
        this.metadataIds = metadataIds;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(int fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

}
