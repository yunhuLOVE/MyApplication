package com.augur.zongyang.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yunhu on 2018-01-15.
 */

public class HistoryOpinionForm implements Serializable{

    private String id;
    private String activityChineseName;//环节名称
    private String assignee;//办理人
    private String handleComments;//办理意见
    private String signTime;//签收时间
    private String endDate;//结束时间
    private String status;//办理状态
    private String iconCls;
    private List<HistoryOpinionForm> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityChineseName() {
        return activityChineseName;
    }

    public void setActivityChineseName(String activityChineseName) {
        this.activityChineseName = activityChineseName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getHandleComments() {
        return handleComments;
    }

    public void setHandleComments(String handleComments) {
        this.handleComments = handleComments;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public List<HistoryOpinionForm> getChildren() {
        return children;
    }

    public void setChildren(List<HistoryOpinionForm> children) {
        this.children = children;
    }
}
