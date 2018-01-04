package com.augur.zongyang.network.helper;

import android.content.Context;

import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.network.operator.OmUserHttpOpera;
import com.augur.zongyang.network.operator.base.BaseHttpOpera;

/**
 * Created by yunhu on 2017-12-11.
 */

public class NetworkHelper {

    private final Context context;
    private static NetworkHelper instance;

    private static OmUserHttpOpera omUserHttpOpera;

    private static MyWorkHttpOpera myWorkHttpOpera;

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
            resultHttpOpera = getOmUserHttpOpera();
        }else if (classT == MyWorkHttpOpera.class){
            resultHttpOpera = getMyWorkHttpOpera();
        }
        return (T)resultHttpOpera;
    }


    private OmUserHttpOpera getOmUserHttpOpera() {
        if (this.omUserHttpOpera == null) {
            this.omUserHttpOpera = new OmUserHttpOpera(context, null);
        }
        return this.omUserHttpOpera;
    }

    private MyWorkHttpOpera getMyWorkHttpOpera(){
        if(this.myWorkHttpOpera == null){
            this.myWorkHttpOpera = new MyWorkHttpOpera(context, null);
        }
        return this.myWorkHttpOpera;
    }
}
