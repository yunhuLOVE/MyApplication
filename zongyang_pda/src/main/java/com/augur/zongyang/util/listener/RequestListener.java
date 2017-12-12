package com.augur.zongyang.util.listener;

/**
 * Created by yunhu on 2017-12-11.
 */

public interface RequestListener {public abstract void onSuccess(Object paramObject);

    public abstract void onFail(RequestFailReason paramRequestFailReason);

    public abstract void onProgress(double paramDouble1, double paramDouble2);

    public static enum RequestFailReason {
        _NET,EXCEPTION
    }
}
