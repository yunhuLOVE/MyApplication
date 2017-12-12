package com.augur.zongyang.util.base;

import com.augur.zongyang.manager.base.BaseJsonManager;
import com.augur.zongyang.util.constant.AppLibConstant;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yunhu on 2017-12-11.
 */

public class BaseLogUtil {

    private static String a(Object paramObject,
                            BaseJsonManager paramBaseJsonManager) {
        if ((paramObject instanceof String)) {
            paramObject = (String) paramObject;
        } else {
            try {
                paramObject = paramBaseJsonManager.getJson(paramObject);
            } catch (Exception localException) {
                localException.printStackTrace();
                paramObject = "该类不符合规范，尝试传字符串类型";
            }
        }
        return (String) paramObject;
    }

    private static String a(int paramInt) {
        paramInt += 7;
        return ">>>"
                + Thread.currentThread().getStackTrace()[paramInt]
                .getClassName()
                + "."
                + Thread.currentThread().getStackTrace()[paramInt]
                .getMethodName();
    }

    protected static String getLogString(Object paramObject1,
                                         Object paramObject2, BaseJsonManager paramBaseJsonManager,
                                         int paramInt) {
        paramObject2 = a(paramObject2, paramBaseJsonManager);
        if (paramObject1 != null) {
            return a(paramObject1, paramBaseJsonManager) + "::"
                    + (String) paramObject2 + a(paramInt);
        }
        return paramObject2 + a(paramInt);
    }

    protected static String getLogString(Object paramObject1,
                                         Object paramObject2, BaseJsonManager paramBaseJsonManager) {
        paramObject2 = a(paramObject2, paramBaseJsonManager);
        if (paramObject1 != null) {
            return a(paramObject1, paramBaseJsonManager) + "::"
                    + (String) paramObject2 + a(0);
        }
        return paramObject2 + a(0);
    }

    protected static String getTagString(Object paramObject,
                                         BaseJsonManager paramBaseJsonManager) {
        if (paramObject == null) {
            return Thread.currentThread().getStackTrace()[5].getClassName();
        }
        if ((paramObject instanceof String)) {
            return (String) paramObject;
        }
        return paramObject.getClass().getName();
    }

    protected static void systemOut(String paramString) {
        System.out.println(paramString);
    }

    protected static void fileOut(String paramString) {
        Object localObject1;
        localObject1 = new File(AppLibConstant.getLogFilePath());
        if (!new File(AppLibConstant.getLogFilePath()).exists()) {
            try {
                new File(((File) localObject1).getParent()).mkdirs();
                ((File) localObject1).createNewFile();
            } catch (IOException localIOException1) {
                localIOException1.printStackTrace();
            }
        }
        Object localObject2 = null;
        try {
            localObject2 = new OutputStreamWriter(new DataOutputStream(
                    new FileOutputStream((File) localObject1, true)), "UTF-8");

            localObject1 = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss  "))
                    .format(new Date());
            ((OutputStreamWriter) localObject2).write((String) localObject1);
            ((OutputStreamWriter) localObject2).write(paramString + "\r\n");
            ((OutputStreamWriter) localObject2).flush();
        } catch (FileNotFoundException localFileNotFoundException) {
            if (localObject2 == null) {
                return;
            }
            try {
                ((OutputStreamWriter) localObject2).close();
            } catch (IOException localIOException2) {
                localObject1 = localIOException2;
                localIOException2.printStackTrace();
                return;
            }
        } catch (IOException localIOException3) {
            if (localObject2 == null) {
                return;
            }
            try {
                ((OutputStreamWriter) localObject2).close();
            } catch (IOException localIOException4) {
                localObject1 = localIOException4;
                localIOException4.printStackTrace();
                return;
            }
        } finally {
            if (localObject2 != null) {
                try {
                    ((OutputStreamWriter) localObject2).close();
                } catch (IOException localIOException5) {
                    localObject1 = localIOException5;
                    localIOException5.printStackTrace();
                }
            }
        }
        try {
            ((OutputStreamWriter) localObject2).close();
            return;
        } catch (IOException localIOException6) {
            localObject1 = localIOException6;
            localIOException6.printStackTrace();
        }
    }


}
