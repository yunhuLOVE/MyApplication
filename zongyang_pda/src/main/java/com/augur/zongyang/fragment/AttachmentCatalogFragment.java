package com.augur.zongyang.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.augur.zongyang.R;
import com.augur.zongyang.bean.CurrentUser;
import com.augur.zongyang.manager.ToastManager;
import com.augur.zongyang.model.CustomTreeForm;
import com.augur.zongyang.model.Response;
import com.augur.zongyang.model.SupervisionProjectForm;
import com.augur.zongyang.model.TaskDetailInfoModel;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.tree.bean.Node;
import com.augur.zongyang.tree.bean.SimpleTreeAdapter;
import com.augur.zongyang.tree.bean.TreeFileBean;
import com.augur.zongyang.tree.bean.TreeListViewAdapter;
import com.augur.zongyang.util.LogUtil;
import com.augur.zongyang.util.MyFileUtil;
import com.augur.zongyang.util.StringUtil;
import com.augur.zongyang.util.asynctask.CommonUploadDataToNetAsyncTask;
import com.augur.zongyang.util.asynctask.GetDataFromNetAsyncTask;
import com.augur.zongyang.util.constant.BundleKeyConstant;
import com.augur.zongyang.widget.Dialog_attachment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 附件目录
 */
public class AttachmentCatalogFragment extends Fragment {

    private String TAG = "AttachmentCatalogFragment";

    private TextView filePathHint;

    private Activity activity;
    private View fragmentView;

    private int type;

    /*
    效能督查
     */
    private SupervisionProjectForm supervisitionInfo;

    //项目列表中任务信息
    private TaskDetailInfoModel taskData;

    private ListView mTree;

    private List<TreeFileBean> mDatas;
    private TreeListViewAdapter mAdapter;

    private List<CustomTreeForm> forms;
    private List<CustomTreeForm> itemForms;

    private Dialog_attachment attachmentDialog;

    private String fileName = null;
    private String filePath = null;
    private String fileExpand = null;
    private String selectedNodeId = null;
    private static final String TEMPNAME = "temp"; // 图片事例名

    public AttachmentCatalogFragment() {
        // Required empty public constructor
    }

    public static AttachmentCatalogFragment newInstance() {
        AttachmentCatalogFragment fragment = new AttachmentCatalogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

        //在API23+以上，不止要在AndroidManifest.xml里面添加权限
        MyFileUtil.verifyStoragePermissions(activity);

        type = activity.getIntent().getExtras().getInt(BundleKeyConstant.TYPE, 0);
        if (type == 4)//效能督查
            supervisitionInfo = (SupervisionProjectForm) activity.getIntent().getExtras().getSerializable(BundleKeyConstant.DATA);
        else
            taskData = (TaskDetailInfoModel) activity.getIntent().getExtras().getSerializable(BundleKeyConstant.DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_attachment_catalog, container, false);
        filePathHint = fragmentView.findViewById(R.id.filePath);
        mTree = fragmentView.findViewById(R.id.listView);

        filePathHint.setText("文件保存路径：文件管理/zongyang/attachment");
        searchData();
        return fragmentView;
    }

    public void searchData() {
        new GetDataFromNetAsyncTask<>(activity,
                "",
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<List<CustomTreeForm>, String>() {
                    @Override
                    public List<CustomTreeForm> getResult(
                            String... strings) {
                        Map<Object, Object> paramMap = new HashMap<>();
                        if(taskData != null) {
                            paramMap.put("templateCode", taskData.getTemplateCode());
                            paramMap.put("procInstId", taskData.getProcInstId());
                            paramMap.put("entityId", taskData.getMasterEntityKey().toString());
                            paramMap.put("viewName", "dzb");
                        }else if(supervisitionInfo != null){
                            paramMap.put("templateCode", supervisitionInfo.getTemplateCode());
                            paramMap.put("procInstId", supervisitionInfo.getProcInstanceId());
                            paramMap.put("entityId", supervisitionInfo.getId().toString());
                            paramMap.put("viewName", "dzb");
                        }
                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(
                                        MyWorkHttpOpera.class).getAttachmentCatalog(paramMap);
                    }

                    @Override
                    public void onSuccess(List<CustomTreeForm> resultData) {
                        if (resultData != null && resultData.size() > 0) {
                            forms = resultData;
                            initData();
                        }
                    }

                    @Override
                    public void onFail() {
                    }
                }).execute();
    }

