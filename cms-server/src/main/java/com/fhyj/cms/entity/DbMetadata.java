package com.fhyj.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 元数据表
 * @TableName db_metadata
 */
@TableName(value ="db_metadata")
@Data
public class DbMetadata implements Serializable {
    /**
     * 字段ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 字段英文名
     */
    private String columnName;

    /**
     * 字段中文名
     */
    private String columnChName;

    /**
     * 字段类型
     */
    private Integer columnType;

    /**
     * 字段来源
     */
    private Integer columnSource;

    /**
     * 类型规则
     */
    private String columnTypeRule;

    /**
     * 字段长度
     */
    private Integer columnLength;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 表单显示
     */
    private Integer formShow;

    /**
     * 列表显示
     */
    private Integer gridShow;

    /**
     * 搜索显示
     */
    private Integer searchShow;

    /**
     * 导入导出显示
     */
    private Integer importExportShow;

    /**
     * 排序
     */
    private Integer sorts;

    /**
     * 模型ID
     */
    private Integer mouldId;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否为必填项
     */
    private Integer required;

    /**
     * 是否可修改列值
     */
    private Integer editable;

    /**
     * 
     */
    private Integer only;

    /**
     * 校验的正则表达式
     */
    private String validexp;

    /**
     * 校验错误的提示信息
     */
    private String validmsg;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}