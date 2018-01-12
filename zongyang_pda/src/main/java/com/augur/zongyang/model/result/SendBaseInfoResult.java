package com.augur.zongyang.model.result;

import com.augur.zongyang.model.TaskDetailInfoModel;

import java.io.Serializable;

/**
 * 项目提交-获取发送相关数据
 * Created by yunhu on 2018-01-10.
 */

public class SendBaseInfoResult implements Serializable{

    private String assignee;
    private String assigneeName;
    private String destActivityChineseName;
    private String message;
    private String message1;
    private String procInstId;
    private TaskDetailInfoModel result;
    private boolean success;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getDestActivityChineseName() {
        return destActivityChineseName;
    }

    public void setDestActivityChineseName(String destActivityChineseName) {
        this.destActivityChineseName = destActivityChineseName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public TaskDetailInfoModel getResult() {
        return result;
    }

    public void setResult(TaskDetailInfoModel result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
