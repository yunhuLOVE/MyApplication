package com.augur.zongyang.model;

/**
 * Created by yunhu on 2017-12-11.
 */

public class Response {

    private boolean success;
    private String description;
    private Integer code;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }


}
