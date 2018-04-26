package com.augur.zongyang.activity.supervision;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.augur.zongyang.R;
import com.augur.zongyang.activity.mywork.TaskTabActivity;
import com.augur.zongyang.adapter.SupervisionTaskAdapter;
import com.augur.zongyang.model.SupervisionProjectForm;
import com.augur.zongyang.model.result.SupervisionTaskListResult;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.util.SpinnerUtil;
import com.augur.zongyang.util.StringUtil;
import com.augur.zongyang.util.TimeUtil;
import com.augur.zongyang.util.asynctask.GetDataFromNetAsyncTask;
import com.augur.zongyang.util.constant.BundleKeyConstant;
import com.augur.zongyang.util.view.PullToRefreshLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 效能督查案件列表（查询）
 */
public class SupervisionListActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private Activity activity;

    private int type = 0;

    //标题
    private TextView title;
    //返回
    private ImageView back;

    //案件列表
    private ListView listView;
    private SupervisionTaskListResult result;
    private List<SupervisionProjectForm> datas;
    private SupervisionTaskAdapter adapter;

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
    //项目状态
    private Spinner sp_project_state;

    //查询按钮
    private Button btn_search;

    private boolean isSearch;

    public int page = 1, pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervision_list);
        activity = this;

        if(getIntent().getExtras() != null)
            type = getIntent().getExtras().getInt(BundleKeyConstant.TYPE,0);

        initView();
        initData();
    }

    private void initView() {

        title = findViewById(R.id.title);
        back = findViewById(R.id.iv_back);

        et_projectName = findViewById(R.id.et_projectName);
        sp_project_state = findViewById(R.id.sp_project_state);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        btn_search = findViewById(R.id.btn_search);

        ((PullToRefreshLayout) findViewById(R.id.refresh_view)).setOnRefreshListener(new MyListener());
        listView = findViewById(R.id.content_view);
    }

    private void initData() {

        if(getIntent().getExtras() != null)
            title.setText(getIntent().getExtras().getString(BundleKeyConstant.TITLE,"登记阶段"));
        back.setOnClickListener(this);

        /*
        初始化查询界面数据
         */
        initSearchData();

        datas = new ArrayList<>();
        adapter = new SupervisionTaskAdapter(this,datas,type);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.content_view:

                Bundle bundle = new Bundle();
                bundle.putInt(BundleKeyConstant.TYPE,4);
                bundle.putSerializable(BundleKeyConstant.DATA, datas.get(position));
                Intent intent = new Intent();
                intent.setClass(activity, TaskTabActivity.class);
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

        new GetDataFromNetAsyncTask<>(activity, null,
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<List<SupervisionTaskListResult>, String>() {
                    @Override
                    public List<SupervisionTaskListResult> getResult(String... params) {

                        Map<Object, Object> paramMap = new HashMap<>();

                        paramMap.put("type",type);

                        if(isSearch){
                            paramMap.put("projectName", StringUtil.getNotNullString(et_projectName.getText().toString(),""));
                            paramMap.put("createsDate",StringUtil.getNotNullString(startTime.getText().toString(),""));
                            paramMap.put("createeDate",StringUtil.getNotNullString(endTime.getText().toString(),""));
//                            paramMap.put("type",sp_project_state.getSelectedItemId());
                        }
                        paramMap.put("page", +page + "");
                        paramMap.put("limit", pageSize + "");
                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(MyWorkHttpOpera.class)
                                .getRegisterListOfSupervision(paramMap);
                    }

                    @Override
                    public void onSuccess(List<SupervisionTaskListResult> taskListResult) {
                        isSearch = false;
                        if(taskListResult != null){

                            if(sp_project_state != null && sp_project_state.getSelectedItem() != null)
                                title.setText(sp_project_state.getSelectedItem().toString());

                            result = taskListResult.get(0);
                            datas = taskListResult.get(0).getResult();
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
        String[] stateArr = getResources().getStringArray(R.array.project_state);
        List<String> list = new ArrayList<>();
        for(int i = 0; i < stateArr.length; i ++){
            list.add(stateArr[i]);
        }
        sp_project_state.setAdapter(SpinnerUtil.getCenterAdapter(activity,list));

        sp_project_state.setSelection(type);

        sp_project_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        String strEnd = TimeUtil.TWITTER_SEARCH_API_DATE_FORMATTER_EX.format(calendar.getTime());

        calendar.set(year, month - 1, day);
        String strStart = TimeUtil.TWITTER_SEARCH_API_DATE_FORMATTER_EX.format(calendar.getTime());

//        startTime.setText(strStart); // 显示一月前的年月日
//        endTime.setText(strEnd); //

        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

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
    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件刷新完毕了哦！
                    if (adapter != null)
                        adapter.notifyDataSetChanged();
                    page = 1;
                    if (datas != null)
                        datas.clear();
                    doSearch();
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作

            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件加载完毕了哦！
                    if (result != null && !result.isLastPage()) {
                        page += 1;
                        doSearch();
                    }
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }

    }
}
