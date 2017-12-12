package com.augur.zongyang.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by yunhu on 2017-12-11.
 */

public class SqliteTemplate {

    private final String mTable;
    public static SQLiteDatabase database;

    /**
     * Construct
     *
     * @param tableName
     *            table name
     */
    public SqliteTemplate(String tableName, Context context) {
        mTable = tableName;
        if (database == null) {
            // System.out.println("===database open===");
            database = new ImportDataFile(context).openDatabase();
        }
    }


    public static void closeDatabase() {
        if (database != null) {
            // System.out.println("===database close===");
            database.close();
            database = null;
        }
    }

    public static void initDataBase(Context context) {
        if (database == null) {
            database = new ImportDataFile(context).openDatabase();
            database.beginTransaction();
            database.setTransactionSuccessful();
            database.endTransaction();
        }
    }


}
