package com.augur.zongyang.task;

/**
 * Created by yunhu on 2017-12-11.
 */

public abstract class TaskAdapter implements TaskListener {

    public abstract String getName();

    public void onPreExecute(GenericTask task) {
    };

    public void onPostExecute(GenericTask task, TaskResult result) {
    };

    public void onProgressUpdate(GenericTask task, Object param) {
    };

    public void onCancelled(GenericTask task) {
    };

}
