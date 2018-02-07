package com.augur.zongyang.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.augur.zongyang.common.Common;
import com.augur.zongyang.common.SettingPreference;
import com.augur.zongyang.network.api.WebServiceApi;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.OmUserHttpOpera;
import com.augur.zongyang.util.asynctask.GetDataFromNetAsyncTask;
import com.augur.zongyang.util.db.ImportDataFile;
import com.augur.zongyang.util.db.SqliteTemplate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yunhu on 2018-01-18.
 */

public class UpdateVersionUtil {

    private Activity activity;

    private WebServiceApi api = null;

    private int newVerCode = 0;
    private String newVerName = "";
    private long filesize;
    private int downsize;

    public ProgressDialog pBar;
    private final Handler handler = new Handler();

    public UpdateVersionUtil(Activity activity) {
        this.activity = activity;
        api = WebServiceApi.getInstance(activity);
    }

    // 检查服务端版本信息
    public void getVerData() {

        new GetDataFromNetAsyncTask<>(activity, "",
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<String, String>() {
                    @Override
                    public String getResult(String... strings) {
                        return NetworkHelper.getInstance(activity)
                                .getHttpOpera(OmUserHttpOpera.class)
                                .getVerson(Common.UPDATE_VERJSON);

                    }

                    @Override
                    public void onSuccess(String resultData) {
                        if (resultData != null) {
                            UpdateVer(resultData);
                        }

                    }

                    @Override
                    public void onFail() {
                    }
                }).execute();

    }

    public void UpdateVer(String str) {
        try {
            JSONArray array = new JSONArray(str);

            if (array.length() > 0) {
                JSONObject obj = array.getJSONObject(0);
                try {
                    newVerCode = Integer.valueOf(obj.getString("verCode"));
                    newVerName = obj.getString("verName");
                } catch (Exception e) {
                    newVerCode = -1;
                    newVerName = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int vercode = Common.getVerCode(activity);
        if (newVerCode > vercode) {
            // getVerConfiguration();
            getVerBy();
        } else {
            // notNewVersionShow();
        }
    }

    // 获取服务端版本更新信息
    private void getVerBy() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    String verjson = NetworkTool.getContent(api.getAPI_UPFILE()
                            + Common.UPDATE_INFO);
                    JSONArray array = new JSONArray(verjson);

                    if (array.length() > 0) {
                        JSONObject obj = array.getJSONObject(0);
                        String updateTitle = obj.getString("updateTitle");
                        String updateContent = obj.getString("updateContent");
                        updateContent = updateContent.substring(0,
                                updateContent.length() - 2);
                        Message msg = new Message();
                        msg.what = 0;
                        Bundle bundle = new Bundle();
                        bundle.putString("updateTitle", updateTitle);
                        bundle.putString("updateContent", updateContent);
                        msg.setData(bundle);
                        myHandler.sendMessage(msg);
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle data = msg.getData();
            switch (msg.what) {
                case 0:
                    String updateTitle = data.getString("updateTitle");
                    String updateContent = data.getString("updateContent");
                    doNewVersionUpdate(updateTitle, updateContent);
                    break;
            }
            return true;
        }
    });

    private void doNewVersionUpdate(String title, String content) {
        String verName = Common.getVerName(activity);
        StringBuffer sb = new StringBuffer();
        sb.append("当前版本:");
        sb.append(verName);
        sb.append(", 发现新版本:");
        sb.append(newVerName + "\n");
        sb.append(content + "\n");
        sb.append("(更新需要清除本地待上报的案件，请确保待上报案件都已上传)");
        Dialog dialog_update = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(sb.toString())
                // 设置内容
                .setPositiveButton("更新",// 设置确定按钮
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                pBar = new ProgressDialog(activity);
                                pBar.setTitle("系统正在更新");
                                pBar.setMessage("请稍候...");
                                pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                // 设置ProgressDialog 的进度条是否不明确
                                pBar.setIndeterminate(false);
                                pBar.setCanceledOnTouchOutside(false);// 点击空白处不消失

                                File file = new File(MyFileUtil.getPathRoot());
                                MyFileUtil.DeleteFile(file);
                                SettingPreference settingPreference = new SettingPreference(activity);
                                // String updateDBStr =
                                // SettingPreference.DB_UPDATE;
                                settingPreference.getPreferences().edit()
                                        .putBoolean("dbupdate", true).commit();
                                downFile(api.getAPI_UPFILE()
                                        + Common.UPDATE_APKNAME);
                            }

                        })
                .setNegativeButton("暂不更新",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 点击"取消"按钮之后退出程序
                                // finish();
                            }
                        }).create();// 创建
        // 显示对话框
        dialog_update.show();
    }

    void downFile(final String url) {
        pBar.show();
        new Thread() {
            @Override
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                HttpResponse response;
                try {
                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    filesize = entity.getContentLength();
                    InputStream is = entity.getContent();
                    FileOutputStream fileOutputStream = null;
                    sendMsg(0);
                    if (is != null) {

                        if (!Common.isFolderExists(Common.APP_TMEP)) {
                            sendMsg(-2);
                            return;
                        }
                        File file = new File(Common.APP_TMEP,
                                Common.UPDATE_SAVENAME);
                        if (file.exists()) {
                            file.delete();
                        }
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buf = new byte[1024 * 10];
                        int ch = -1;
                        downsize = 0;
                        while ((ch = is.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, ch);
                            downsize += ch;
                            if (filesize > 0) {
                            }
                            sendMsg(downsize);
                            Thread.sleep(10);
                        }
                    }
                    fileOutputStream.flush();
                    sendMsg(-4);
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    down();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void sendMsg(int flag) {
        Message msg = new Message();
        msg.what = flag;
        mhandler.sendMessage(msg);
    }

    void down() {
        sendMsg(-3);
        handler.post(new Runnable() {
            @Override
            public void run() {
                beforUpdate();
                update();
            }
        });
    }

    void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.fromFile(new File(Common.APP_TMEP, Common.UPDATE_SAVENAME)),
                "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }

    void beforUpdate() {
        SqliteTemplate.closeDatabase();
        activity.deleteDatabase(ImportDataFile.getDBFileName());

        String updateDBStr = SettingPreference.DB_UPDATE;
        SettingPreference settingPreference = new SettingPreference(activity);
        settingPreference.getPreferences().edit().putBoolean(updateDBStr, true)
                .commit();
    }

    Handler mhandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 0:
                        pBar.setMax((int) filesize);
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
                        pBar.setProgress(msg.what);
                        break;
                }

            }
            return true;
        }
    });

}
