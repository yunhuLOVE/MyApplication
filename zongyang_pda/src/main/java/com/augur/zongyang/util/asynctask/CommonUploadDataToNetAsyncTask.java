package com.augur.zongyang.util.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.augur.zongyang.manager.ToastManager;
import com.augur.zongyang.model.Response;

/**
 * Created by yunhu on 2018-01-02.
 */

public class CommonUploadDataToNetAsyncTask<T> extends AsyncTask<T, Void, Response> {
    private ProgressDialog progressDialog;
    private final Context context;
    // private final String dataName;
    private final String loadingString;
    private final String successString;
    private final String failString;
    private final CommonUploadDataToNetAsyncTaskListener<T> getDataFromNetAsyncTaskListener;

    public CommonUploadDataToNetAsyncTask(
            Context context,
            String loadingString,
            String successString,
            String failString,
            CommonUploadDataToNetAsyncTaskListener<T> getDataFromNetAsyncTaskListener) {
        this.context = context;
        this.loadingString = loadingString;
        this.successString = successString;
        this.failString = failString;
        // this.dataName = dataName;
        this.getDataFromNetAsyncTaskListener = getDataFromNetAsyncTaskListener;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        this.dismissProgressDialog();
        if (response != null && response.isSuccess()) {

            if (response.getDescription() != null
                    && !response.getDescription().equals("")) {//返回结果描述不为空
                Toast.makeText(context, response.getDescription(), Toast.LENGTH_LONG).show();
//				ToastManager.getInstance(context).longToast(
//						response.getDescription());
            } else {
                if (successString != null && !successString.equals(""))
                    ToastManager.getInstance(context).shortToast(
                            this.successString);
            }

            this.getDataFromNetAsyncTaskListener.onSuccess();
        } else {

            if (response != null &&response.getDescription() != null
                    && !response.getDescription().equals("")) {//返回结果描述不为空
                ToastManager.getInstance(context).shortToast(
                        response.getDescription());
            } else {
                if (failString != null && !failString.equals(""))
                    ToastManager.getInstance(context).shortToast(
                            this.failString);
            }

            this.getDataFromNetAsyncTaskListener.onFail();
        }
    }

    @Override
    protected Response doInBackground(T... params) {
        return this.getDataFromNetAsyncTaskListener.doUpload(params);
    }

    private void showProgressString(String progressString) {
        progressDialog.setMessage(progressString);
    }

    private void dismissProgressDialog() {
        if(progressDialog != null)
            progressDialog.dismiss();
    }

    private void prepareAndShowProgressDialog() {
        progressDialog = new ProgressDialog(this.context);
        // progressDialog.setMessage("正在初始化公共数据...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(this.loadingString!=null&&this.loadingString.length()>0){
            this.prepareAndShowProgressDialog();
            this.showProgressString(this.loadingString);
        }

    }

    public interface CommonUploadDataToNetAsyncTaskListener<T> {
        public Response doUpload(T... params);

        public void onSuccess();

        public void onFail();
    }

}
