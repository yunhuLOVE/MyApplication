package com.augur.zongyang.network.operator;

import android.content.Context;

import com.augur.zongyang.bean.OmUserData;
import com.augur.zongyang.model.Response;
import com.augur.zongyang.model.http.request.RequestMethod;
import com.augur.zongyang.network.operator.base.MyBaseHttpOpera;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

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
    public Boolean checkOmUser(String name, String password) {
        api.refreshSetting();

        String url = api.getAPI_GET_CHECK_USER() + api.urlDivider + name
                + api.urlDivider + password + api.urlDivider + 1;
        return this
                .getResultObject(url, null, RequestMethod.GET, Boolean.class);
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
