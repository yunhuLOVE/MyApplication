package com.augur.zongyang.bean;

/**
 * Created by yunhu on 2017-12-19.
 */

public class LoginUserAllInfo {

    private boolean success;

    private LoginResultInfo result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public LoginResultInfo getResult() {
        return result;
    }

    public void setResult(LoginResultInfo result) {
        this.result = result;
    }
}
