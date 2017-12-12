package com.augur.zongyang.network.operator.base;

import android.content.Context;

/**
 * Created by yunhu on 2017-12-11.
 */

public class MyBaseHttpOpera extends BaseHttpOpera {
    public MyBaseHttpOpera(Context context,
                           OnNetResultListener onNetResultListener) {
        super(context, onNetResultListener);
    }

    @Override
    public boolean isUseLog() {
        return true;
    }

}
