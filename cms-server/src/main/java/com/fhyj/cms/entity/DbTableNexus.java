package com.fhyj.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName db_table_nexus
 */
@TableName(value ="db_table_nexus")
@Data
public class DbTableNexus implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String nexusTable;

    /**
     * 
     */
    private String nexusField;

    /**
     * 
     */
    private String nexusFromTable;

    /**
     * 
     */
    private String nexusFromField;

    /**
     * 
     */
    private String nexusFromValueField;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public String getDbSql() {
        return "";
    }
}