package com.augur.zongyang.activity.mywork;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.augur.zongyang.R;
import com.augur.zongyang.adapter.AttachmentListAdapter;
import com.augur.zongyang.bean.CurrentUser;
import com.augur.zongyang.common.Common;
import com.augur.zongyang.model.Response;
import com.augur.zongyang.model.SysFileForm;
import com.augur.zongyang.model.result.SysFileFormResult;
import com.augur.zongyang.network.api.WebServiceApi;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.util.MyFileUtil;
import com.augur.zongyang.util.asynctask.CommonUploadDataToNetAsyncTask;
import com.augur.zongyang.util.asynctask.GetDataFromNetAsyncTask;

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
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AttachmentListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private String TAG = "AttachmentListActivity";

    private static final int FILE_SELECT_CODE = 1;// 选择文件
    private static final int TAKE_PHOTO_CODE = 2;// 拍照

    private Context context;

    private ImageView back;//返回
    private TextView title;//标题

    private Button btn_take_photo, btn_upload;//拍照，上传

    private ListView listView;
    private AttachmentListAdapter adapter;
    private List<SysFileForm> listData;
    private SysFileFormResult result;

    private TextView pickFilePath;//上传文件路径
    private TextView saveFilePath;//文件存储路径

    private String filePath = null;//文件路径

    private MyHandler mhandler;
    private Long sysFileId;
    private String fileName;
    private String fileUrl;
    private static ProgressDialog pBar;
    private WebServiceApi api = null;
    private static long filesize;
    private static int downsize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attachment_list);
        context = this;

        initView();
        initData();
    }

    private void initView() {
        back = findViewById(R.id.iv_back);
        title = findViewById(R.id.title);

        btn_take_photo = findViewById(R.id.btn_take_photo);
        btn_upload = findViewById(R.id.btn_upload);

        pickFilePath = findViewById(R.id.tv_upload_path);
        saveFilePath = findViewById(R.id.tv_save_path);

        listView = findViewById(R.id.list_attachment);
    }

    private void initData() {
        title.setText("附件列表");

        saveFilePath.setText("下载路径:" + Common.APP_ATTACHMENT);

        listData = new ArrayList<>();
        adapter = new AttachmentListAdapter(context, listData);
        listView.setAdapter(adapter);

        mhandler = new MyHandler(this);

        getAttachmentListData();//获取文件列表

        back.setOnClickListener(this);
        btn_take_photo.setOnClickListener(this);
        btn_upload.setOnClickListener(this);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    /*
     * 获取附件列表
	 */
    private void getAttachmentListData() {
        // TODO Auto-generated method stub
        new GetDataFromNetAsyncTask<>(
                this,
                "",
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<SysFileFormResult, String>() {
                    @Override
                    public SysFileFormResult getResult(String... strings) {
                        return NetworkHelper
                                .getInstance(context)
                                .getHttpOpera(
                                        MyWorkHttpOpera.class)
                                .getAttachmentList("entityId", "entity", 1,
                                        50);

                    }

                    @Override
                    public void onSuccess(SysFileFormResult resultData) {
                        if (resultData != null) {
                            result = resultData;
                            initAttachmentList();
                        }
                    }

                    @Override
                    public void onFail() {
                    }
                }).execute();
    }

    private void initAttachmentList() {
        // TODO Auto-generated method stub
        if (result.getResult() != null) {
            listData = result.getResult();
            adapter.setData(listData);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_take_photo:
                String fileName = System.currentTimeMillis() + ".jpg";
                filePath = MyFileUtil.getFileName(fileName);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(filePath);
                Uri imageUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO_CODE);
                break;
            case R.id.btn_upload:
                showFileChooser();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        sysFileId = listData.get(position).getSysFileId();
        fileName = listData.get(position).getFileName();

        File dir = new File(Common.APP_ATTACHMENT);
        String tip;
        if (MyFileUtil.getAllFiles(dir, fileName)) {
            tip = "是否重新下载“";
        } else {
            tip = "是否下载“";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                        // fileUrl = api.getAgSupportUrl() + resultData;
                        Log.e(TAG, "fileUrl:" + fileUrl);
                        pBar = new ProgressDialog(context);
                        pBar.setTitle("文件下载:");
                        pBar.setMessage(fileName);
                        pBar.setProgressNumberFormat("%1d KB/%2d KB");
                        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        // 设置ProgressDialog 的进度条是否不明确
                        pBar.setIndeterminate(false);
                        pBar.setCanceledOnTouchOutside(false);// 点击空白处不消失

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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        sysFileId = listData.get(position).getSysFileId();
        fileName = listData.get(position).getFileName();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        return true;
    }

    /*
        选择文件
         */
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");// 设置类型，我这里是任意类型，任意后缀的可以这样写。"*/*"
        // ContactsContract.Contacts.CONTENT_ITEM_TYPE
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:// 文件选择
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    filePath = MyFileUtil.getPath(this, uri);
                    Log.e(TAG, "filePath:" + filePath);
                    if (filePath == null) {
                        Toast.makeText(context, "选择文件失败，请重新选择", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        uploadDialog(filePath, "*");
                    }

                    Log.e(TAG, "path:" + filePath);
                }
                break;

            case TAKE_PHOTO_CODE:// 拍照
                uploadDialog(filePath, "jpg");
//			}
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void uploadDialog(final String filePath, final String type) {

        LayoutInflater factory = LayoutInflater.from(context);
        final View textEntryView = factory.inflate(R.layout.dialog_edit, null);
        TextView tv_title = textEntryView.findViewById(R.id.tv_hint);
        tv_title.setText("请输入图片名称:");
        final EditText et_opinion = textEntryView
                .findViewById(R.id.editText);

        String fileName = (new File(filePath)).getName();
        Log.e(TAG, "fileName:" + fileName);
        pickFilePath.setVisibility(View.VISIBLE);
        pickFilePath.setText("上传文件路径:" + filePath);
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle("确定上传文件“" + fileName + "”?");
        if (type.equals("jpg"))
            dlg.setView(textEntryView);
        dlg.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = et_opinion.getText().toString();
                if (name.length() > 0)
                    name += ".jpg";

                if (!type.equals("jpg"))
                    name = null;
                uploadFile(filePath, name);
            }
        });
        dlg.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        }).create();
        dlg.show();
    }

    private void uploadFile(final String path, final String name) {
        String loadingString = "正在上传文件中...";
        String successString = "上传文件成功";
        String failString = "上传文件失败";
        new CommonUploadDataToNetAsyncTask<>(
                context,
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
                                .getInstance(context)
                                .getHttpOpera(
                                        MyWorkHttpOpera.class)
                                .getUploadFile(userId, "entityId", "entity", path, name);
                    }

                    @Override
                    public void onSuccess() {
//                        getAttachmentListData();//上传成功后，重新获取文件列表，刷新列表
                    }

                    @Override
                    public void onFail() {
                    }
                }).execute();
    }

    File file;

    private void downFile(final String url) {
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
                    }
                    if (fileOutputStream != null)
                        fileOutputStream.flush();
                    sendMsg(-4);
                    if (fileOutputStream != null)
                        fileOutputStream.close();
                    sendMsg(-3);
                    if (file != null) {
                        MyFileUtil.openFile(context, file);
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

    static class MyHandler extends Handler {

        WeakReference<AttachmentListActivity> mActivity;

        private MyHandler(AttachmentListActivity activity) {
            this.mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            AttachmentListActivity activity = mActivity.get();
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 0:
                        pBar.setMax((int) filesize / (1024));
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
                        pBar.setProgress(msg.what / (1024));
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

    /*
     * 删除附件
	 */
    private void deleteFile(final Long sysFileId) {

        String loadingString = "正在删除文件中...";
        String successString = "删除文件成功";
        String failString = "删除文件失败";

        new CommonUploadDataToNetAsyncTask<>(
                context,
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
                                .getInstance(context)
                                .getHttpOpera(
                                        MyWorkHttpOpera.class)
                                .getDeleteFile(userId, sysFileId);
                    }

                    @Override
                    public void onSuccess() {
                        getAttachmentListData();//删除成功后，重新获取文件列表，刷新列表
                    }

                    @Override
                    public void onFail() {
                    }
                }).execute();

    }
}
