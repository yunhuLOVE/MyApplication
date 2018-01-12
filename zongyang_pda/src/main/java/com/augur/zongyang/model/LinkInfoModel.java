package com.augur.zongyang.model;

import java.io.Serializable;

/**
 * 环节信息
 * Created by yunhu on 2018-01-10.
 */

public class LinkInfoModel implements Serializable{
    private String chineseName;
    private String message;
    private String name;

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
