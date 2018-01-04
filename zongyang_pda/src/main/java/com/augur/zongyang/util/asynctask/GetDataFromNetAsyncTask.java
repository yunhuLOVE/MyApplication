package com.augur.zongyang.util.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.augur.zongyang.manager.ToastManager;

/**
 * Created by yunhu on 2017-12-19.
 */

public class GetDataFromNetAsyncTask<T, K> extends AsyncTask<K, Void, T> {

    private ProgressDialog progressDialog;
    private final Context context;
    private final String dataName;
    private final GetDataFromNetAsyncTaskListener<T, K> getDataFromNetAsyncTaskListener;

    public GetDataFromNetAsyncTask(
            Context context,
            String dataName,
            GetDataFromNetAsyncTaskListener<T, K> getDataFromNetAsyncTaskListener
    ) {
        this.context = context;
        this.dataName = dataName;
        this.getDataFromNetAsyncTaskListener = getDataFromNetAsyncTaskListener;
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        if (t != null) {
            if (dataName != null && this.dataName.length() > 0)
                ToastManager.getInstance(context).shortToast(
                        this.dataName + "加载成功");
            this.getDataFromNetAsyncTaskListener.onSuccess(t);
        } else {
            if (dataName != null && dataName.length() > 0)
                ToastManager.getInstance(context).shortToast(
                        this.dataName + "加载失败");
            this.getDataFromNetAsyncTaskListener.onFail();

        }
        this.dismissProgressDialog();
    }

    @Override
    protected T doInBackground(K... params) {
        return this.getDataFromNetAsyncTaskListener.getResult(params);
    }

    private void showProgressString(String progressString) {
        progressDialog.setMessage(progressString);
    }

    private void dismissProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void prepareAndShowProgressDialog() {

        progressDialog = new ProgressDialog(this.context);
        this.showProgressString("正在联网获取" + this.dataName + "中...");
        // progressDialog.setMessage("正在初始化公共数据...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (this.dataName != null && this.dataName.length() > 0)
            this.prepareAndShowProgressDialog();
    }

    public interface GetDataFromNetAsyncTaskListener<T, K> {
        public T getResult(K... params);

        // public void onReceiveResult(T t);

        public void onSuccess(T t);

        public void onFail();
    }


}
