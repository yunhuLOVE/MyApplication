package com.augur.zongyang.util;

/**
 * Created by yunhu on 2017-12-25.
 */

public class StringUtil {
    public static boolean isEmpty(String string) {
        return string == null || "".equals(string.trim());
    }

    public static String getNotNullString(Object stringObject,
                                          String defaultString) {
        return stringObject == null ? defaultString : stringObject.toString();
    }

}
