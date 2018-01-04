package com.augur.zongyang.network.operator;

import android.content.Context;

import com.augur.zongyang.bean.LoginUserAllInfo;
import com.augur.zongyang.bean.OmUserData;
import com.augur.zongyang.model.Response;
import com.augur.zongyang.model.http.request.RequestMethod;
import com.augur.zongyang.network.operator.base.MyBaseHttpOpera;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yunhu on 2017-12-11.
 */

public class OmUserHttpOpera extends MyBaseHttpOpera {
    private String TAG = "OmUserHttpOpera";

    public OmUserHttpOpera(Context context,
                           OnNetResultListener onNetResultListener) {
        super(context, onNetResultListener);
    }

    // 首次登录验证
    public LoginUserAllInfo checkOmUser(String loginName, String password) {
        api.refreshSetting();
        Type type = new TypeToken<LoginUserAllInfo>() {
        }.getType();
        String url = api.getAPI_GET_CHECK_USER();

        Map<String,Object> params = new HashMap<>();

        params.put("loginName",loginName);
        params.put("password",password);
        params.put("d",String.valueOf(new Date().getTime()));

        return this
                .getResultObject(url, params, RequestMethod.GET, type);
    }

    /*
     * 二次登录验证
     */
    public String loginChecked(String name, String pwd) {
        String url = api.getAPI_USER_CHECKED();
        String result = null;

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("passwd", pwd));
        result = this.getResultObject(url, params, RequestMethod.POST,
                String.class);

        return result;
    }

    public Response enableSSO(String userName) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userName", userName));
        return this.getResultObject(api.getAPI_SSO(), params,
                RequestMethod.GET, Response.class);
    }

    /*
	 * 获取用户信息
	 */
    public OmUserData getUserInfo(String name) {
        OmUserData user = null;
        if (false) {
        } else {
            String url = api.getAPI_USER_INFO() + "/" + name;
            user = this.getResultObject(url, null, RequestMethod.GET,
                    OmUserData.class);
        }
        return user;
    }
}
