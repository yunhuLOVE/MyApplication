package com.augur.zongyang.database.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by yunhu on 2018-01-02.
 */
@DatabaseTable
public class Cphoto implements Serializable{
    /**
     *
     */
    @DatabaseField(id = true)
    private Long dbid;
    @DatabaseField
    private String localPath;
    @DatabaseField
    private Long problemid;
    @DatabaseField
    private Long siteReportId;
    @DatabaseField
    private Integer isSave;//0代表未保存，1代表保存
    @DatabaseField
    private String photoname;//图片url
    @DatabaseField
    private Long taskid;

    public Long getDbid() {
        return dbid;
    }
    public void setDbid(Long dbid) {
        this.dbid = dbid;
    }
    public String getLocalPath() {
        return localPath;
    }
    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
    public Long getProblemid() {
        return problemid;
    }
    public void setProblemid(Long problemid) {
        this.problemid = problemid;
    }
    public Integer getIsSave() {
        if(isSave == null)
            return 0;
        return isSave;
    }
    public void setIsSave(Integer isSave) {
        this.isSave = isSave;
    }
    public Long getSiteReportId() {
        return siteReportId;
    }
    public void setSiteReportId(Long siteReportId) {
        this.siteReportId = siteReportId;
    }
    public String getPhotoname() {
        return photoname;
    }
    public void setPhotoname(String photoname) {
        this.photoname = photoname;
    }
    public Long getTaskid() {
        return taskid;
    }
    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }


}
