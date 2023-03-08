package com.fhyj.cms.bean;

import com.fhyj.cms.entity.BaseLog;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
public class LogCustomBean extends BaseLog {

    private String creatorName;

    private String logTypeName;

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getLogTypeName() {
        return logTypeName;
    }

    public void setLogTypeName(String logTypeName) {
        this.logTypeName = logTypeName;
    }
}
