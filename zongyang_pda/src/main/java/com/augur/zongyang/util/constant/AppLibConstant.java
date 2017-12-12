package com.augur.zongyang.util.constant;

/**
 * Created by yunhu on 2017-12-11.
 */

public class AppLibConstant {

    public static Boolean LOG_USE;
    public static String LOG_FILE;

    public static boolean isUseLog() {
        if (LOG_USE != null) {
            return LOG_USE.booleanValue();
        }
        return false;
    }

    public static String getLogFilePath() {
        if (LOG_FILE != null) {
            return LOG_FILE;
        }
        return "/sdcard/acc_log.txt";
    }


}
