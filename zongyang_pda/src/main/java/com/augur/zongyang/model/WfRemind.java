package com.augur.zongyang.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yunhu on 2017-12-19.
 */

public class WfRemind implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -2092230526398968184L;
    private Long id;
    private String procInstId;
    private String activityName;
    private String reminder;
    private String reminderName;
    private Date remindDate;
    private String content;
    private String assignee;
    private String assigneeName;
    private String activityChineseName;
    private Long taskInstDbid;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcInstId() {
        return this.procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getReminder() {
        return this.reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public Date getRemindDate() {
        return this.remindDate;
    }

    public void setRemindDate(Date remindDate) {
        this.remindDate = remindDate;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReminderName() {
        return this.reminderName;
    }

    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    public String getAssignee() {
        return this.assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getAssigneeName() {
        return this.assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getActivityChineseName() {
        return this.activityChineseName;
    }

    public void setActivityChineseName(String activityChineseName) {
        this.activityChineseName = activityChineseName;
    }

    public Long getTaskInstDbid() {
        return this.taskInstDbid;
    }

    public void setTaskInstDbid(Long taskInstDbid) {
        this.taskInstDbid = taskInstDbid;
    }


}
