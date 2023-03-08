package com.fhyj.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 模型表
 * @TableName db_mould
 */
@TableName(value ="db_mould")
@Data
public class DbMould implements Serializable {
    /**
     * 模型ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 模型名称
     */
    private String name;

    /**
     * 模型描述
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}