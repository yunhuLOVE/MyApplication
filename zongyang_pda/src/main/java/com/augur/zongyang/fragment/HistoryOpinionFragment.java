package com.augur.zongyang.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.augur.zongyang.R;
import com.augur.zongyang.manager.ToastManager;
import com.augur.zongyang.model.HistoryOpinionForm;
import com.augur.zongyang.model.TaskDetailInfoModel;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.tree.bean.Node;
import com.augur.zongyang.tree.bean.SimpleTreeAdapter;
import com.augur.zongyang.tree.bean.TreeFileBean;
import com.augur.zongyang.tree.bean.TreeListViewAdapter;
import com.augur.zongyang.util.StringUtil;
import com.augur.zongyang.util.asynctask.GetDataFromNetAsyncTask;
import com.augur.zongyang.util.constant.BundleKeyConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryOpinionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private String TAG = "AttachmentCatalogFragment";


    private Activity activity;
    private View fragmentView;

    private int type;

    //项目列表中任务信息
    private TaskDetailInfoModel taskData;

    private ListView mTree;

    private List<TreeFileBean> mDatas;
    private TreeListViewAdapter mAdapter;

    private List<HistoryOpinionForm> forms;
    private List<HistoryOpinionForm> itemForms;

    public HistoryOpinionFragment() {
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
        type = activity.getIntent().getExtras().getInt(BundleKeyConstant.TYPE, 0);
        taskData = (TaskDetailInfoModel) activity.getIntent().getExtras().getSerializable(BundleKeyConstant.DATA);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_history_opinion, container, false);
        mTree = fragmentView.findViewById(R.id.listView);
        searchData();
        return fragmentView;
    }

    /*
    查询历史意见数据
     */
    public void searchData() {
        new GetDataFromNetAsyncTask<>(activity,
                "",
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<List<HistoryOpinionForm>, String>() {
                    @Override
                    public List<HistoryOpinionForm> getResult(
                            String... strings) {
                        Map<Object, Object> paramMap = new HashMap<>();
                        paramMap.put("procInstId", taskData.getProcInstId());
                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(
                                        MyWorkHttpOpera.class).getHistoryOpinion(paramMap);
                    }

                    @Override
                    public void onSuccess(List<HistoryOpinionForm> resultData) {
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
        try {
            setData(forms, null);
            mAdapter = new SimpleTreeAdapter<>(mTree, activity,
                    mDatas, 3, handle, type);
            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if (node.isLeaf()) {

                        for (HistoryOpinionForm form : itemForms) {
                            if (form.getId().equals(node.getId())) {

                            }
                        }

                    }
                }
            });

            mAdapter.setOnTreeNodeLongClickListener(new TreeListViewAdapter.OnTreeNodeLongClickListener() {
                @Override
                public boolean onLongClick(Node node, int position) {
                    if (node.isLeaf()) {
                        for (HistoryOpinionForm form : itemForms) {
                            if (form.getId().equals(node.getId())) {

                            }
                        }
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

    public void setData(List<HistoryOpinionForm> forms, String mParentId) {
        if (forms == null)
            return;

        for (HistoryOpinionForm form : forms) {
            itemForms.add(form);
            //当前节点id
            String _id = StringUtil.getNotNullString(form.getId(), "");
            //父节点id
            String parentId = StringUtil.getNotNullString(mParentId, "");
            //节点名称
            String name = StringUtil.getNotNullString(form.getActivityChineseName(), "");

            String proId = "opinion";
            String opinionInfo = "";
            if (form.getAssignee() != null && form.getAssignee().length() > 0) {
                opinionInfo = form.getActivityChineseName() + "\n";
                opinionInfo += "   办理意见：" + form.getHandleComments() + "\n";
                opinionInfo += "   办理人：" + form.getAssignee() + "\n";
                opinionInfo += "   签收时间：" + form.getSignTime() + "\n";
                opinionInfo += "   结束时间：" + form.getEndDate();
            }

            String itemId = opinionInfo;

            mDatas.add(new TreeFileBean(_id, parentId, name, proId, itemId));
            if (form.getChildren() != null)
                setData(form.getChildren(), form.getId());
        }
    }

    Handler handle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
