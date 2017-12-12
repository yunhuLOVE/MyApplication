package com.augur.zongyang.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunhu on 2017-12-11.
 */

public class OmUserData {
    private String funcCodes;
    private String orgCode;
    private String userName;
    private String loginName;
    private String password;
    private Long userId;
    private String userCode;
    private String isActive;
    private String mobile;
    private Long loginUnitType;// 登录单位类型
    // 网格编码
    private String netCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return userName;
    }

    public void setOrgName(String userName) {
        this.userName = userName;
    }

    public List<String> getFuncCodes() {
        List<String> codes = new ArrayList<String>();
        String  [] arr = funcCodes.split(",");
        for (String code : arr) {
            codes.add(code);
        }
        return codes;
    }

    public void setFuncCodes(String funcCodes) {
        this.funcCodes = funcCodes;
    }

    public String getNetCode() {
        return netCode;
    }

    public void setNetCode(String netCode) {
        this.netCode = netCode;
    }

    public Long getLoginUnitType() {
        return loginUnitType;
    }

    public void setLoginUnitType(Long loginUnitType) {
        this.loginUnitType = loginUnitType;
    }


}
