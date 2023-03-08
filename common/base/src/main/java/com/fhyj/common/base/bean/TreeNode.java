package com.fhyj.common.base.bean;

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
public class TreeNode {

    private Integer id;

    private String name;

    private Integer parentId;

    private List<TreeNode> children;

    public static List<TreeNode> listToTreeNode(List<TreeNode> list) {
        List<TreeNode> treeList = new ArrayList<>();
        for (TreeNode tree : list) {
            // root节点没有父id
            if (tree.getId() == null) {
                // 用递归找子
                treeList.add(findChildren(tree, list));
            }
        }
        return treeList;
    }

    private static TreeNode findChildren(TreeNode tree, List<TreeNode> list) {
        for (TreeNode node : list) {
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
