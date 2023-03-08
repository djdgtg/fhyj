package com.fhyj.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 菜单表
 * @TableName base_menu
 */
@TableName(value ="base_menu")
@Data
public class BaseMenu implements Serializable {
    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 父菜单
     */
    private Integer parentId;

    /**
     * 路径
     */
    private String path;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 
     */
    private Boolean isOutLink;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}