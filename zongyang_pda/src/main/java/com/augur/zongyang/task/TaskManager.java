package com.augur.zongyang.task;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by yunhu on 2017-12-11.
 */

public class TaskManager extends Observable {

    private static final String TAG = "TaskManager";

    public static final Integer CANCEL_ALL = 1;

    public void cancelAll() {
        Log.i(TAG, "All task Cancelled.");
        super.setChanged();
        super.notifyObservers(CANCEL_ALL);
    }

    public void addTask(Observer task) {
        super.addObserver(task);
    }

}
