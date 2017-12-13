package com.augur.zongyang.model;

import android.os.Bundle;

/**
 * Created by yunhu on 2017-12-13.
 */

public class MainManuItemData {

    private int imageId;//图标
    private int bgId;//背景色
    private int textId;//文字
    private int count;//案件数
    private Class classType;
    private Bundle bundle;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getBgId() {
        return bgId;
    }

    public void setBgId(int bgId) {
        this.bgId = bgId;
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }


}
