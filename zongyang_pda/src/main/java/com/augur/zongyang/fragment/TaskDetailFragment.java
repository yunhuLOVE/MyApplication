package com.augur.zongyang.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augur.zongyang.R;
import com.augur.zongyang.bean.CurrentUser;
import com.augur.zongyang.model.TaskDetailInfoModel;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.util.asynctask.GetDataFromNetAsyncTask;
import com.augur.zongyang.util.constant.BundleKeyConstant;

public class TaskDetailFragment extends Fragment {

    private View fragmentView;

    private Activity activity;

    private TaskDetailInfoModel taskData;

    /*
    案件类型 0：待办，1：在办，2：已办
     */
    private int type;

    private OnFragmentInteractionListener mListener;

    public TaskDetailFragment() {
    }

    public static TaskDetailFragment newInstance(int position) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putInt(BundleKeyConstant.TYPE, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();

        type = getArguments().getInt(BundleKeyConstant.TYPE, 0);

        taskData = (TaskDetailInfoModel) getActivity().getIntent().getExtras().getSerializable(BundleKeyConstant.DATA);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_task_detail, container, false);

        initView();
        initData();

        return fragmentView;
    }

    private void initView() {

    }

    private void initData() {


        if (type == 0)
            signTask();
    }

    /*
    任务签收
     */
    private void signTask() {

        new GetDataFromNetAsyncTask<String, String>(activity, null,
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<String, String>() {
                    @Override
                    public String getResult(String... params) {

                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(MyWorkHttpOpera.class)
                                .getSignTask(taskData.getTaskInstDbid(),
                                        CurrentUser.getInstance()
                                                .getCurrentUser()
                                                .getLoginName());
                    }

                    @Override
                    public void onSuccess(String taskListResult) {

                    }

                    @Override
                    public void onFail() {

                    }
                }).execute();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
