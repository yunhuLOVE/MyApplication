package com.augur.zongyang.model;

import java.io.Serializable;

/**
 * 下一环节信息
 * Created by yunhu on 2018-01-10.
 */

public class NextLinkInfoModel implements Serializable{
    private String chineseName;
    private LinkInfoModel destination;
    private String name;
    private String source;

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public LinkInfoModel getDestination() {
        return destination;
    }

    public void setDestination(LinkInfoModel destination) {
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
