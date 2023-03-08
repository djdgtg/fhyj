package com.fhyj.safe.entity;

import com.fhyj.common.mybatis.plus.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 设备对象 device
 *
 * @author fsmer
 * @since 2023-02-20
 */

@Data
@Document(indexName = "safe_device", createIndex = false)
public class Device extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 设备编号
     */
    @Field(type = FieldType.Text)
    private String deviceNumber;

    /**
     * 设备名称
     */
    @Field(type = FieldType.Text)
    private String deviceName;

    /**
     * 设备类型id
     */
    @Field(type = FieldType.Integer)
    private Integer deviceTypeId;

    /**
     * 设备描述
     */
    @Field(analyzer = "ik_smart") //ik_smart和ik_max_word，其中ik smart为最少切分，ik_max_word为最细粒度划分
    private String description;


}
