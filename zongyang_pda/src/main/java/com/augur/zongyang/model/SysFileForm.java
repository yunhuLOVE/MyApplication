package com.augur.zongyang.model;

import java.io.Serializable;

/**
 * Created by yunhu on 2018-01-03.
 */

public class SysFileForm implements Serializable{

    private static final long serialVersionUID = 3265158163913809715L;
    private Long sysFileId;
    private String fileCode;
    private String fileName;
    private Long fileSize;
    private String fileType;
    private String filePath;
    private String fileFormat;
    private String cmp;
    private String cdt;
    private String eemp;
    private String edt;
    private String memo1;
    private String memo2;
    private String entity;
    private String entityId;
    private boolean isAttachmentContentChange;
    private String newFilePath;
    private String newFileName;

    private String templateCode;
    private String printTplPath;
    private String zhengwenPath;
    private String redHeadPath;
    public Long getSysFileId() {
        return sysFileId;
    }
    public void setSysFileId(Long sysFileId) {
        this.sysFileId = sysFileId;
    }
    public String getFileCode() {
        return fileCode;
    }
    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public Long getFileSize() {
        return fileSize;
    }
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getFileFormat() {
        return fileFormat;
    }
    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }
    public String getCmp() {
        return cmp;
    }
    public void setCmp(String cmp) {
        this.cmp = cmp;
    }
    public String getCdt() {
        return cdt;
    }
    public void setCdt(String cdt) {
        this.cdt = cdt;
    }
    public String getEemp() {
        return eemp;
    }
    public void setEemp(String eemp) {
        this.eemp = eemp;
    }
    public String getEdt() {
        return edt;
    }
    public void setEdt(String edt) {
        this.edt = edt;
    }
    public String getMemo1() {
        return memo1;
    }
    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }
    public String getMemo2() {
        return memo2;
    }
    public void setMemo2(String memo2) {
        this.memo2 = memo2;
    }
    public String getEntity() {
        return entity;
    }
    public void setEntity(String entity) {
        this.entity = entity;
    }
    public String getEntityId() {
        return entityId;
    }
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
    public boolean isAttachmentContentChange() {
        return isAttachmentContentChange;
    }
    public void setAttachmentContentChange(boolean isAttachmentContentChange) {
        this.isAttachmentContentChange = isAttachmentContentChange;
    }
    public String getNewFilePath() {
        return newFilePath;
    }
    public void setNewFilePath(String newFilePath) {
        this.newFilePath = newFilePath;
    }
    public String getNewFileName() {
        return newFileName;
    }
    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }
    public String getTemplateCode() {
        return templateCode;
    }
    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }
    public String getPrintTplPath() {
        return printTplPath;
    }
    public void setPrintTplPath(String printTplPath) {
        this.printTplPath = printTplPath;
    }
    public String getZhengwenPath() {
        return zhengwenPath;
    }
    public void setZhengwenPath(String zhengwenPath) {
        this.zhengwenPath = zhengwenPath;
    }
    public String getRedHeadPath() {
        return redHeadPath;
    }
    public void setRedHeadPath(String redHeadPath) {
        this.redHeadPath = redHeadPath;
    }


}
