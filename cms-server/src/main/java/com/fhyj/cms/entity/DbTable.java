package com.fhyj.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 模型数据库表
 * @TableName db_table
 */
@TableName(value ="db_table")
@Data
public class DbTable implements Serializable {
    /**
     * 库ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 库英文名
     */
    private String tableName;

    /**
     * 库中文名
     */
    private String tableChName;

    /**
     * 库描述
     */
    private String description;

    /**
     * 模型ID
     */
    private Integer mouldId;

    /**
     * 状态
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}