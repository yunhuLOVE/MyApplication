package com.augur.zongyang.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.augur.zongyang.R;

import java.io.File;

/**
 * Created by yunhu on 2017-12-11.
 */

public class Common {
    private static final String TAG = "Common";

    public static final String UPDATE_APKNAME = "ZONGYANG_PDA.apk";
    public static final String UPDATE_VERJSON = "ver.json";
    public static final String UPDATE_INFO = "update_info.json";

    public static final String SD_PATH = Environment
            .getExternalStorageDirectory().getPath();
    // 更新软件文件名字
    public static final String UPDATE_SAVENAME = "update_zongyang.apk";
    // 软件包名
    public static final String APP_PACKAGE = "com.augurit.zongyang_pda";

    public static final String APP_ROOT = SD_PATH + "/zongyang";

    public static final String APP_TMEP = SD_PATH + "/zongyang/temp";
    // 照片路径
    public static final String APP_PHOTO = SD_PATH + "/zongyang/image/";
    // 录像路径
    public static final String APP_VIDEO = SD_PATH + "/zongyang/video";

    //附件路径
    public static final String APP_ATTACHMENT = SD_PATH + "/zongyang/attachment";

    /**
     * 获得版本号
     *
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager()
                    .getPackageInfo(APP_PACKAGE, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return verCode;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager()
                    .getPackageInfo(APP_PACKAGE, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return verName;
    }

    /**
     * 获取应用名字
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        String verName = context.getResources()
                .getText(R.string.app_name).toString();

        return verName;
    }

    /**
     *
     * @param strFolder
     *            不存在时在SD卡上建立多级目录（"/sdcard/meido/audio/")：
     * @return
     */
    public static boolean isFolderExists(String strFolder) {
        File rootDir = new File(Common.APP_ROOT);
        if(!rootDir.exists()){
            rootDir.mkdirs();
        }
        File file = new File(strFolder);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return true;
            } else
                return false;
        }
        return true;
    }

//	public static int getSourceType(String name, Context context) {
//		String[] source = context.getResources().getStringArray(
//				R.array.casesource);
//		for (int i = 0; i < source.length; i++) {
//			if (source[i].equals(name))
//				return i;
//		}
//		return 0;
//	}


}
