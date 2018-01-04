package com.augur.zongyang.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yunhu on 2018-01-02.
 */

public class TimeUtil {
    public static final DateFormat forma1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat forma = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
    public static final DateFormat TWITTER_SEARCH_API_DATE_FORMATTER_EX = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat TWITTER_SEARCH_API_DATE_FORMATTER = new SimpleDateFormat(
            "yyyy年MM月dd日 HH时mm分ss秒");

    public static void MessageBox(final Activity activity, String tile, final String msg) {
        new AlertDialog.Builder(activity).setTitle(tile).setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(msg.contains("成功")){
//                            if(activity.getClass().equals(PatrolReportActivity.class))
//                                activity.finish();
                        }

                    }
                }).show();
    }

    public static final String dateToAppString(Date date) {
        try {
            if (date == null)
                return "";
            else
                return TWITTER_SEARCH_API_DATE_FORMATTER.format(date);
        } catch (Exception e) {
            // Log.w(TAG, "Could not format  date " );
            return null;
        }
    }

    public static final Date parseAppDateEx(String paramString)
    {
        try {
            return TWITTER_SEARCH_API_DATE_FORMATTER_EX.parse(paramString);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
