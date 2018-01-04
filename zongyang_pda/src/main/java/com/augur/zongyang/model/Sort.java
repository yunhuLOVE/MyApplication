package com.augur.zongyang.model;

import java.io.Serializable;

/**
 * Created by yunhu on 2017-12-19.
 */

public class Sort implements Serializable{

    private String dir;
    private String property;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
