package com.augur.zongyang.task;

/**
 * Created by yunhu on 2017-12-11.
 */

public interface TaskListener {

    String getName();

    void onPreExecute(GenericTask task);

    void onPostExecute(GenericTask task, TaskResult result);

    void onProgressUpdate(GenericTask task, Object param);

    void onCancelled(GenericTask task);


}
