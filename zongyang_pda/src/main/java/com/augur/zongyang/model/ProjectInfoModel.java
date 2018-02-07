package com.augur.zongyang.model;

import java.io.Serializable;

/**
 * Created by yunhu on 2018-01-09.
 */

public class ProjectInfoModel implements Serializable{

//    private Long id;
//    private Long projId;
//    protected String procInstId;
//
//    private String projectCode;//项目编号
//    private String projectName;//项目名称
//    private String mainClass;//项目类别
//    private String subClass;//项目小类
//    private String designOrg;//建设内容
//    private String applicantOrgCode;//法人代表
//    private String applicant;//建设单位
//    private String idcard;//单位地址
//    private String responsibleOrg;//主管部门
//    private String landCharacter;//用地性质
//    private Double buildLandAreaSum;//建设用地面积
//    private Double buildAreaSum;//建筑面积
//    private Double investSum;//总投资
//    private String financialSource;//资金来源
//    private String district;//所属区域
//    private String site;//拟选位置
//    private String startYear;//建设开始年限
//    private String endYear;//建设完成年限
//    private String buildingDroit;//土地权属
//    private String description;//工程概况
//    private String contact;//联系人
//    private String mobile;//联系电话


    private static final long serialVersionUID = 2982059095994485064L;
    private Long id;
    protected String projectCode;
    private String projectName;
    private String applicant;
    private Boolean isCompl;
    private String authorizeCode;
    private Double investSum;
    private Double fixInvestSum;
    private Double sumLandAreaSum;
    private Double buildLandAreaSum;
    private Double buildAreaSum;
    private Double otherSum;
    private Double invest01;
    private Double sumLandArea01;
    private Double buildLandArea01;
    private Double buildArea01;
    private String other01;
    private Double invest02;
    private Double sumLandArea02;
    private Double buildLandArea02;
    private Double buildArea02;
    private String other02;
    private String buildBaseScope;
    private String buildingDroit;
    private String characterQuality;
    private String landCharacter;
    private String plotActuality;
    private String otherRequirement;
    private String attachements;

    private String district;  //所属区
    private String fgProjectCode;  //发改项目编号
    private String ghProjectCode;  //规划项目编号
    private String gtProjectCode;  //国土项目编号
    private String description;  ////项目描述
    private String site;  //项目位置
    private String startYear; //起始时间
    private String endYear; //结束时间

    private String procInstanceId;  //流程实例ID
    private String mapSharpId;   //空间数据ID

    private String businessId; //业务流水号(政务窗口接件的流水号)
    private String businessTypeCode; //办理事项编号

    private String mainClass; //大类
    private String subClass; //小类

    private String refProjectCode;  //关联项目编号
    private String refProjectName;	//关联项目名称
    private String refCbProCode;  //调用储备项目信息

    private String state;
    /**
     * 后来项目增加的字段
     */
    private String financialSource;   //资金来源
    private String contact;		//联系人
    private String idcard;	//联系人证件号码
    private String phonenum;	//联系人电话号码
    private String mobile;	//联系人手机号码
    private String email;	//联系人电子邮件
    private String applicantOrgCode;	//建设单位代码
    private String designOrg;	//设计单位
    private String designOrgCode;	//设计单位代码
    private String responsibleOrg;	//责任单位
    private String responsibleOrgCode;	//责任单位代码
    private String dataSource;	//项目数据来源(''''Z'''':来自政务中心调用web服务,''''A''''或空:来自多规合一业务协同平台录入)

    private String currentProgress; //目前进展情况
    private Double threeYearsInvestSum;  //未来三年预计投资总额（万元）
    private Double thisYearInvest;  //本年度计划投资（万元）
    private String thisYearProgress; //本年度进度计划（前期工作节点、施工形象进度等）
    private Double nextYearInvest;   //下一年度计划投资（万元）
    private String nextYearProgress; //下一年度进度目标（前期工作节点、施工形象进度等）

    private String createString;	//因需要显示 具体的年度而增加，比如：2014-2016年度计划
    private String createsString;
    private String createeString;

    private String tzks;  //投资匡算（亿元）
    private String xmly;  //项目来源
    private String beizhu;   //备注

    private String ssqy;
    private String templateName;
    private String templateCode;
    private String isSubmit;
    private String wbi;
    private String proc;
    private String statusCode;
    private String statusName;
    private String reason;
    private String beginString;
    private String endString;
    private String orgName;
    private String matterName;
    private String cString;
    private Long projId;
    private String procInstId;

