package com.augur.zongyang.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by yunhu on 2017-12-11.
 */

public class SettingPreference {

    public static final String DB_UPDATE = "db_update";
    private SharedPreferences setting;
    private Context context;

    public SettingPreference(Context context) {
        this.context = context;
    }

    public SharedPreferences getPreferences() {
        if (setting == null) {
            setting = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return setting;
    }


}
