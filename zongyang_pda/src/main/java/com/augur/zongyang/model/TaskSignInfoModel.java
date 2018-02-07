package com.augur.zongyang.model;

/**
 * 签收信息
 * Created by yunhu on 2018-01-29.
 */

public class TaskSignInfoModel {
    private String viewName;
    private Integer version;
    private String processDefId;
    private Long nextTaskInstanceDbid;
    private String reassignRangeType;

    private String destActivityName;
    private String destActivityChineseName;
    private boolean isDirectSend = false;
    private boolean needSelectAssignee = false;
    private String defaultSendAssignees;
    private String message;
    private String smsRemind;
    private String mailRemind;

    private Long dbversion;
    private String name;
    private String state;
    private String susphiststate;
    private String form;
    private Long priority;
    private Long progress;
    private Long signalling;
    private String executionId;
    private Long hasvars;
    private Long supertask;
    private Long execution;
    private Long procinst;
    private Long swimlane;
    private String taskdefname;
    private String handleComments;
    private String handleCommentsTime;
    private String isMainTask;
    private String taskType;
    private String reassign;
    private String reassignName;
    private String reassignComments;
    private String reassignCommentsTime;

    private Long taskInstDbid;
    private String assignee;
    private String assigneeName;
    private String dueString;
    private String create;
    private String end;
    private String signTime;
    private String agent;
    private String agentName;
    private String agentStartString;
    private String agentEndString;
    private String activityName;
    private String activityChineseName;


    private Long wfBusInstanceId;
    private String masterEntity;
    private Long masterEntityKey;
    private String procInstId;
    private Long templateId;
    private String templateCode;
    private String templateName;
    private String templateTypeName;
    private String procInstState;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getProcessDefId() {
        return processDefId;
    }

    public void setProcessDefId(String processDefId) {
        this.processDefId = processDefId;
    }

    public Long getNextTaskInstanceDbid() {
        return nextTaskInstanceDbid;
    }

    public void setNextTaskInstanceDbid(Long nextTaskInstanceDbid) {
        this.nextTaskInstanceDbid = nextTaskInstanceDbid;
    }

    public String getReassignRangeType() {
        return reassignRangeType;
    }

    public void setReassignRangeType(String reassignRangeType) {
        this.reassignRangeType = reassignRangeType;
    }

    public String getDestActivityName() {
        return destActivityName;
    }

    public void setDestActivityName(String destActivityName) {
        this.destActivityName = destActivityName;
    }

    public String getDestActivityChineseName() {
        return destActivityChineseName;
    }

    public void setDestActivityChineseName(String destActivityChineseName) {
        this.destActivityChineseName = destActivityChineseName;
    }

    public boolean isDirectSend() {
        return isDirectSend;
    }

    public void setDirectSend(boolean directSend) {
        isDirectSend = directSend;
    }

    public boolean isNeedSelectAssignee() {
        return needSelectAssignee;
    }

    public void setNeedSelectAssignee(boolean needSelectAssignee) {
        this.needSelectAssignee = needSelectAssignee;
    }

    public String getDefaultSendAssignees() {
        return defaultSendAssignees;
    }

    public void setDefaultSendAssignees(String defaultSendAssignees) {
        this.defaultSendAssignees = defaultSendAssignees;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSmsRemind() {
        return smsRemind;
    }

    public void setSmsRemind(String smsRemind) {
        this.smsRemind = smsRemind;
    }

    public String getMailRemind() {
        return mailRemind;
    }

    public void setMailRemind(String mailRemind) {
        this.mailRemind = mailRemind;
    }

    public Long getDbversion() {
        return dbversion;
    }

    public void setDbversion(Long dbversion) {
        this.dbversion = dbversion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSusphiststate() {
        return susphiststate;
    }

    public void setSusphiststate(String susphiststate) {
        this.susphiststate = susphiststate;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Long getProgress() {
        return progress;
    }

    public void setProgress(Long progress) {
        this.progress = progress;
    }

    public Long getSignalling() {
        return signalling;
    }

    public void setSignalling(Long signalling) {
        this.signalling = signalling;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public Long getHasvars() {
        return hasvars;
    }

    public void setHasvars(Long hasvars) {
        this.hasvars = hasvars;
    }

    public Long getSupertask() {
        return supertask;
    }

    public void setSupertask(Long supertask) {
        this.supertask = supertask;
    }

    public Long getExecution() {
        return execution;
    }

    public void setExecution(Long execution) {
        this.execution = execution;
    }

    public Long getProcinst() {
        return procinst;
    }

    public void setProcinst(Long procinst) {
        this.procinst = procinst;
    }

    public Long getSwimlane() {
        return swimlane;
    }

    public void setSwimlane(Long swimlane) {
        this.swimlane = swimlane;
    }

    public String getTaskdefname() {
        return taskdefname;
    }

    public void setTaskdefname(String taskdefname) {
        this.taskdefname = taskdefname;
    }

    public String getHandleComments() {
        return handleComments;
    }

    public void setHandleComments(String handleComments) {
        this.handleComments = handleComments;
    }

    public String getHandleCommentsTime() {
        return handleCommentsTime;
    }

    public void setHandleCommentsTime(String handleCommentsTime) {
        this.handleCommentsTime = handleCommentsTime;
    }

    public String getIsMainTask() {
        return isMainTask;
    }

    public void setIsMainTask(String isMainTask) {
        this.isMainTask = isMainTask;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getReassign() {
        return reassign;
    }

    public void setReassign(String reassign) {
        this.reassign = reassign;
    }

    public String getReassignName() {
        return reassignName;
    }

    public void setReassignName(String reassignName) {
        this.reassignName = reassignName;
    }

    public String getReassignComments() {
        return reassignComments;
    }

    public void setReassignComments(String reassignComments) {
        this.reassignComments = reassignComments;
    }

    public String getReassignCommentsTime() {
        return reassignCommentsTime;
    }

    public void setReassignCommentsTime(String reassignCommentsTime) {
        this.reassignCommentsTime = reassignCommentsTime;
    }

    public Long getTaskInstDbid() {
        return taskInstDbid;
    }

    public void setTaskInstDbid(Long taskInstDbid) {
        this.taskInstDbid = taskInstDbid;
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

    public String getDueString() {
        return dueString;
    }

    public void setDueString(String dueString) {
        this.dueString = dueString;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
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

    public String getAgentStartString() {
        return agentStartString;
    }

    public void setAgentStartString(String agentStartString) {
        this.agentStartString = agentStartString;
    }

    public String getAgentEndString() {
        return agentEndString;
    }

    public void setAgentEndString(String agentEndString) {
        this.agentEndString = agentEndString;
    }

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

    public Long getWfBusInstanceId() {
        return wfBusInstanceId;
    }

    public void setWfBusInstanceId(Long wfBusInstanceId) {
        this.wfBusInstanceId = wfBusInstanceId;
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

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
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

    public String getProcInstState() {
        return procInstState;
    }

    public void setProcInstState(String procInstState) {
        this.procInstState = procInstState;
    }
}
