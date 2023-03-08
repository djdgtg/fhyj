package com.fhyj.safe.vo;

import com.fhyj.safe.entity.SysKnowledgeLibComment;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/3/1 9:21
 */

@Getter
@Setter
public class CommentTree extends SysKnowledgeLibComment {

    private List<CommentTree> children;

    public static List<CommentTree> listToTree(List<CommentTree> list) {
        List<CommentTree> treeList = new ArrayList<>();
        for (CommentTree tree : list) {
            // root节点没有父id
            if (tree.getParentId() == null) {
                // 用递归找子
                treeList.add(findChildren(tree, list));
            }
        }
        return treeList;
    }

    private static CommentTree findChildren(CommentTree tree, List<CommentTree> list) {
        for (CommentTree node : list) {
            if (Objects.equals(node.getParentId(), tree.getId())) {
                if (tree.getChildren() == null) {
                    tree.setChildren(new ArrayList<>());
                }
                tree.getChildren().add(findChildren(node, list));
            }
        }
        return tree;
    }
}
