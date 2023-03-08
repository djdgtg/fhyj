package com.fhyj.cms.bean;

import java.util.List;

/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
public class Node {

    private String nodeId;    //树的节点Id，区别于数据库中保存的数据Id。

    private String text;   //节点名称

    private State state;

    private List<Node> nodes;    //子节点，可以用递归的方法读取

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

}