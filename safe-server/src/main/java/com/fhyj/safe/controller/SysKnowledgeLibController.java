package com.fhyj.safe.controller;

import com.fhyj.safe.entity.SysKnowledgeLib;
import com.fhyj.safe.repository.SysKnowledgeLibRepository;
import com.fhyj.safe.mapper.SysKnowledgeLibCommentMapper;
import com.fhyj.safe.vo.CommentTree;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组件集成说明Controller
 *
 * @author fsmer
 * @date 2022-12-22
 */
@RestController
@RequestMapping("/sys/knowledgeLib")
@AllArgsConstructor
public class SysKnowledgeLibController {

    private final SysKnowledgeLibRepository knowledgeLibRepository;
    private final SysKnowledgeLibCommentMapper sysKnowledgeLibCommentMapper;

    /**
     * 搜索
     */
    @GetMapping("/search")
    public SearchPage<SysKnowledgeLib> search(String keyword,
                                              @RequestParam(required = false, defaultValue = "1") int pageNum,
                                              @RequestParam(required = false, defaultValue = "10") int pageSize) {
        // ES分页默认起始页是第0页
        return knowledgeLibRepository.search(keyword, PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Order.desc("id"))));
    }

    @GetMapping("/comments/{knowledgeLibId}")
    public List<CommentTree> comments(@PathVariable Long knowledgeLibId) {
        List<CommentTree> comments = sysKnowledgeLibCommentMapper.getComments(knowledgeLibId);
        return CommentTree.listToTree(comments);
    }

}
