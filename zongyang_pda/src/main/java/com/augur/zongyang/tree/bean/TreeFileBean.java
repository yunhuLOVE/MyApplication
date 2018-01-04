package com.augur.zongyang.tree.bean;

/**
 * Created by yunhu on 2017-12-25.
 */

public class TreeFileBean {

    @TreeNodeId
    private String _id;
    @TreeNodePid
    private String parentId;
    @TreeNodeLabel
    private String name;
    @TreeNodeProjectId
    private String projectId;
    @TreeNodeItemId
    private String itemId;
    private long length;
    private String desc;

    public TreeFileBean(String _id, String parentId, String name,String projectId,String itemId)
    {
        super();
        this._id = _id;
        this.parentId = parentId;
        this.name = name;
        this.projectId = projectId;
        this.itemId = itemId;
    }
}
