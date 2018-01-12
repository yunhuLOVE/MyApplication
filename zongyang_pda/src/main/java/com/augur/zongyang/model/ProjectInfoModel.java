package com.augur.zongyang.model;

import java.io.Serializable;

/**
 * Created by yunhu on 2018-01-09.
 */

public class ProjectInfoModel implements Serializable{

    private Long id;
    private Long projId;
    protected String procInstId;

    private String projectCode;//项目编号
    private String projectName;//项目名称
    private String mainClass;//项目类别
    private String subClass;//项目小类
    private String designOrg;//建设内容
    private String applicantOrgCode;//法人代表
    private String applicant;//建设单位
    private String idcard;//单位地址
    private String responsibleOrg;//主管部门
    private String landCharacter;//用地性质
    private Long buildLandAreaSum;//建设用地面积
    private Long buildAreaSum;//建筑面积
    private Long investSum;//总投资
    private String financialSource;//资金来源
    private String district;//所属区域
    private String site;//拟选位置
    private String startYear;//建设开始年限
    private String endYear;//建设完成年限
    private String buildingDroit;//土地权属
    private String description;//工程概况
    private String contact;//联系人
    private String mobile;//联系电话

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDesignOrg() {
        return designOrg;
    }

    public void setDesignOrg(String designOrg) {
        this.designOrg = designOrg;
    }

    public String getApplicantOrgCode() {
        return applicantOrgCode;
    }

    public void setApplicantOrgCode(String applicantOrgCode) {
        this.applicantOrgCode = applicantOrgCode;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getResponsibleOrg() {
        return responsibleOrg;
    }

    public void setResponsibleOrg(String responsibleOrg) {
        this.responsibleOrg = responsibleOrg;
    }

    public String getLandCharacter() {
        return landCharacter;
    }

    public void setLandCharacter(String landCharacter) {
        this.landCharacter = landCharacter;
    }

    public Long getBuildLandAreaSum() {
        return buildLandAreaSum;
    }

    public void setBuildLandAreaSum(Long buildLandAreaSum) {
        this.buildLandAreaSum = buildLandAreaSum;
    }

    public Long getBuildAreaSum() {
        return buildAreaSum;
    }

    public void setBuildAreaSum(Long buildAreaSum) {
        this.buildAreaSum = buildAreaSum;
    }

    public Long getInvestSum() {
        return investSum;
    }

    public void setInvestSum(Long investSum) {
        this.investSum = investSum;
    }

    public String getFinancialSource() {
        return financialSource;
    }

    public void setFinancialSource(String financialSource) {
        this.financialSource = financialSource;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
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

    public String getBuildingDroit() {
        return buildingDroit;
    }

    public void setBuildingDroit(String buildingDroit) {
        this.buildingDroit = buildingDroit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
