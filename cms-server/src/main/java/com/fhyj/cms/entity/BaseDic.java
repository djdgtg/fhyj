package com.fhyj.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 字典表
 * @TableName base_dic
 */
@TableName(value ="base_dic")
@Data
public class BaseDic implements Serializable {
    /**
     * 字典ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 字典名
     */
    private String name;

    /**
     * 字典值
     */
    private String value;

    /**
     * 字典类型中文名
     */
    private String typeName;

    /**
     * 是否字典类型
     */
    private Boolean isDicType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}