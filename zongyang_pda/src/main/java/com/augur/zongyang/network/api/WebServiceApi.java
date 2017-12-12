package com.augur.zongyang.network.api;

import android.content.Context;

import com.augur.zongyang.R;
import com.augur.zongyang.common.SettingPreference;

import java.util.Map;

/**
 * Created by yunhu on 2017-12-11.
 */

public class WebServiceApi {

    private String TAG = "WebServiceApi";

    private static WebServiceApi instance;
    private Context context;
    private SettingPreference settingPreference;
    private final String strHead = "http://";
    private final String strSupport = "/agsupport";
    private final String strRest = "/rest";

    public final String urlDivider = "/";
    private final String urlAndParamDivider = "?";
    private final String urlParamDivider = "&";
    private final String urlEqual = "=";
    public String IP;

    private String API_SSO;

    public static WebServiceApi getInstance(Context context) {
        if (instance == null) {
            instance = new WebServiceApi(context);
        }
        return instance;
    }

    private WebServiceApi(Context context) {
        this.context = context;
        settingPreference = new SettingPreference(context);
        this.refreshSetting();
    }

    public void refreshSetting() {
        // TODO Auto-generated method stub
        IP = settingPreference.getPreferences().getString("setting_ip",
                context.getString(R.string.default_base_url));
    }

    public String getAPI_SSO() {
        if (API_SSO == null || API_SSO.equals("")) {
            API_SSO = this.getAgSupportUrl() + "/enable_sso";
        }
        return API_SSO;
    }

    public String getUrl(String url, Map<String, String> paramMap) {
        if (paramMap == null || paramMap.keySet().size() == 0) {
            return url;
        }
        StringBuilder stringBuilder = new StringBuilder(url);
        stringBuilder.append(this.urlAndParamDivider);
        for (String key : paramMap.keySet()) {
            stringBuilder.append(key).append(this.urlEqual)
                    .append(paramMap.get(key)).append(this.urlParamDivider);
        }
        return stringBuilder.toString();
    }

    public String getBaseUrl() {
        return strHead + IP;
    }

    public String getAgSupportUrl() {
        return this.getBaseUrl() + "/agsupport";
    }

    private String getRestUrl() {
        return this.getAgSupportUrl() + strRest + urlDivider;
    }


    // 首次登录
    public String getAPI_GET_CHECK_USER() {
        // TODO Auto-generated method stub
        return this.getRestUrl() + "user/checkLoginInfo";

    }

    // 二次登录
    public String getAPI_USER_CHECKED() {
        // TODO Auto-generated method stub
        return this.getRestUrl() + "user/checked";
    }

    // 获取用户信息
    public String getAPI_USER_INFO() {
        // TODO Auto-generated method stub
        return this.getRestUrl() + "user";
    }
}
