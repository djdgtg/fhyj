package com.fhyj.safe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fhyj.safe.entity.SysKnowledgeLibComment;
import com.fhyj.safe.vo.CommentTree;

import java.util.List;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/3/1 11:20
 */
public interface SysKnowledgeLibCommentMapper extends BaseMapper<SysKnowledgeLibComment> {
    List<CommentTree> getComments(Long knowledgeLibId);
}
