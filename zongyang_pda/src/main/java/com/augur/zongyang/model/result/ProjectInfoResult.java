package com.augur.zongyang.model.result;

import com.augur.zongyang.model.ProjectInfoModel;
import com.augur.zongyang.model.ProjectTypeModel;

import java.io.Serializable;
import java.util.List;

/**
 * 项目详情界面数据
 * Created by yunhu on 2018-01-09.
 */

public class ProjectInfoResult implements Serializable{

    private List<ProjectTypeModel> xmlb;

    private List<ProjectInfoModel> projectInfo;

    public List<ProjectTypeModel> getXmlb() {
        return xmlb;
    }

    public void setXmlb(List<ProjectTypeModel> xmlb) {
        this.xmlb = xmlb;
    }

    public List<ProjectInfoModel> getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(List<ProjectInfoModel> projectInfo) {
        this.projectInfo = projectInfo;
    }
}
