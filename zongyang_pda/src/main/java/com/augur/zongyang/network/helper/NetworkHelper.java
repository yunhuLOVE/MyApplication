package com.augur.zongyang.network.helper;

import android.content.Context;

import com.augur.zongyang.network.operator.OmUserHttpOpera;
import com.augur.zongyang.network.operator.base.BaseHttpOpera;

/**
 * Created by yunhu on 2017-12-11.
 */

public class NetworkHelper {

    private final Context context;
    private static NetworkHelper instance;

    private static OmUserHttpOpera loginUserHttpOpera;

    private NetworkHelper(Context context) {
        this.context = context;
    }

    public static NetworkHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkHelper(context);
        }
        return instance;
    }

    public <T> T getHttpOpera(Class<T> classT) {
        BaseHttpOpera resultHttpOpera = null;
        if(classT == OmUserHttpOpera.class){
            resultHttpOpera = getLoginUserHttpOpera();
        }
        return (T)resultHttpOpera;
    }


    private OmUserHttpOpera getLoginUserHttpOpera() {
        if (this.loginUserHttpOpera == null) {
            this.loginUserHttpOpera = new OmUserHttpOpera(context, null);
        }
        return this.loginUserHttpOpera;
    }
}