    private void initData() {
        mDatas = new ArrayList<>();
        itemForms = new ArrayList<>();
        attachmentDialog = new Dialog_attachment(activity, handle);
        try {
            setData(forms, null);
            mAdapter = new SimpleTreeAdapter<>(mTree, activity,
                    mDatas, 3, handle, type);
            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if (node.isLeaf()) {
                        attachmentDialog.setDownloadDialog(node);
                    }
                }
            });

            mAdapter.setOnTreeNodeLongClickListener(new TreeListViewAdapter.OnTreeNodeLongClickListener() {
                @Override
                public boolean onLongClick(Node node, int position) {
                    if ((type == 0 || type == 1)&&node.isLeaf()) {
                        attachmentDialog.setDeleteDialog(node);
//                        for(CustomTreeForm form : itemForms){
//                            if(form.getId().equals(node.getId())){
//
//                            }
//                        }

                        return true;
                    }
                    return true;
                }
            });
            mTree.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private CustomTreeForm getItemByNodeId(String nodeId) {
        CustomTreeForm item = null;
        if (itemForms == null)
            return item;
        for (CustomTreeForm form : itemForms) {
            if (form.getId().equals(nodeId)) {
                item = form;
                return item;
            }
        }
        return item;
    }

    Handler handle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle data = msg.getData();
            switch (msg.what) {
                case 1:
                    ToastManager.getInstance(activity).shortToast("删除成功");
                    searchData();
                    break;
                case -1:
                    ToastManager.getInstance(activity).shortToast("删除失败");
                    break;
                case 2://上传
                    if (data != null && data.getString("id") != null)
                        selectedNodeId = data.getString("id");
                    showFileChooser();
                    break;
            }
            return true;
        }
    });

    public void setData(List<CustomTreeForm> forms, String mParentId) {
        if (forms == null)
            return;

        for (CustomTreeForm form : forms) {

            itemForms.add(form);
            //当前节点id
            String _id = StringUtil.getNotNullString(form.getId(), "");
            //父节点id
            String parentId = StringUtil.getNotNullString(mParentId, "");
            //节点名称
            String name = StringUtil.getNotNullString(form.getName(), "");

            String proId = StringUtil.getNotNullString(form.getProjectId(), "");

            String itemId = StringUtil.getNotNullString(form.getFileType(), "");
            mDatas.add(new TreeFileBean(_id, parentId, name, proId, itemId));
            if (form.getChildren() != null)
                setData(form.getChildren(), form.getId());
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");// 设置类型，我这里是任意类型，任意后缀的可以这样写。"*/*"
        // ContactsContract.Contacts.CONTENT_ITEM_TYPE
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
                    1);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:// 文件选择
                if (data != null) {
                    Uri uri = data.getData();
                    filePath = MyFileUtil.getPath(activity, uri);
//                    Log.e(TAG, "filePath:" + filePath);
                    if (filePath == null) {
                        Toast.makeText(activity, "选择文件失败，请重新选择", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        uploadDialog(filePath, "*");
                    }

//                    Log.e(TAG, "path:" + filePath);
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadDialog(final String filePath, final String type) {

        LayoutInflater factory = LayoutInflater.from(activity);
        final View textEntryView = factory.inflate(R.layout.dialog_edit, null);
        TextView tv_title = textEntryView.findViewById(R.id.tv_hint);
        tv_title.setText("请输入文件名称:");
        final EditText et_opinion = textEntryView
                .findViewById(R.id.editText);

        fileName = (new File(filePath)).getName();
        if (fileName.contains(".")) {
            int index = fileName.lastIndexOf(".");
            et_opinion.setText(fileName.substring(0, index));
            if (index > 0)
                fileExpand = fileName.substring(index + 1).toLowerCase();
        } else {
            et_opinion.setText(fileName);
        }

        AlertDialog.Builder dlg = new AlertDialog.Builder(activity);
        dlg.setTitle("确定上传文件“" + fileName + "”?");
        if (fileExpand != null)// && (fileExpand.equals("jpg") || fileExpand.equals("png") || fileExpand.equals("jpeg"))
            dlg.setView(textEntryView);
        dlg.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = et_opinion.getText().toString();
                if (name != null && name.length() > 0) {
                    name += "." + StringUtil.getNotNullString(fileExpand, "jpg");
                } else
                    name = fileName;
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
                        CustomTreeForm form = getItemByNodeId(selectedNodeId);
                        Map<Object, Object> paramMap = new HashMap<>();
                        paramMap.put("procInstId", StringUtil.getNotNullString(taskData.getProcInstId(), ""));

                        if (form.getId() != null && form.getId().contains("_")) {
                            String dirId = form.getId().substring(form.getId().lastIndexOf("_") + 1);
                            paramMap.put("dirId", dirId);
                        }

                        paramMap.put("activityChineseName", StringUtil.getNotNullString(taskData.getActivityChineseName(), ""));
                        paramMap.put("matterCode", StringUtil.getNotNullString(form.getMatterCode(), ""));
                        paramMap.put("taskInstDbid", StringUtil.getNotNullString(taskData.getTaskInstDbid(), ""));
                        paramMap.put("userId", userId.toString());
                        paramMap.put("entityId", StringUtil.getNotNullString(taskData.getMasterEntityKey(), ""));
                        LogUtil.info(activity, "paramMap:", paramMap);
                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(
                                        MyWorkHttpOpera.class)
                                .getUploadFile(paramMap, path, name);
                    }

                    @Override
                    public void onSuccess() {
                        searchData();//上传成功后，重新获取文件列表，刷新列表
                    }

                    @Override
                    public void onFail() {
                    }
                }).execute();
    }


}
