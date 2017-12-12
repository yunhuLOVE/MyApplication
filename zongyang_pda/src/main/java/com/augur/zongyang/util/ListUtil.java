package com.augur.zongyang.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunhu on 2017-12-11.
 */

public class ListUtil {

    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static <T> List<T> makeSureInit(List<T> ts) {
        if (ts == null) {
            ts = new ArrayList<T>();
        }
        return ts;
    }

    public static <T> List<T> getList(T... ts) {
        List<T> resultTs = new ArrayList<T>();
        for (T t : ts) {
            resultTs.add(t);
        }
        return resultTs;
    }

    public static <T> boolean isContainString(List<T> stringObjects,
                                              Object stringObject) {
        if (ListUtil.isEmpty(stringObjects) || stringObject == null) {
            return false;
        }
        for (T tempStringObject : stringObjects) {
            if (stringObject.toString().equals(tempStringObject.toString())) {
                return true;
            }
        }
        return false;
    }

    public static <T> void addAll(List<T> resultTs, List<T> toAddTs) {
        if (resultTs != null && !ListUtil.isEmpty(toAddTs)) {
            resultTs.addAll(toAddTs);
        }
    }

    public static List<String> arrayToList(String[] array,List<String> list){
        if(list == null)
            list = new ArrayList<String>();
        if(array == null)
            return list;
        for(String str : array){
            list.add(str);
        }
        return list;
    }

    /*
     * 获取字符串在list集合中的位置
     */
    public static int getPositionWithList(String arg, List<String> list) {
        int position = 0;
        if(arg != null && list != null){
            for (int i = 0; i < list.size(); i++) {
                if (arg.equals(list.get(i))) {
                    position = i;
                    break;
                }
            }
        }
        return position;
    }


}
