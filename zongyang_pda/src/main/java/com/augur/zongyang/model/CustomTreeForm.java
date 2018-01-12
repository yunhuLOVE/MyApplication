package com.augur.zongyang.model;

/**
 * Created by yunhu on 2017-12-25.
 */

import java.io.Serializable;
import java.util.List;

/**
 * 自定义多级列表树form
 */
public class CustomTreeForm implements Serializable{

    private static final long serialVersionUID = 6047013658000048613L;

    private String id;

    private String name;

    private String fileType;

    private String fileFormat;

    private String memo1;

    private String  isCommon ;
    private String  isDeleted ;
    private String  code ;//节点CODe
    private String pcode;//父节点code
    private String  projectId ;//项目ID
    private Long isLeaf;
    private boolean  leaf ;//是否是叶子(末梢节点)
    private String  qtip ;
    private List<CustomTreeForm> children;
    private String text;
    private Integer treeLevel; //层级

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    public String getIsCommon() {
        return isCommon;
    }
    public void setIsCommon(String isCommon) {
        this.isCommon = isCommon;
    }
    public String getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getPcode() {
        return pcode;
    }
    public void setPcode(String pcode) {
        this.pcode = pcode;
    }
    public String getProjectId() {
        return projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Long getIsLeaf() {
        return isLeaf;
    }
    public void setIsLeaf(Long isLeaf) {
        this.isLeaf = isLeaf;
    }
    public boolean getLeaf() {
        return leaf;
    }
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    public String getQtip() {
        return qtip;
    }
    public void setQtip(String qtip) {
        this.qtip = qtip;
    }
    public List<CustomTreeForm> getChildren() {
        return children;
    }
    public void setChildren(List<CustomTreeForm> children) {
        this.children = children;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Integer getTreeLevel() {
        return treeLevel;
    }
    public void setTreeLevel(Integer treeLevel) {
        this.treeLevel = treeLevel;
    }




}
