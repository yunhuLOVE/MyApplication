package com.augur.zongyang.network.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.augur.zongyang.R;

import java.util.Map;

/**
 * Created by yunhu on 2017-12-11.
 */

public class WebServiceApi {

    private String TAG = "WebServiceApi";

    private static WebServiceApi instance;
    private Context context;
    private SharedPreferences sharedPreferences;
//    private SettingPreference settingPreference;
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
        sharedPreferences = context.getSharedPreferences("sp_ip", Context.MODE_PRIVATE);
//        settingPreference = new SettingPreference(context);
        this.refreshSetting();
    }

    public void refreshSetting() {
        // TODO Auto-generated method stub
//        IP = settingPreference.getPreferences().getString("setting_ip",
//                context.getString(R.string.default_base_url));
        IP = sharedPreferences.getString("ip", context.getString(R.string.default_base_url));

    }

    public String getAPI_SSO() {
        if (API_SSO == null || API_SSO.equals("")) {
            API_SSO = this.getDgspUrl() + "/enable_sso";
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

    public String getDgspUrl() {
        return this.getBaseUrl() + "/dgsp";
    }

    private String getRestUrl() {
        return this.getDgspUrl() + strRest + urlDivider;
    }

    private String getPdaHandleUrl(){
        return this.getRestUrl() + "pdaHandle" + urlDivider;
    }


    // 首次登录
    public String getAPI_GET_CHECK_USER() {
        // TODO Auto-generated method stub
        return this.getPdaHandleUrl() + "loginValidate";

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

    private String getAfsSysFileUrl() {
        return this.getPdaHandleUrl() + this.urlDivider + "afs-sys-file!";
    }

    // gallery下载图片
    public String getAPI_IMAGEREAD() {
        // TODO Auto-generated method stub
        return this.getAfsSysFileUrl() + "readAttachment.action";
    }

    //任务签收
    public String getAPI_GET_TASK_SIGN(){
        return this.getPdaHandleUrl() + "signTask";
    }

    //获取待办列表
    public String getAPI_GET_TASK_LIST_NOT_TO_DO(){
        return this.getPdaHandleUrl() + "getDbSummary";
    }

    //获取在办列表
    public String getAPI_GET_TASK_LIST_DOING(){
        return this.getPdaHandleUrl() + "getZbSummary";
    }

    // 获取附件列表
    public String getAPI_GET_ATTACHMENT_LIST_DATA() {
        return this.getPdaHandleUrl() + "attachment/getAttachments";
    }

    // 上传文件
    public String getAPI_GET_UPLOAD_FILE() {
        return this.getPdaHandleUrl() + "attachment/uploadFiles";
    }

    // 删除附件
    public String getAPI_GET_DELETE_FILE() {
        return this.getPdaHandleUrl() + "attachment/deleteFiles";
    }
}