    private String mainClassCn; //审批项目类别
    private String districtCn;  //所属区
    private String buildingDroitCn; //土地权属;
    private String isSonProjectCn;    //是否为子项目c
    private String planTypeCn;
    private String selectedAssignees;   //选择参与者

    public String getSelectedAssignees() {
        return selectedAssignees;
    }

    public void setSelectedAssignees(String selectedAssignees) {
        this.selectedAssignees = selectedAssignees;
    }

    public String getCreatesString() {
        return createsString;
    }

    public void setCreatesString(String createsString) {
        this.createsString = createsString;
    }

    public String getCreateeString() {
        return createeString;
    }

    public void setCreateeString(String createeString) {
        this.createeString = createeString;
    }

    private String projectStep;     //项目阶段（项目生成、项目审批）

    private String yearlyPlanCode;	//年度计划编号
    private String projCreateUser;	//项目发起人
    private String projCreateUserMobile;	//项目发起人

    private Long hylb;	//行业类别
    private Long ssjd;	//实施阶段

    private String projectHangCause; //项目挂起原因

    //参与审批流程的机构，默认情况下：市发改委、市规划委、市国土局参与 “市项目审批流程”
    private String apprJoinOrg;

    //投资主管单位
    private String invstSupervisory;

    private String projectStageType;

    private Long templateId;//流程类型id，只用于页面查询使用
    private String tempLateName; //流程类型名字，
    private String declareOrg;  //申报单位

    private String smsContent;
    private String planType;

    private String isSonProject;    //是否为子项目c
    private String projectNowActivity; //项目当前节点
    private String nowActivityDept;   //当前节点办理单位
    private String allHandler;    //用来暂存已经参与办理过的单位。
    private String cbStatus;     //项目状态（项目策划与预审阶段的项目状态）
    private String rkYear;     //入库年份
    private String proCreateString;
    //--项目流程信息---
    private String wfStartString;
    private String wfEndString;
    private String wfState;
    private String wfdueString;
    private String wfMark;
    private String allString;

    private String buildLandAreaSumStr;
    private String buildAreaSumStr;
    private String investSumStr;

    public String getBuildLandAreaSumStr() {
        return buildLandAreaSumStr;
    }

    public void setBuildLandAreaSumStr(String buildLandAreaSumStr) {
        this.buildLandAreaSumStr = buildLandAreaSumStr;
    }

    public String getBuildAreaSumStr() {
        return buildAreaSumStr;
    }

    public void setBuildAreaSumStr(String buildAreaSumStr) {
        this.buildAreaSumStr = buildAreaSumStr;
    }

    public String getInvestSumStr() {
        return investSumStr;
    }

