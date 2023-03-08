package com.fhyj.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 日志表
 * @TableName base_log
 */
@TableName(value ="base_log")
@Data
public class BaseLog implements Serializable {
    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 日志类型
     */
    private Integer type;

    /**
     * 日志详细
     */
    private String detail;

    /**
     * 创建者
     */
    private Integer creator;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}