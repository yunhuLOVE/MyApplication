package com.augur.zongyang.bean;

/**
 * Created by yunhu on 2017-12-11.
 */

public class CurrentUser {

    private OmUserData omUser;
    private static CurrentUser instance;

    private CurrentUser() {
    }

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public OmUserData getCurrentUser() {
        return this.omUser;
    }

    public void setCurrentUser(OmUserData omUser) {
        this.omUser = omUser;
    }




}
