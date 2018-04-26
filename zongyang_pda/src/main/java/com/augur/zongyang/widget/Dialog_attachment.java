package com.augur.zongyang.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.augur.zongyang.bean.CurrentUser;
import com.augur.zongyang.common.Common;
import com.augur.zongyang.model.Response;
import com.augur.zongyang.network.api.WebServiceApi;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.tree.bean.Node;
import com.augur.zongyang.util.MyFileUtil;
import com.augur.zongyang.util.asynctask.CommonUploadDataToNetAsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yunhu on 2018-01-12.
 */

public class Dialog_attachment {

    private String TAG = "Dialog_attachment";

    private Activity activity;

    private Handler msgHandler;

    private MyHandler mhandler;

    private String filePath = null;//文件路径
    private String sysFileId;
    private String fileName;
    private String fileUrl;
    private static ProgressDialog pBar;
    private WebServiceApi api = null;
    private static long filesize;
    private static int downsize;

    public Dialog_attachment(Activity activity, Handler handle) {
        this.activity = activity;
        this.msgHandler = handle;
        this.api = WebServiceApi.getInstance(activity);
    }

    public void setDownloadDialog(Node node) {
        sysFileId = node.getId();
        fileName = node.getName();

        File dir = new File(Common.APP_ATTACHMENT);
        String tip;
        if (MyFileUtil.getAllFiles(dir, fileName)) {
            tip = "是否重新下载“";
        } else {
            tip = "是否下载“";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("提示"); // 设置标题
        builder.setMessage(tip + fileName + "”？"); // 设置内容
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() { // 设置确定按钮
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss(); // 关闭dialog

                        // getFilePath();
                        fileUrl = api.getAPI_IMAGEREAD()
                                + "?sysFileId=" + sysFileId;
                        Log.e(TAG, "fileUrl:" + fileUrl);
                        pBar = new ProgressDialog(activity);
                        pBar.setTitle("文件下载:");
                        pBar.setMessage(fileName);
//                        pBar.setProgressNumberFormat("%1d KB/%2d KB");
                        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        // 设置ProgressDialog 的进度条是否不明确
                        pBar.setIndeterminate(true);
                        pBar.setCanceledOnTouchOutside(false);// 点击空白处不消失
//                        //创建下载任务,downloadUrl就是下载链接
//                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
//                        //指定下载路径和下载文件名
//                        request.setDestinationInExternalPublicDir(Common.APP_ATTACHMENT, fileName);
//                        //获取下载管理器
//                        DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
//                        //将下载任务加入下载队列，否则不会进行下载
//                        downloadManager.enqueue(request);

                        downFile(fileUrl);

                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() { // 设置取消按钮
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                    }
                });

        // 参数都设置完成了，创建并显示出来
        builder.create().show();
    }

    public void setDeleteDialog(Node node) {
        sysFileId = node.getId();
        fileName = node.getName();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("提示"); // 设置标题
        builder.setMessage("是否删除文件“" + fileName + "”？"); // 设置内容
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() { // 设置确定按钮
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss(); // 关闭dialog

                        deleteFile(sysFileId);

                    }

                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() { // 设置取消按钮
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }

    File file;

    private void downFile(final String urlStr) {

        pBar.show();
        new Thread() {
            @Override
            public void run() {
                HttpClient client = new DefaultHttpClient();
                mhandler = new MyHandler();
                try {
                    HttpGet get = new HttpGet(urlStr);

                    //在请求中明确定义不要进行压缩
                    get.setHeader("Accept-Encoding", "identity");

                    HttpResponse response;
                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    filesize = entity.getContentLength();
                    Log.e(TAG, "filesize:" + filesize);
                    InputStream is = entity.getContent();
                    FileOutputStream fileOutputStream = null;
                    sendMsg(0);
                    if (is != null) {
                        if (!Common.isFolderExists(Common.APP_ATTACHMENT)) {
                            sendMsg(-2);
                            return;
                        }
                        file = new File(Common.APP_ATTACHMENT, fileName);
                        if (file.exists())
                            file.delete();
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buf = new byte[1024 * 10];
                        int ch;
                        downsize = 0;

                        while ((ch = is.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, ch);
                            downsize += ch;
                            sendMsg(downsize);
                            Thread.sleep(10);
                        }
                        pBar.dismiss();
                    }
                    if (fileOutputStream != null)
                        fileOutputStream.flush();
                    sendMsg(-4);
                    if (fileOutputStream != null)
                        fileOutputStream.close();
                    sendMsg(-3);
                    if (file != null) {
                        MyFileUtil.openFile(activity, file);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /*
    * 删除附件
    */
    private void deleteFile(final String sysFileId) {

        String loadingString = "正在删除文件中...";
        String successString = "删除文件成功";
        String failString = "删除文件失败";

        new CommonUploadDataToNetAsyncTask<>(
                activity,
                loadingString,
                successString,
                failString,
                new CommonUploadDataToNetAsyncTask.CommonUploadDataToNetAsyncTaskListener<String>() {

                    @Override
                    public Response doUpload(String... params) {
                        Long userId = (long) -1;
                        if (CurrentUser.getInstance().getCurrentUser() != null)
                            userId = CurrentUser.getInstance().getCurrentUser()
                                    .getUserId();
                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(
                                        MyWorkHttpOpera.class)
                                .getDeleteFile(userId, sysFileId);
                    }

                    @Override
                    public void onSuccess() {
                        Message msg = new Message();
                        msg.what = 1;
                        msgHandler.sendMessage(msg);//删除成功后，重新获取文件列表，刷新列表
                    }

                    @Override
                    public void onFail() {
                        Message msg = new Message();
                        msg.what = -1;
                        msgHandler.sendMessage(msg);
                    }
                }).execute();

    }



    class MyHandler extends Handler {


        private MyHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 0:
//                        pBar.setMax((int) filesize / (1024));
                        break;
                    case -4:
                        Toast.makeText(activity, "文件下载完成", Toast.LENGTH_SHORT).show();
                        break;
                    case -2:
                        Toast.makeText(activity, "文件路径建立失败", Toast.LENGTH_SHORT).show();
                        break;
                    case -3:
                        pBar.dismiss();
                        break;
                    case -1:
                        String error = msg.getData().getString("error");
                        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
                        break;
                    default:
//                        pBar.setProgress(msg.what / (1024));
                        break;
                }
            }
            super.handleMessage(msg);
        }
    }

    private void sendMsg(int flag) {
        Message msg = new Message();
        msg.what = flag;
        mhandler.sendMessage(msg);
    }


}
