package com.fhyj.safe.entity;

import com.fhyj.common.mybatis.plus.BaseEntity;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 组件集成说明对象 sys_knowledge_lib
 *
 * @author fsmer
 * @since 2022-12-22
 */

@Data
@Document(indexName = "fhyj-aswift-sys-knowledge-lib", createIndex = false)
public class SysKnowledgeLib extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 图表
     */
    private String iconUrl;

    /**
     * 名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String title;

    /**
     * 介绍
     */
    private String introduce;

    /**
     * 租户id
     */
    private Long tenantId;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String keyWords;

}