    public void setInvestSumStr(String investSumStr) {
        this.investSumStr = investSumStr;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getApprJoinOrg() {
        return apprJoinOrg;
    }

    public void setApprJoinOrg(String apprJoinOrg) {
        this.apprJoinOrg = apprJoinOrg;
    }

    public String getProjCreateUser() {
        return projCreateUser;
    }
    public void setProjCreateUser(String projCreateUser) {
        this.projCreateUser = projCreateUser;
    }

    public String getProjCreateUserMobile() {
        return projCreateUserMobile;
    }
    public void setProjCreateUserMobile(String projCreateUserMobile) {
        this.projCreateUserMobile = projCreateUserMobile;
    }
    public String getYearlyPlanCode() {
        return yearlyPlanCode;
    }
    public void setYearlyPlanCode(String yearlyPlanCode) {
        this.yearlyPlanCode = yearlyPlanCode;
    }

    public String getCurrentProgress() {
        return currentProgress;
    }
    public void setCurrentProgress(String currentProgress) {
        this.currentProgress = currentProgress;
    }
    public Double getThreeYearsInvestSum() {
        return threeYearsInvestSum;
    }
    public void setThreeYearsInvestSum(Double threeYearsInvestSum) {
        this.threeYearsInvestSum = threeYearsInvestSum;
    }
    public Double getThisYearInvest() {
        return thisYearInvest;
    }
    public void setThisYearInvest(Double thisYearInvest) {
        this.thisYearInvest = thisYearInvest;
    }
    public String getThisYearProgress() {
        return thisYearProgress;
    }
    public void setThisYearProgress(String thisYearProgress) {
        this.thisYearProgress = thisYearProgress;
    }
    public Double getNextYearInvest() {
        return nextYearInvest;
    }
    public void setNextYearInvest(Double nextYearInvest) {
        this.nextYearInvest = nextYearInvest;
    }
    public String getNextYearProgress() {
        return nextYearProgress;
    }
    public void setNextYearProgress(String nextYearProgress) {
        this.nextYearProgress = nextYearProgress;
    }
    public String getProjectStep() {
        return projectStep;
    }
    public void setProjectStep(String projectStep) {
        this.projectStep = projectStep;
    }
    public String getCreateString() {
        return createString;
    }
    public void setCreateString(String createString) {
        this.createString = createString;
    }


    public String getDataSource() {
        return dataSource;
    }
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
    public String getProjectCode() {
        return projectCode;
    }
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Boolean getIsCompl() {
        return isCompl;
    }

    public void setIsCompl(Boolean isCompl) {
        this.isCompl = isCompl;
    }
    public String getAuthorizeCode() {
        return authorizeCode;
    }
    public void setAuthorizeCode(String authorizeCode) {
        this.authorizeCode = authorizeCode;
    }
    public Double getInvestSum() {
        return investSum;
    }
    public void setInvestSum(Double investSum) {
        this.investSum = investSum;
    }
    public Double getSumLandAreaSum() {
        return sumLandAreaSum;
    }
    public void setSumLandAreaSum(Double sumLandAreaSum) {
        this.sumLandAreaSum = sumLandAreaSum;
    }
    public Double getBuildLandAreaSum() {
        return buildLandAreaSum;
    }

    public void setBuildLandAreaSum(Double buildLandAreaSum) {
        this.buildLandAreaSum = buildLandAreaSum;
    }
    public Double getBuildAreaSum() {
        return buildAreaSum;
    }
    public void setBuildAreaSum(Double buildAreaSum) {
        this.buildAreaSum = buildAreaSum;
    }
    public Double getOtherSum() {
        return otherSum;
    }
    public void setOtherSum(Double otherSum) {
        this.otherSum = otherSum;
    }
    public Double getInvest01() {
        return invest01;
    }
    public void setInvest01(Double invest01) {
        this.invest01 = invest01;
    }
    public Double getSumLandArea01() {
        return sumLandArea01;
    }
    public void setSumLandArea01(Double sumLandArea01) {
        this.sumLandArea01 = sumLandArea01;
    }
    public Double getBuildLandArea01() {
        return buildLandArea01;
    }
    public void setBuildLandArea01(Double buildLandArea01) {
        this.buildLandArea01 = buildLandArea01;
    }
    public Double getBuildArea01() {
        return buildArea01;
    }
    public void setBuildArea01(Double buildArea01) {
        this.buildArea01 = buildArea01;
    }
    public String getOther01() {
        return other01;
    }
    public void setOther01(String other01) {
        this.other01 = other01;
    }
    public Double getInvest02() {
        return invest02;
    }
    public void setInvest02(Double invest02) {
        this.invest02 = invest02;
    }
    public Double getSumLandArea02() {
        return sumLandArea02;
    }
    public void setSumLandArea02(Double sumLandArea02) {
        this.sumLandArea02 = sumLandArea02;
    }
    public Double getBuildLandArea02() {
        return buildLandArea02;
    }
    public void setBuildLandArea02(Double buildLandArea02) {
        this.buildLandArea02 = buildLandArea02;
    }
    public Double getBuildArea02() {
        return buildArea02;
    }
    public void setBuildArea02(Double buildArea02) {
        this.buildArea02 = buildArea02;
    }
    public String getOther02() {
        return other02;
    }
    public void setOther02(String other02) {
        this.other02 = other02;
    }
    public String getBuildBaseScope() {
        return buildBaseScope;
    }
    public void setBuildBaseScope(String buildBaseScope) {
        this.buildBaseScope = buildBaseScope;
    }
    public String getBuildingDroit() {
        return buildingDroit;
    }
    public void setBuildingDroit(String buildingDroit) {
        this.buildingDroit = buildingDroit;
    }
    public String getCharacterQuality() {
        return characterQuality;
    }
    public void setCharacterQuality(String characterQuality) {
        this.characterQuality = characterQuality;
    }
    public String getLandCharacter() {
        return landCharacter;
    }
    public void setLandCharacter(String landCharacter) {
        this.landCharacter = landCharacter;
    }
    public String getPlotActuality() {
        return plotActuality;
    }
    public void setPlotActuality(String plotActuality) {
        this.plotActuality = plotActuality;
    }
    public String getOtherRequirement() {
        return otherRequirement;
    }
    public void setOtherRequirement(String otherRequirement) {
        this.otherRequirement = otherRequirement;
    }
    public String getAttachements() {
        return attachements;
    }
    public void setAttachements(String attachements) {
        this.attachements = attachements;
    }
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getFgProjectCode() {
        return fgProjectCode;
    }
    public void setFgProjectCode(String fgProjectCode) {
        this.fgProjectCode = fgProjectCode;
    }
    public String getGhProjectCode() {
        return ghProjectCode;
    }
    public void setGhProjectCode(String ghProjectCode) {
        this.ghProjectCode = ghProjectCode;
    }
    public String getGtProjectCode() {
        return gtProjectCode;
    }
    public void setGtProjectCode(String gtProjectCode) {
        this.gtProjectCode = gtProjectCode;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getProcInstanceId() {
        return procInstanceId;
    }
    public void setProcInstanceId(String procInstanceId) {
        this.procInstanceId = procInstanceId;
    }

    public String getMapSharpId() {
        return mapSharpId;
    }
    public void setMapSharpId(String mapSharpId) {
        this.mapSharpId = mapSharpId;
    }
    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
    public String getBusinessTypeCode() {
        return businessTypeCode;
    }

    public void setBusinessTypeCode(String businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }
    public String getMainClass() {
        return mainClass;
    }
    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }
    public String getSubClass() {
        return subClass;
    }
    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }
    public String getRefProjectCode() {
        return refProjectCode;
    }
    public void setRefProjectCode(String refProjectCode) {
        this.refProjectCode = refProjectCode;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getFinancialSource() {
        return financialSource;
    }
    public void setFinancialSource(String financialSource) {
        this.financialSource = financialSource;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getIdcard() {
        return idcard;
    }
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
    public String getPhonenum() {
        return phonenum;
    }
    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getApplicantOrgCode() {
        return applicantOrgCode;
    }
    public void setApplicantOrgCode(String applicantOrgCode) {
        this.applicantOrgCode = applicantOrgCode;
    }
    public String getDesignOrg() {
        return designOrg;
    }
    public void setDesignOrg(String designOrg) {
        this.designOrg = designOrg;
    }
    public String getDesignOrgCode() {
        return designOrgCode;
    }
    public void setDesignOrgCode(String designOrgCode) {
        this.designOrgCode = designOrgCode;
    }
    public String getResponsibleOrg() {
        return responsibleOrg;
    }
    public void setResponsibleOrg(String responsibleOrg) {
        this.responsibleOrg = responsibleOrg;
    }
    public String getResponsibleOrgCode() {
        return responsibleOrgCode;
    }
    public void setResponsibleOrgCode(String responsibleOrgCode) {
        this.responsibleOrgCode = responsibleOrgCode;
    }
    public String getRefProjectName() {
        return refProjectName;
    }
    public void setRefProjectName(String refProjectName) {
        this.refProjectName = refProjectName;
    }

    public Long getHylb() {
        return hylb;
    }

    public void setHylb(Long hylb) {
        this.hylb = hylb;
    }

    public Long getSsjd() {
        return ssjd;
    }

    public void setSsjd(Long ssjd) {
        this.ssjd = ssjd;
    }

    public String getInvstSupervisory() {
        return invstSupervisory;
    }

    public void setInvstSupervisory(String invstSupervisory) {
        this.invstSupervisory = invstSupervisory;
    }

    public String getProjectStageType() {
        return projectStageType;
    }

    public void setProjectStageType(String projectStageType) {
        this.projectStageType = projectStageType;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Double getFixInvestSum() {
        return fixInvestSum;
    }

    public void setFixInvestSum(Double fixInvestSum) {
        this.fixInvestSum = fixInvestSum;
    }

    public String getDeclareOrg() {
        return declareOrg;
    }

    public void setDeclareOrg(String declareOrg) {
        this.declareOrg = declareOrg;
    }

    public String getWfStartString() {
        return wfStartString;
    }

    public void setWfStartString(String wfStartString) {
        this.wfStartString = wfStartString;
    }

    public String getWfEndString() {
        return wfEndString;
    }

    public void setWfEndString(String wfEndString) {
        this.wfEndString = wfEndString;
    }

    public String getWfState() {
        return wfState;
    }

    public void setWfState(String wfState) {
        this.wfState = wfState;
    }

    public String getWfdueString() {
        return wfdueString;
    }

    public void setWfdueString(String wfdueString) {
        this.wfdueString = wfdueString;
    }

    public String getWfMark() {
        return wfMark;
    }

    public void setWfMark(String wfMark) {
        this.wfMark = wfMark;
    }

    public String getAllString() {
        return allString;
    }

    public void setAllString(String allString) {
        this.allString = allString;
    }
    public String getTempLateName() {
        return tempLateName;
    }

    public void setTempLateName(String tempLateName) {
        this.tempLateName = tempLateName;
    }
    public String getIsSonProject() {
        return isSonProject;
    }

    public void setIsSonProject(String isSonProject) {
        this.isSonProject = isSonProject;
    }

    public String getProjectHangCause() {
        return projectHangCause;
    }

    public void setProjectHangCause(String projectHangCause) {
        this.projectHangCause = projectHangCause;
    }
    public String getProjectNowActivity() {
        return projectNowActivity;
    }
    public void setProjectNowActivity(String projectNowActivity) {
        this.projectNowActivity = projectNowActivity;
    }
    public String getNowActivityDept() {
        return nowActivityDept;
    }

    public void setNowActivityDept(String nowActivityDept) {
        this.nowActivityDept = nowActivityDept;
    }

    public String getAllHandler() {
        return allHandler;
    }

    public void setAllHandler(String allHandler) {
        this.allHandler = allHandler;
    }

    public String getRefCbProCode() {
        return refCbProCode;
    }

    public void setRefCbProCode(String refCbProCode) {
        this.refCbProCode = refCbProCode;
    }

    public String getTzks() {
        return tzks;
    }

    public void setTzks(String tzks) {
        this.tzks = tzks;
    }

    public String getXmly() {
        return xmly;
    }

    public void setXmly(String xmly) {
        this.xmly = xmly;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getCbStatus() {
        return cbStatus;
    }

    public void setCbStatus(String cbStatus) {
        this.cbStatus = cbStatus;
    }

    public String getRkYear() {
        return rkYear;
    }

    public void setRkYear(String rkYear) {
        this.rkYear = rkYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProCreateString() {
        return proCreateString;
    }

    public void setProCreateString(String proCreateString) {
        this.proCreateString = proCreateString;
    }

    public String getMainClassCn() {
        return mainClassCn;
    }

    public void setMainClassCn(String mainClassCn) {
        this.mainClassCn = mainClassCn;
    }

    public String getSsqy() {
        return ssqy;
    }

    public void setSsqy(String ssqy) {
        this.ssqy = ssqy;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(String isSubmit) {
        this.isSubmit = isSubmit;
    }

    public String getWbi() {
        return wbi;
    }

    public void setWbi(String wbi) {
        this.wbi = wbi;
    }

    public String getProc() {
        return proc;
    }

    public void setProc(String proc) {
        this.proc = proc;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getBeginString() {
        return beginString;
    }

    public void setBeginString(String beginString) {
        this.beginString = beginString;
    }

    public String getEndString() {
        return endString;
    }

    public void setEndString(String endString) {
        this.endString = endString;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getMatterName() {
        return matterName;
    }

    public void setMatterName(String matterName) {
        this.matterName = matterName;
    }

    public String getCString() {
        return cString;
    }

    public void setCString(String String) {
        cString = String;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }


    public String getDistrictCn() {
        return districtCn;
    }

    public void setDistrictCn(String districtCn) {
        this.districtCn = districtCn;
    }

    public String getBuildingDroitCn() {
        return buildingDroitCn;
    }

    public void setBuildingDroitCn(String buildingDroitCn) {
        this.buildingDroitCn = buildingDroitCn;
    }

    public String getIsSonProjectCn() {
        return isSonProjectCn;
    }

    public void setIsSonProjectCn(String isSonProjectCn) {
        this.isSonProjectCn = isSonProjectCn;
    }

    public String getPlanTypeCn() {
        return planTypeCn;
    }

    public void setPlanTypeCn(String planTypeCn) {
        this.planTypeCn = planTypeCn;
    }
}
