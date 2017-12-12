package com.augur.zongyang.manager.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Created by yunhu on 2017-12-11.
 */

public class BaseJsonManager {

    private final Gson a = new GsonBuilder().setDateFormat(
            "yyyy-MM-dd HH:mm:ss").create();

    public String getJson(Object paramObject) {
        return this.a.toJson(paramObject);
    }

    public <T> T getObject(String paramString, Class<T> paramClass) {
        Object localObject = null;
        try {
            localObject = this.a.fromJson(paramString, paramClass);
        } catch (JsonSyntaxException localJsonSyntaxException) {
            localJsonSyntaxException.printStackTrace();
        }
        return (T) localObject;
    }

    public <T> T getObject(String paramString, Type paramType) {
        Object localObject = null;
        try {
            localObject = this.a.fromJson(paramString, paramType);
        } catch (JsonSyntaxException localJsonSyntaxException) {
            localJsonSyntaxException.printStackTrace();
        }
        return (T) localObject;
    }


}
