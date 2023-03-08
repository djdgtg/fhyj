package com.fhyj.cms.bean;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
public class TableManageSearchBean extends CustomSearchBean {

    private String databaseId;

    private String databaseName;

    private String databaseCName;

    private String mouldId;

    private String status;

    private String isWare;

    private String dataStatus;

    private String databaseIds;

    private int userId;

    private String seqId;

    private String resIds;

    public String getMouldId() {
        return mouldId;
    }

    public void setMouldId(String mouldId) {
        this.mouldId = mouldId;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseCName() {
        return databaseCName;
    }

    public void setDatabaseCName(String databaseCName) {
        this.databaseCName = databaseCName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsWare() {
        return isWare;
    }

    public void setIsWare(String isWare) {
        this.isWare = isWare;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getDatabaseIds() {
        return databaseIds;
    }

    public void setDatabaseIds(String databaseIds) {
        this.databaseIds = databaseIds;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getResIds() {
        return resIds;
    }

    public void setResIds(String resIds) {
        this.resIds = resIds;
    }
}
