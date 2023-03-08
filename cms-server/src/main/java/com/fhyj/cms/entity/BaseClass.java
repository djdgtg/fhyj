package com.fhyj.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 分类表
 * @TableName base_class
 */
@TableName(value ="base_class")
@Data
public class BaseClass implements Serializable {
    /**
     * 分类ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 父分类
     */
    private Integer parentId;

    /**
     * 分类名
     */
    private String name;

    /**
     * 分类图片
     */
    private String imgUrl;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}