package com.augur.zongyang.manager;

import android.content.Context;

import com.augur.zongyang.manager.base.BaseJsonManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Created by yunhu on 2017-12-11.
 */

public class JsonManager extends BaseJsonManager {

    private static JsonManager a;

    public static JsonManager getInstance() {
        if (a == null) {
            a = new JsonManager();
        }
        return a;
    }

    private static JsonManager instance;
    private final Gson gson;

    public static JsonManager getInstance(Context context) {
        if (instance == null) {
            instance = new JsonManager(context);
        }
        return instance;
    }

    private JsonManager() {
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    private JsonManager(Context context) {
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    public String getJson(Object object) {
        return this.gson.toJson(object);
    }

    public <T> T getObject(String jsonString, Class<T> classT) {
        T returnObject = null;
        try {
            returnObject = this.gson.fromJson(jsonString, classT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return returnObject;
    }

    public <T> T getObject(String jsonString, Type type) {
        T returnObject = null;
        try {
            returnObject = this.gson.fromJson(jsonString, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return returnObject;
    }


}
