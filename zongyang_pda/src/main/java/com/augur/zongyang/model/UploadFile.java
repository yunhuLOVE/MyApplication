package com.augur.zongyang.model;

import java.io.Serializable;

/**
 * Created by yunhu on 2017-12-11.
 */

public class UploadFile implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = -3677008314118645258L;
    private String name;
    private String filePath;
    private String contentType;
    private String base64Str;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getBase64Str() {
        return base64Str;
    }

    public void setBase64Str(String base64Str) {
        this.base64Str = base64Str;
    }

}
