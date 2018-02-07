package com.augur.zongyang.model;

/**
 * Created by yunhu on 2018-01-29.
 */

public class ReceiveForm {

    private Long is;
    private String subProcessflag;
    private Long projectId;
    private String taskCode;
    private String taskName;
    private String handler;
    private String createDate;
    private String createBy;
    private String handlingDetail;
    private String processResult;
    private Long taskInstanceId;
    private Long handlerDays;//办理期限
    private String taskType;

    public Long getIs() {
        return is;
    }

    public void setIs(Long is) {
        this.is = is;
    }

    public String getSubProcessflag() {
        return subProcessflag;
    }

    public void setSubProcessflag(String subProcessflag) {
        this.subProcessflag = subProcessflag;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getHandlingDetail() {
        return handlingDetail;
    }

    public void setHandlingDetail(String handlingDetail) {
        this.handlingDetail = handlingDetail;
    }

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }

    public Long getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(Long taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }

    public Long getHandlerDays() {
        return handlerDays;
    }

    public void setHandlerDays(Long handlerDays) {
        this.handlerDays = handlerDays;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
