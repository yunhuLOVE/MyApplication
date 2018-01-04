package com.augur.zongyang.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yunhu on 2017-12-19.
 */

public class TaskDetailInfoModel implements Serializable{

    private String activityName;//当前节点名称   ""
    private String activityChineseName;//当前环节
    private String agent;//任务代理人
    private String agentName;//代
    private String agentStartDate;//代理开始时间
    private String agentEndDate;//'代理结束时间
    private String assignee;//任务参与者'(签收)
    private String assigneeName;

    private String busDate1;
    private String busDate2;
    private String busDate3;
    private String busDate4;
    private String busMemo1;//
    private String busMemo2;//
    private String busMemo3;//
    private String busMemo4;//
    private String busMemo5;//
    private String busMemo6;//
    private String busMemo7;//
    private String busMemo8;//
    private String busMemo9;//
    private Long busNum1;//
    private Long busNum2;//

    private String create;
    private String createStartDate;
    private String createEndDate;

    private String duedate;//超时

    private String end;
    private String endDateFrom;
    private String endDateTo;
    private String groupByProperty;
    private String keyWord;
    private String masterEntity;
    private Long masterEntityKey;

    private String procStartdate;
    private String procDuedate;
    private String procEnddate;

    private String procInstId;
    private String procInstState;


    private List<WfRemind> remindList;

    private String signTime;//签收时间
    private Long taskInstDbid;//任务实例ID

    private Long templateId;
    private String templateCode;
    private String templateName;//审批流程'
    private String templateTypeName;//审批流程类别
    private Long wfBusInstanceId;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityChineseName() {
        return activityChineseName;
    }

    public void setActivityChineseName(String activityChineseName) {
        this.activityChineseName = activityChineseName;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentStartDate() {
        return agentStartDate;
    }

    public void setAgentStartDate(String agentStartDate) {
        this.agentStartDate = agentStartDate;
    }

    public String getAgentEndDate() {
        return agentEndDate;
    }

    public void setAgentEndDate(String agentEndDate) {
        this.agentEndDate = agentEndDate;
    }

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

    public String getBusDate1() {
        return busDate1;
    }

    public void setBusDate1(String busDate1) {
        this.busDate1 = busDate1;
    }

    public String getBusDate2() {
        return busDate2;
    }

    public void setBusDate2(String busDate2) {
        this.busDate2 = busDate2;
    }

    public String getBusDate3() {
        return busDate3;
    }

    public void setBusDate3(String busDate3) {
        this.busDate3 = busDate3;
    }

    public String getBusDate4() {
        return busDate4;
    }

    public void setBusDate4(String busDate4) {
        this.busDate4 = busDate4;
    }

    public String getBusMemo1() {
        return busMemo1;
    }

    public void setBusMemo1(String busMemo1) {
        this.busMemo1 = busMemo1;
    }

    public String getBusMemo2() {
        return busMemo2;
    }

    public void setBusMemo2(String busMemo2) {
        this.busMemo2 = busMemo2;
    }

    public String getBusMemo3() {
        return busMemo3;
    }

    public void setBusMemo3(String busMemo3) {
        this.busMemo3 = busMemo3;
    }

    public String getBusMemo4() {
        return busMemo4;
    }

    public void setBusMemo4(String busMemo4) {
        this.busMemo4 = busMemo4;
    }

    public String getBusMemo5() {
        return busMemo5;
    }

    public void setBusMemo5(String busMemo5) {
        this.busMemo5 = busMemo5;
    }

    public String getBusMemo6() {
        return busMemo6;
    }

    public void setBusMemo6(String busMemo6) {
        this.busMemo6 = busMemo6;
    }

    public String getBusMemo7() {
        return busMemo7;
    }

    public void setBusMemo7(String busMemo7) {
        this.busMemo7 = busMemo7;
    }

    public String getBusMemo8() {
        return busMemo8;
    }

    public void setBusMemo8(String busMemo8) {
        this.busMemo8 = busMemo8;
    }

    public String getBusMemo9() {
        return busMemo9;
    }

    public void setBusMemo9(String busMemo9) {
        this.busMemo9 = busMemo9;
    }

    public Long getBusNum1() {
        return busNum1;
    }

    public void setBusNum1(Long busNum1) {
        this.busNum1 = busNum1;
    }

    public Long getBusNum2() {
        return busNum2;
    }

    public void setBusNum2(Long busNum2) {
        this.busNum2 = busNum2;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getCreateStartDate() {
        return createStartDate;
    }

    public void setCreateStartDate(String createStartDate) {
        this.createStartDate = createStartDate;
    }

    public String getCreateEndDate() {
        return createEndDate;
    }

    public void setCreateEndDate(String createEndDate) {
        this.createEndDate = createEndDate;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEndDateFrom() {
        return endDateFrom;
    }

    public void setEndDateFrom(String endDateFrom) {
        this.endDateFrom = endDateFrom;
    }

    public String getEndDateTo() {
        return endDateTo;
    }

    public void setEndDateTo(String endDateTo) {
        this.endDateTo = endDateTo;
    }

    public String getGroupByProperty() {
        return groupByProperty;
    }

    public void setGroupByProperty(String groupByProperty) {
        this.groupByProperty = groupByProperty;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getMasterEntity() {
        return masterEntity;
    }

    public void setMasterEntity(String masterEntity) {
        this.masterEntity = masterEntity;
    }

    public Long getMasterEntityKey() {
        return masterEntityKey;
    }

    public void setMasterEntityKey(Long masterEntityKey) {
        this.masterEntityKey = masterEntityKey;
    }

    public String getProcDuedate() {
        return procDuedate;
    }

    public void setProcDuedate(String procDuedate) {
        this.procDuedate = procDuedate;
    }

    public String getProcEnddate() {
        return procEnddate;
    }

    public void setProcEnddate(String procEnddate) {
        this.procEnddate = procEnddate;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getProcInstState() {
        return procInstState;
    }

    public void setProcInstState(String procInstState) {
        this.procInstState = procInstState;
    }

    public String getProcStartdate() {
        return procStartdate;
    }

    public void setProcStartdate(String procStartdate) {
        this.procStartdate = procStartdate;
    }

    public List<WfRemind> getRemindList() {
        return remindList;
    }

    public void setRemindList(List<WfRemind> remindList) {
        this.remindList = remindList;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public Long getTaskInstDbid() {
        return taskInstDbid;
    }

    public void setTaskInstDbid(Long taskInstDbid) {
        this.taskInstDbid = taskInstDbid;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateTypeName() {
        return templateTypeName;
    }

    public void setTemplateTypeName(String templateTypeName) {
        this.templateTypeName = templateTypeName;
    }

    public Long getWfBusInstanceId() {
        return wfBusInstanceId;
    }

    public void setWfBusInstanceId(Long wfBusInstanceId) {
        this.wfBusInstanceId = wfBusInstanceId;
    }
}
