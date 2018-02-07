package com.augur.zongyang.activity.mywork;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.augur.zongyang.R;
import com.augur.zongyang.fragment.MyWorkFragment;
import com.augur.zongyang.util.constant.BundleKeyConstant;

public class MyWorkTabActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;

    private TextView title;
    private ImageView back;

    private FragmentTabHost mTabHost;

    private LayoutInflater mInflater;

    private String tabSpecs[] = {"待办", "在办"};

    private int type;//案件类型 0：待办，1：在办，2：已办

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_tab);
        mContext = this;
        init();
    }

    private void init() {

        title = findViewById(R.id.title);
        back = findViewById(R.id.iv_back);
        title.setText("我的工作");

        back.setOnClickListener(this);
        type = getIntent().getExtras().getInt(BundleKeyConstant.TYPE, 0);

        if (type == 3){
            title.setText("项目查询");
            tabSpecs = new String[]{"查询条件"};
        }


        mInflater = LayoutInflater.from(this);
        mTabHost = findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);


            for (int i = 0; i < tabSpecs.length; i++) {
                TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tabSpecs[i]).setIndicator(getTabItemView(i));
                Bundle b = new Bundle();
                b.putInt(BundleKeyConstant.TYPE, i);
                if(type == 3)
                    b.putInt(BundleKeyConstant.TYPE, 3);
                mTabHost.addTab(tabSpec, MyWorkFragment.class, b);
            }


        mTabHost.setBackgroundColor(Color.WHITE);

        mTabHost.setCurrentTab(type);
        updateTab(mTabHost);

        mTabHost.setOnTabChangedListener(new tabChangedListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /**
     * TabHost选择监听器
     *
     * @author
     */
    private class tabChangedListener implements TabHost.OnTabChangeListener {

        @Override
        public void onTabChanged(String tabId) {
            mTabHost.setCurrentTabByTag(tabId);
            updateTab(mTabHost);
        }
    }

    /**
     * 更新Tab标签的颜色，和字体的颜色
     *
     * @param tabHost
     */
    private void updateTab(final TabHost tabHost) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View view = tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(R.id.text);
            tv.setTextSize(16);
            tv.setTypeface(Typeface.SERIF, 0); // 设置字体和风格
            if (tabHost.getCurrentTab() == i) {
                // 选中
                view.setBackgroundColor(this.getResources().getColor(R.color.blue));// 选中后的背景
                tv.setTextColor(this.getResources().getColor(R.color.white));
            } else {
                // 不选中
                view.setBackgroundColor(this.getResources().getColor(R.color.blue_deep));// 非选择的背景
                tv.setTextColor(this.getResources().getColor(R.color.white));
            }
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = mInflater.inflate(R.layout.item_tab_widget, null);
        TextView textView = view.findViewById(R.id.text);
        textView.setText(tabSpecs[index]);
        return view;
    }
}
