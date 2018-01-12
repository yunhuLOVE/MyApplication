package com.augur.zongyang.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.augur.zongyang.R;
import com.augur.zongyang.bean.CurrentUser;
import com.augur.zongyang.manager.ToastManager;
import com.augur.zongyang.model.CustomTreeForm;
import com.augur.zongyang.model.Response;
import com.augur.zongyang.model.TaskDetailInfoModel;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.tree.bean.Node;
import com.augur.zongyang.tree.bean.SimpleTreeAdapter;
import com.augur.zongyang.tree.bean.TreeFileBean;
import com.augur.zongyang.tree.bean.TreeListViewAdapter;
import com.augur.zongyang.util.StringUtil;
import com.augur.zongyang.util.asynctask.CommonUploadDataToNetAsyncTask;
import com.augur.zongyang.util.asynctask.GetDataFromNetAsyncTask;
import com.augur.zongyang.util.constant.BundleKeyConstant;
import com.augur.zongyang.widget.Dialog_attachment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *附件目录
 */
public class AttachmentCatalogFragment extends Fragment {

    private String TAG = "AttachmentCatalogFragment";


    private Activity activity;
    private View fragmentView;

    //项目列表中任务信息
    private TaskDetailInfoModel taskData;

    private ListView mTree;
    private List<TreeFileBean> mDatas;
    private TreeListViewAdapter mAdapter;
    private List<CustomTreeForm> forms;

    private Dialog_attachment attachmentDialog;



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
        taskData = (TaskDetailInfoModel) activity.getIntent().getExtras().getSerializable(BundleKeyConstant.DATA);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_attachment_catalog,container, false);
        mTree = fragmentView.findViewById(R.id.listView);
        searchData();
        return fragmentView;
    }

    public void searchData() {
        new GetDataFromNetAsyncTask<List<CustomTreeForm>, String>(activity,
                "",
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<List<CustomTreeForm>, String>() {
                    @Override
                    public List<CustomTreeForm> getResult(
                            String... strings) {
                        Long userId = -1L;
                        if (CurrentUser.getInstance().getCurrentUser() != null) {
                            userId = CurrentUser.getInstance().getCurrentUser()
                                    .getUserId();
                        }
                        Map<Object, Object> paramMap = new HashMap<Object, Object>();
                        paramMap.put("templateCode",taskData.getTemplateCode());
                        paramMap.put("procInstId",taskData.getProcInstId());
                        paramMap.put("entityId",taskData.getMasterEntityKey().toString());
                        paramMap.put("viewName","dzb");
                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(
                                        MyWorkHttpOpera.class).getAttachmentCatalog(paramMap);
                    }

                    @Override
                    public void onSuccess(List<CustomTreeForm> resultData) {
                        if (resultData != null && resultData.size() > 0) {
//							Log.e(TAG, "datas.size:" + resultData.size());
                            forms = resultData;
                            initData();
                        }
                    }

                    @Override
                    public void onFail() {
                    }
                }).execute();
    }

    private void initData(){
        mDatas = new ArrayList<>();
        attachmentDialog = new Dialog_attachment(activity,handle);
        try {
            setData(forms,null);
            mAdapter = new SimpleTreeAdapter<TreeFileBean>(mTree,activity,
                    mDatas, 0);
            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if(node.isLeaf()){

                        attachmentDialog.setDownloadDialog(node);

                    }
                }
            });

            mAdapter.setOnTreeNodeLongClickListener(new TreeListViewAdapter.OnTreeNodeLongClickListener() {
                @Override
                public boolean onLongClick(Node node, int position) {
                    if(node.isLeaf()){
                        attachmentDialog.setDeleteDialog(node);
                        return true;
                    }
                    return true;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    Handler handle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    ToastManager.getInstance(activity).shortToast("删除成功");
                    searchData();
                    break;
                case -1:
                    ToastManager.getInstance(activity).shortToast("删除失败");
                    break;
            }
            return true;
        }
    });

    public void setData(List<CustomTreeForm> forms,String mParentId) {
        if(forms == null)
            return;

        for(CustomTreeForm form : forms){
            //当前节点id
            String _id = StringUtil.getNotNullString(form.getId(), "");
            //父节点id
            String parentId = StringUtil.getNotNullString(mParentId, "");
            //节点名称
            String name = StringUtil.getNotNullString(form.getName(), "");


            String proId = StringUtil.getNotNullString(form.getProjectId(), "");

            String itemId = StringUtil.getNotNullString(form.getId(),"");
            mDatas.add(new TreeFileBean(_id, parentId, name, proId,itemId));
            if(form.getChildren()!=null)
                setData(form.getChildren(),form.getId());
        }
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
                        return NetworkHelper
                                .getInstance(activity)
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



}
