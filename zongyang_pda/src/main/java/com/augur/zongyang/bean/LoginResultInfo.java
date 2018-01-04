package com.augur.zongyang.bean;

/**
 * Created by yunhu on 2017-12-19.
 */

public class LoginResultInfo {

    private OmUserData user;

    //登录名
    private String loginName;

    //用户ID
    private Long userId;

    //用户名
    private String userName;

    public OmUserData getUser() {
        return user;
    }

    public void setUser(OmUserData user) {
        this.user = user;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
