package com.augur.zongyang.activity.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.augur.zongyang.R;
import com.augur.zongyang.activity.supervision.SupervisionListActivity;
import com.augur.zongyang.adapter.SupervisionMenuAdapter;
import com.augur.zongyang.model.MainManuItemData;
import com.augur.zongyang.model.result.SupervisionTaskListResult;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.util.asynctask.GetDataFromNetAsyncTask;
import com.augur.zongyang.util.constant.BundleKeyConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 效能督查菜单
 */
public class SupervisionMenuActivity extends AppCompatActivity {

    private String TAG = "SupervisionMenuActivity";

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
    private SupervisionMenuAdapter supervisionMenuAdapter;

    private int count0, count1, count2, count3, count4, count5, count6, count7, count8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervision_menu);
        initView();
        initData();
    }

    private void initView() {
        title = findViewById(R.id.title);
        back = findViewById(R.id.iv_back);
        gridView = findViewById(R.id.gridView);
    }

    private void initData() {
        try {
            mContext = this;
            title.setText(getIntent().getExtras().getString(BundleKeyConstant.TITLE, "效能督查"));
            mainManuItemDatas = new ArrayList<>();
            supervisionMenuAdapter = new SupervisionMenuAdapter(mContext, mainManuItemDatas);
            gridView.setAdapter(supervisionMenuAdapter);
            back.setOnClickListener(clickListener);
            gridView.setOnItemClickListener(itemClickListener);
            updateUI();
            getTaskNum();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTaskNum() {

        for(int i = 0; i <= 8; i++)
            getCount(i);
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
        登记阶段
         */
        {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleKeyConstant.TYPE, 0);
            bundle.putString(BundleKeyConstant.TITLE, "登记阶段");
            addItem(R.mipmap.item_not_to_do, R.drawable.item_blue, R.string.register_phase, count0, SupervisionListActivity.class, bundle);
        }

        /*
        正在办理
         */
        {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleKeyConstant.TYPE, 1);
            bundle.putString(BundleKeyConstant.TITLE, "正在办理");
            addItem(R.mipmap.icon_work, R.drawable.item_green, R.string.being_deal_with, count1, SupervisionListActivity.class, bundle);
        }

        /*
        逾期
         */
        {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleKeyConstant.TYPE, 2);
            bundle.putString(BundleKeyConstant.TITLE, "逾期");
            addItem(R.mipmap.item_have_to_do, R.drawable.item_orange_red, R.string.overdue, count2, SupervisionListActivity.class, bundle);
        }

        /*
        已办结
         */
        {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleKeyConstant.TYPE, 3);
            bundle.putString(BundleKeyConstant.TITLE, "已办结");
            addItem(R.mipmap.item_not_to_do, R.drawable.item_blue, R.string.have_done, count3, SupervisionListActivity.class, bundle);
        }

        /*
        挂起
         */
        {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleKeyConstant.TYPE, 4);
            bundle.putString(BundleKeyConstant.TITLE, "挂起");
            addItem(R.mipmap.icon_search, R.drawable.item_orange_yellow, R.string.hang_up, count4, SupervisionListActivity.class, bundle);
        }

        /*
        即将到期
         */
        {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleKeyConstant.TYPE, 5);
            bundle.putString(BundleKeyConstant.TITLE, "即将到期");
            addItem(R.mipmap.item_have_to_do, R.drawable.item_orange_red, R.string.about_to_expire, count5, SupervisionListActivity.class, bundle);
        }

        /*
        超时未办
         */
        {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleKeyConstant.TYPE, 6);
            bundle.putString(BundleKeyConstant.TITLE, "超时未办");
            addItem(R.mipmap.icon_search, R.drawable.item_orange_red, R.string.overtime_not_todo, count6, SupervisionListActivity.class, bundle);
        }

        /*
        超时已办
         */
        {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleKeyConstant.TYPE, 7);
            bundle.putString(BundleKeyConstant.TITLE, "超时已办");
            addItem(R.mipmap.item_have_to_do, R.drawable.item_orange_yellow, R.string.overtime_have_todo, count7, SupervisionListActivity.class, bundle);
        }

        /*
        方案会审
         */
        {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleKeyConstant.TYPE, 8);
            bundle.putString(BundleKeyConstant.TITLE, "方案会审");
            addItem(R.mipmap.icon_search, R.drawable.item_green, R.string.shceme_joint_trial, count8, SupervisionListActivity.class, bundle);
        }

        supervisionMenuAdapter.setData(this.mainManuItemDatas);

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.iv_back://返回
                    finish();
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
                finish();
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
        if (mainManuItemDatas == null)
            return;

        Class<?> classType = mainManuItemDatas.get(
                position).getClassType();
        Bundle bundle = mainManuItemDatas.get(
                position).getBundle();
        if (classType != null) {
            Intent intent = new Intent();
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            intent.setClass(SupervisionMenuActivity.this, classType);
            startActivityForResult(intent, 1);
        }
    }

    /*
      获取数据列表
     */
    private void getCount(final int type) {
        new GetDataFromNetAsyncTask<>(mContext, null,
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<List<SupervisionTaskListResult>, String>() {
                    @Override
                    public List<SupervisionTaskListResult> getResult(String... params) {

                        Map<Object, Object> paramMap = new HashMap<>();

                        paramMap.put("type", type);

                        paramMap.put("page", +1 + "");
                        paramMap.put("limit", 1 + "");
                        return NetworkHelper
                                .getInstance(mContext)
                                .getHttpOpera(MyWorkHttpOpera.class)
                                .getRegisterListOfSupervision(paramMap);
                    }

                    @Override
                    public void onSuccess(List<SupervisionTaskListResult> taskListResult) {
                        if (taskListResult != null) {

                            int size = taskListResult.get(0).getTotalItems();

                            switch (type) {
                                case 0:
                                    count0 = size;
                                    updateUI();
                                    break;
                                case 1:
                                    count1 = size;
                                    updateUI();
                                    break;
                                case 2:
                                    count2 = size;
                                    updateUI();
                                    break;
                                case 3:
                                    count3 = size;
                                    updateUI();
                                    break;
                                case 4:
                                    count4 = size;
                                    updateUI();
                                    break;
                                case 5:
                                    count5 = size;
                                    updateUI();
                                    break;
                                case 6:
                                    count6 = size;
                                    updateUI();
                                    break;
                                case 7:
                                    count7 = size;
                                    updateUI();
                                    break;
                                case 8:
                                    count8 = size;
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
