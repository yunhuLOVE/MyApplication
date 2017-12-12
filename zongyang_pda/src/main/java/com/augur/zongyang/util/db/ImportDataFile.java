package com.augur.zongyang.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.augur.zongyang.R;
import com.augur.zongyang.common.Common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * MyArcGISWebServices 主要把APK里面的数据文件导入到系统存放数据文件的路径下面
 * Created by yunhu on 2017-12-11.
 */

public class ImportDataFile {
    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "huaianpda.db"; // 保存的数据库文件名
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + Common.APP_PACKAGE+"/"+"databases"; // 在手机里存放数据库的位置

    private SQLiteDatabase database;
    private final Context context;

    public ImportDataFile(Context context) {
        this.context = context;
    }

    public SQLiteDatabase openDatabase() {
        database = this.openDatabase(DB_PATH + "/" + DB_NAME);
        return database;
    }

    private SQLiteDatabase openDatabase(String dbfile) {
        try {
            if (!(new File(dbfile).exists())) {
                // 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                InputStream is = this.context.getResources().openRawResource(
                        R.raw.zongyangpda);
                // 欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
                    null);
            return db;
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteDBFile() {
        String dbFilePath = DB_PATH + "/" + DB_NAME;
        new File(dbFilePath).delete();
    }

    private void closeDatabase() {
        this.database.close();
    }

    public SQLiteDatabase getDB() {
        return database;
    }

    public static String getDBFileName() {
        return DB_PATH + "/" + DB_NAME;
    }
}
