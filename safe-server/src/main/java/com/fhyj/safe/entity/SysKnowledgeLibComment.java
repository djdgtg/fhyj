package com.fhyj.safe.entity;

import com.fhyj.common.mybatis.plus.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/3/1 11:18
 */
@Getter
@Setter
public class SysKnowledgeLibComment extends BaseEntity {

    private Integer KnowledgeLibId;
    private String content;
    private Integer parentId;

}
