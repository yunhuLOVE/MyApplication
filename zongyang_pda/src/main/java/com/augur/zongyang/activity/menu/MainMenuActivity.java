package com.augur.zongyang.activity.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.augur.zongyang.R;
import com.augur.zongyang.activity.login.LoginActivity;
import com.augur.zongyang.activity.mywork.MyWorkTabActivity;
import com.augur.zongyang.adapter.MainMenuAdapter;
import com.augur.zongyang.bean.CurrentUser;
import com.augur.zongyang.model.MainManuItemData;
import com.augur.zongyang.model.result.TaskListResult;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.util.UpdateVersionUtil;
import com.augur.zongyang.util.asynctask.GetDataFromNetAsyncTask;
import com.augur.zongyang.util.constant.BundleKeyConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yunhu on 2017-12-11.
 */

public class MainMenuActivity extends AppCompatActivity {

    private String TAG = "MainMenuActivity";

    //上下文
    private Context mContext;
    //标题
    private TextView title;
    //返回
    private ImageView back;
    //网格
    private GridView gridView;

    //菜单填充数据
    private List<MainManuItemData> mainManuItemDatas;
    //带单适配器
    private MainMenuAdapter mainMenuAdapter;

    private int count_0,count_1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        initView();
        initData();
        //版本更新检查
        new UpdateVersionUtil(this).getVerData();
    }

    private void initView() {
        title = findViewById(R.id.title);
        back = findViewById(R.id.iv_back);
        gridView = findViewById(R.id.gridView);
    }

    private void initData() {
        try {
            mContext = this;
            title.setText("枞阳县‘多规合一’联合审批系统");
            mainManuItemDatas = new ArrayList<>();
            mainMenuAdapter = new MainMenuAdapter(mContext, mainManuItemDatas);
            gridView.setAdapter(mainMenuAdapter);
            back.setOnClickListener(clickListener);
            gridView.setOnItemClickListener(itemClickListener);
            updateUI();
            getTaskNum();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTaskNum(){
        //待办
        getCount(0);
        //在办
        getCount(1);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTaskNum();
    }

    private void updateUI() {
        mainManuItemDatas = new ArrayList<>();
        /*
        待办
         */
        {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleKeyConstant.TYPE,0);
            bundle.putString(BundleKeyConstant.TITLE, "待办");
            addItem(R.mipmap.menu_todo, R.drawable.item_blue, R.string.item_not_to_do, count_0, MyWorkTabActivity.class, bundle);
        }

        /*
        在办
         */
        {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleKeyConstant.TYPE,1);
            bundle.putString(BundleKeyConstant.TITLE, "在办");
            addItem(R.mipmap.menu_doing, R.drawable.item_green, R.string.item_doing, count_1, MyWorkTabActivity.class, bundle);
        }

        /*
        已办
         */
        {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleKeyConstant.TYPE,2);
            bundle.putString(BundleKeyConstant.TITLE, "已办");
//            addItem(R.mipmap.item_have_to_do, R.drawable.item_orange_red, R.string.item_have_to_do, 0, MyWorkTabActivity.class, bundle);
        }

        /*
        效能督查
         */
        {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleKeyConstant.TYPE,2);
            bundle.putString(BundleKeyConstant.TITLE, "效能督查");
            addItem(R.mipmap.menu_inspector, R.drawable.item_orange_red, R.string.item_supervision, 0, SupervisionMenuActivity.class, bundle);
        }

        /*
        项目查询
         */
        {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleKeyConstant.TYPE,3);
            bundle.putString(BundleKeyConstant.TITLE, "项目查询");
            addItem(R.mipmap.menu_search, R.drawable.item_orange_yellow, R.string.item_project_search, 0, MyWorkTabActivity.class, bundle);
        }

        mainMenuAdapter.setData(this.mainManuItemDatas);

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.iv_back://返回
                    exit();
                    break;

            }

        }
    };

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (parent.getId()) {
                case R.id.gridView:
                    gridViewClickEvent(position);
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK://返回键
                exit();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
    添加菜单基础数据
     */
    private void addItem(int imageId, int bgId, int textId, int count,
                         Class classType, Bundle bundle) {
        MainManuItemData mainManuItemData = new MainManuItemData();
        mainManuItemData.setImageId(imageId);
        mainManuItemData.setBgId(bgId);
        mainManuItemData.setTextId(textId);
        mainManuItemData.setCount(count);
        mainManuItemData.setBundle(bundle);
        mainManuItemData.setClassType(classType);
        this.mainManuItemDatas.add(mainManuItemData);
    }

    /*
    菜单跳转事件
     */
    private void gridViewClickEvent(int position) {
        if (MainMenuActivity.this.mainManuItemDatas == null)
            return;

        Class<?> classType = MainMenuActivity.this.mainManuItemDatas.get(
                position).getClassType();
        Bundle bundle = MainMenuActivity.this.mainManuItemDatas.get(
                position).getBundle();
        if (classType != null) {
            Intent intent = new Intent();
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            intent.setClass(MainMenuActivity.this, classType);
            startActivityForResult(intent, 1);
        }
    }

    /*
    退出系统提示
     */
    private void exit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainMenuActivity.this);
        alertDialog.setTitle("退出提示").setMessage(R.string.exit_app)
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainMenuActivity.this.moveTaskToBack(true);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("注销", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginActivity.unRegist(mContext);
                        MainMenuActivity.this.finish();
                    }
                }).setCancelable(false).create().show();

    }

    /*
        获取数据列表
         */
    private void getCount(final int type) {

        new GetDataFromNetAsyncTask<>(mContext, null,
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<TaskListResult, String>() {
                    @Override
                    public TaskListResult getResult(String... params) {
                        String loginName = "";
                        if(CurrentUser.getInstance() != null && CurrentUser.getInstance().getCurrentUser() != null
                                && CurrentUser.getInstance().getCurrentUser().getLoginName() != null)
                            loginName = CurrentUser.getInstance()
                                    .getCurrentUser()
                                    .getLoginName();
                        Map<Object, Object> paramMap = new HashMap<>();
                        paramMap.put("loginName",loginName);
                        if (type == 1){
                            return NetworkHelper
                                    .getInstance(mContext)
                                    .getHttpOpera(MyWorkHttpOpera.class)
                                    .getDoingTaskList(paramMap);
                        }


                        return NetworkHelper
                                .getInstance(mContext)
                                .getHttpOpera(MyWorkHttpOpera.class)
                                .getNotToDoTaskList(paramMap);
                    }

                    @Override
                    public void onSuccess(TaskListResult taskListResult) {

                        if(taskListResult.getResult() != null){
                            int size = taskListResult.getTotalItems();
                            switch (type){
                                case 0://待办
                                    count_0 = size;
                                    updateUI();
                                    break;
                                case 1://在办
                                    count_1 = size;
                                    updateUI();
                                    break;
                            }

                        }

                    }

                    @Override
                    public void onFail() {

                    }
                }).execute();
    }
}
