package com.augur.zongyang.util;

import android.util.Log;

import com.augur.zongyang.manager.JsonManager;
import com.augur.zongyang.util.base.BaseLogUtil;
import com.augur.zongyang.util.constant.AppConstant;
import com.augur.zongyang.util.constant.AppLibConstant;

/**
 * Created by yunhu on 2017-12-11.
 */

public class LogUtil extends BaseLogUtil {

    public static void warning(Object tagObject, Object informationObject) {
        if (!AppConstant.LOG) {
            return;
        }
        LoggerUtil.warning(tagObject, informationObject);
    }

    public static void info(Object tagObject, String informationPrefixString,
                            Object informationObject) {
        if (!AppConstant.LOG) {
            return;
        }
        LoggerUtil.info(tagObject, informationPrefixString, informationObject);
    }

    public static void warning(Object tagObject,
                               String informationPrefixString, Object informationObject) {
        if (!AppConstant.LOG) {
            return;
        }
        LoggerUtil.warning(tagObject, informationPrefixString,
                informationObject);
    }

    public static void error(Object tagObject, String informationPrefixString,
                             Object informationObject) {
        if (!AppConstant.LOG) {
            return;
        }
        LoggerUtil.error(tagObject, informationPrefixString, informationObject);
    }

    public static void debug(Object tagObject, String informationPrefixString,
                             Object informationObject) {
        if (!AppConstant.LOG) {
            return;
        }
        LoggerUtil.debug(tagObject, informationPrefixString, informationObject);
    }

    public static void verbose(Object tagObject,
                               String informationPrefixString, Object informationObject) {
        if (!AppConstant.LOG) {
            return;
        }
        LoggerUtil.verbose(tagObject, informationPrefixString,
                informationObject);
    }

    public static void systemOut(Object tagObject,
                                 String informationPrefixString, Object informationObject) {
        if (!AppConstant.LOG) {
            return;
        }
        LoggerUtil.systemOut(tagObject, informationPrefixString,
                informationObject);
    }

    public static String getLogString(Object paramObject) {
        return getLogString(null, paramObject, JsonManager.getInstance());
    }

    public static String getLogString(Object paramObject1, Object paramObject2) {
        return getLogString(paramObject1, paramObject2,
                JsonManager.getInstance());
    }

    private static void a(Object paramObject1, Object paramObject2,
                          Object paramObject3, int paramInt) {
        if ((paramObject3 == null) || (!AppLibConstant.isUseLog())) {
            return;
        }
        JsonManager localJsonManager = JsonManager.getInstance();
        paramObject1 = (paramInt == 9) || (paramInt == 10) ? null
                : getTagString(paramObject1, localJsonManager);
        paramObject2 = getLogString(paramObject2, paramObject3);
        switch (paramInt) {
            case 9:
                BaseLogUtil.systemOut((String) paramObject2);
                return;
            case 10:
                BaseLogUtil.fileOut((String) paramObject2);
                return;
            case 2:
                Log.v((String) paramObject1, (String) paramObject2);
                return;
            case 3:
                Log.d((String) paramObject1, (String) paramObject2);
                return;
            case 4:
                Log.i((String) paramObject1, (String) paramObject2);
                return;
            case 5:
                Log.w((String) paramObject1, (String) paramObject2);
                return;
            case 6:
                Log.e((String) paramObject1, (String) paramObject2);
        }
    }

    public static void systemOut(Object paramObject) {
        a(null, null, paramObject, 9);
    }

    public static void systemOut(Object paramObject1, Object paramObject2) {
        a(null, paramObject1, paramObject2, 9);
    }

    public static void fileOut(Object paramObject) {
        a(null, null, paramObject, 10);
    }

    public static void fileOut(Object paramObject1, Object paramObject2) {
        a(null, paramObject1, paramObject2, 10);
    }

    public static void verbose(Object paramObject) {
        a(null, null, paramObject, 2);
    }

    public static void verbose(Object paramObject1, Object paramObject2) {
        a(paramObject1, null, paramObject2, 2);
    }

    public static void verbose(Object paramObject1, Object paramObject2,
                               Object paramObject3) {
        a(paramObject1, paramObject2, paramObject3, 2);
    }

    public static void debug(Object paramObject) {
        a(null, null, paramObject, 3);
    }

    public static void debug(Object paramObject1, Object paramObject2) {
        a(paramObject1, null, paramObject2, 3);
    }

    public static void debug(Object paramObject1, Object paramObject2,
                             Object paramObject3) {
        a(paramObject1, paramObject2, paramObject3, 3);
    }

    public static void info(Object paramObject) {
        a(null, null, paramObject, 4);
    }

    public static void info(Object paramObject1, Object paramObject2) {
        a(paramObject1, null, paramObject2, 4);
    }

    public static void info(Object paramObject1, Object paramObject2,
                            Object paramObject3) {
        a(paramObject1, paramObject2, paramObject3, 4);
    }

    public static void warn(Object paramObject) {
        a(null, null, paramObject, 5);
    }

    public static void warn(Object paramObject1, Object paramObject2) {
        a(paramObject1, null, paramObject2, 5);
    }

    public static void warn(Object paramObject1, Object paramObject2,
                            Object paramObject3) {
        a(paramObject1, paramObject2, paramObject3, 5);
    }

    public static void error(Object paramObject) {
        a(null, null, paramObject, 6);
    }

    public static void error(Object paramObject1, Object paramObject2) {
        a(paramObject1, null, paramObject2, 6);
    }

    public static void error(Object paramObject1, Object paramObject2,
                             Object paramObject3) {
        a(paramObject1, paramObject2, paramObject3, 6);
    }


}
