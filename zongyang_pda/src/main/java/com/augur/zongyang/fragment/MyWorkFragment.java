package com.augur.zongyang.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.augur.zongyang.R;
import com.augur.zongyang.activity.mywork.TaskTabActivity;
import com.augur.zongyang.adapter.TaskListAdapter;
import com.augur.zongyang.bean.CurrentUser;
import com.augur.zongyang.model.TaskDetailInfoModel;
import com.augur.zongyang.model.result.TaskListResult;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.util.StringUtil;
import com.augur.zongyang.util.TimeUtil;
import com.augur.zongyang.util.asynctask.GetDataFromNetAsyncTask;
import com.augur.zongyang.util.constant.BundleKeyConstant;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yunhu on 2017-12-13.
 */

public class MyWorkFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener {

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

    // 记录当前时间的变量
    private int year;
    private int month;
    private int day;
    private int flag = 0;

    //开始时间
    private TextView startTime;
    //结束时间
    private TextView endTime;
    //项目名称
    private EditText et_projectName;
    //流程类别
    private EditText et_processCategory;
    //查询按钮
    private Button btn_search;

    private boolean isSearch;

    public static MyWorkFragment newInstance(int position) {
        MyWorkFragment mFragment = new MyWorkFragment();

        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        type = getArguments().getInt(BundleKeyConstant.TYPE, -1);
        Log.e(TAG,"type:"+type);
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

        et_projectName = fragmentView.findViewById(R.id.et_projectName);
        et_processCategory = fragmentView.findViewById(R.id.et_processCategory);
        startTime = fragmentView.findViewById(R.id.startTime);
        endTime = fragmentView.findViewById(R.id.endTime);
        btn_search = fragmentView.findViewById(R.id.btn_search);

        listView = fragmentView.findViewById(R.id.listView);
    }

    private void initData() {
        /*
        初始化查询界面数据
         */
        initSearchData();

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
                intent.setClass(MyWorkFragment.this.getContext(), TaskTabActivity.class);
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

        new GetDataFromNetAsyncTask<>(MyWorkFragment.this.getContext(), null,
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<TaskListResult, String>() {
            @Override
            public TaskListResult getResult(String... params) {

//                Log.e(TAG,"type:"+type);

                Map<Object, Object> paramMap = new HashMap<>();
                paramMap.put("loginName", CurrentUser.getInstance()
                        .getCurrentUser()
                        .getLoginName());

                if(isSearch){
                    paramMap.put("projectName",StringUtil.getNotNullString(et_projectName.getText().toString(),""));
                    paramMap.put("templateName", StringUtil.getNotNullString(et_processCategory.getText().toString(),""));
                    paramMap.put("createSDate",StringUtil.getNotNullString(startTime.getText().toString(),""));
                    paramMap.put("createEDate",StringUtil.getNotNullString(endTime.getText().toString(),""));
                }


                if (type == 1)
                    return NetworkHelper
                            .getInstance(MyWorkFragment.this.getContext())
                            .getHttpOpera(MyWorkHttpOpera.class)
                            .getDoingTaskList(paramMap);

                if(type == 3)
                    return NetworkHelper
                            .getInstance(MyWorkFragment.this.getContext())
                            .getHttpOpera(MyWorkHttpOpera.class)
                            .getAllDealTaskList(paramMap);

                return NetworkHelper
                        .getInstance(MyWorkFragment.this.getContext())
                        .getHttpOpera(MyWorkHttpOpera.class)
                        .getNotToDoTaskList(paramMap);
            }

            @Override
            public void onSuccess(TaskListResult taskListResult) {
                isSearch = false;
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

    /*
        初始化查询界面数据
         */
    private void initSearchData(){
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        String strEnd = TimeUtil.TWITTER_SEARCH_API_DATE_FORMATTER_EX.format(calendar.getTime());

        calendar.set(year, month - 1, day);
        String strStart = TimeUtil.TWITTER_SEARCH_API_DATE_FORMATTER_EX.format(calendar.getTime());

        startTime.setText(strStart); // 显示一月前的年月日
        endTime.setText(strEnd); //

        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startTime://开始时间
                flag = 0;
                try {
                    Date date0 = TimeUtil.TWITTER_SEARCH_API_DATE_FORMATTER_EX
                            .parse(startTime.getText().toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date0);
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DATE);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                DatePickerDialog dpd = new DatePickerDialog(
                        activity, Datelistener,
                        year, month, day);
                dpd.show();// 显示DatePickerDialog组件
                break;
            case R.id.endTime://结束时间
                flag = 1;
                try {
                    Date date0 = TimeUtil.TWITTER_SEARCH_API_DATE_FORMATTER_EX
                            .parse(endTime.getText().toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date0);
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DATE);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                DatePickerDialog dpd1 = new DatePickerDialog(
                        activity, Datelistener,
                        year, month, day);
                dpd1.show();// 显示DatePickerDialog组件
                break;
            case R.id.btn_search:
                isSearch = true;
                doSearch();
                break;
        }
    }

    private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {
        /**
         * params：view：该事件关联的组件 params：myyear：当前选择的年 params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear,
                              int dayOfMonth) {

            // 修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            year = myyear;
            month = monthOfYear;
            day = dayOfMonth;
            // 更新日期
            updateDate();

        }

        // 当DatePickerDialog关闭时，更新日期显示
        private void updateDate() {

            Calendar calendar = Calendar.getInstance();

            calendar.set(year, month, day);
            String timeStr = TimeUtil.TWITTER_SEARCH_API_DATE_FORMATTER_EX.format(calendar.getTime());


            // 在TextView上显示日期
            if (flag == 0) {
                startTime.setText(timeStr);
            }

            if (flag == 1) {
                endTime.setText(timeStr);
            }

        }
    };
}
