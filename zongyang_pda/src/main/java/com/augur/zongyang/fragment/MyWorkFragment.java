package com.augur.zongyang.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.augur.zongyang.R;
import com.augur.zongyang.activity.mywork.TaskDetailActivity;
import com.augur.zongyang.adapter.TaskListAdapter;
import com.augur.zongyang.bean.CurrentUser;
import com.augur.zongyang.model.TaskDetailInfoModel;
import com.augur.zongyang.model.result.TaskListResult;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.util.asynctask.GetDataFromNetAsyncTask;
import com.augur.zongyang.util.constant.BundleKeyConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunhu on 2017-12-13.
 */

public class MyWorkFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String TAG = "MyWorkFragment";

    private Activity activity;

    private View fragmentView;

    /*
    案件类型 0：待办，1：在办，2：已办
     */
    private int type;

    //案件列表
    private ListView listView;
    private List<TaskDetailInfoModel> datas;
    private TaskListAdapter adapter;

    public static MyWorkFragment newInstance(int position) {
        MyWorkFragment mFragment = new MyWorkFragment();

        Bundle args = new Bundle();
        args.putInt(BundleKeyConstant.POSITION, position);

        switch (position) {
            case 0://待办
                break;
            case 1://在办
                break;
            case 2://已办
                break;
            default:
                break;
        }

        mFragment.setArguments(args);

        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        Bundle args = getArguments();
        type = args.getInt(BundleKeyConstant.POSITION, 0);
//        Log.e(TAG,"type:"+type);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_my_work, container, false);

        initView();
        initData();

        return fragmentView;
    }

    private void initView() {

        listView = fragmentView.findViewById(R.id.listView);
    }

    private void initData() {

        datas = new ArrayList<>();
        adapter = new TaskListAdapter(this.getActivity(),datas,type);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.listView:

                Bundle bundle = new Bundle();
                bundle.putInt(BundleKeyConstant.TYPE,type);
                bundle.putSerializable(BundleKeyConstant.DATA, datas.get(position));
                Intent intent = new Intent();
                intent.setClass(MyWorkFragment.this.getContext(), TaskDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        doSearch();
    }

    /*
        获取数据列表
         */
    private void doSearch() {
        String dataName;
        switch (type) {
//            case 0://待办
//                dataName = "待办列表";
//                break;
//            case 1://在办
//                dataName = "在办列表";
//                break;
//            case 2://已办
//                dataName = "已办列表";
//                break;
            default:
                dataName = null;
                break;
        }

        new GetDataFromNetAsyncTask<TaskListResult, String>(MyWorkFragment.this.getContext(), dataName,
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<TaskListResult, String>() {
            @Override
            public TaskListResult getResult(String... params) {

                if (type == 1)
                    return NetworkHelper
                            .getInstance(MyWorkFragment.this.getContext())
                            .getHttpOpera(MyWorkHttpOpera.class)
                            .getDoingTaskList(
                                    CurrentUser.getInstance()
                                            .getCurrentUser()
                                            .getLoginName());

                return NetworkHelper
                        .getInstance(MyWorkFragment.this.getContext())
                        .getHttpOpera(MyWorkHttpOpera.class)
                        .getNotToDoTaskList(
                                CurrentUser.getInstance()
                                        .getCurrentUser()
                                        .getLoginName());
            }

            @Override
            public void onSuccess(TaskListResult taskListResult) {

                if(taskListResult.getResult() != null){
                    datas = taskListResult.getResult();
                    adapter.setData(datas);
                }

            }

            @Override
            public void onFail() {

            }
        }).execute();
    }
}
