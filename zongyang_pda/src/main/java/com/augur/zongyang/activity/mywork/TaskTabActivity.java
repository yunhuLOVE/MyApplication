package com.augur.zongyang.activity.mywork;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.augur.zongyang.R;
import com.augur.zongyang.fragment.AttachmentCatalogFragment;
import com.augur.zongyang.fragment.HistoryOpinionFragment;
import com.augur.zongyang.fragment.TaskDetailFragment;

public class TaskTabActivity extends AppCompatActivity {
    private Context mContext;

    private FragmentTabHost mTabHost;

    private LayoutInflater mInflater;

    private String tabSpecs[] = {"工程信息", "审批附件", "审批意见"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_tab);
        mContext = this;
        init();
    }

    private void init() {
        mInflater = LayoutInflater.from(this);
        mTabHost = findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        for (int i = 0; i < tabSpecs.length; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tabSpecs[i]).setIndicator(getTabItemView(i));
            if (i == 0)
                mTabHost.addTab(tabSpec, TaskDetailFragment.class, null);
            if (i == 1)
                mTabHost.addTab(tabSpec, AttachmentCatalogFragment.class, null);
            if (i == 2)
                mTabHost.addTab(tabSpec, HistoryOpinionFragment.class, null);
        }
        mTabHost.setBackgroundColor(Color.WHITE);

        mTabHost.setCurrentTab(0);
        updateTab(mTabHost);

        mTabHost.setOnTabChangedListener(new tabChangedListener());
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
                view.setBackgroundColor(this.getResources().getColor(R.color.blue_deep));// 选中后的背景
                tv.setTextColor(this.getResources().getColor(R.color.white));
            } else {
                // 不选中
                view.setBackgroundColor(this.getResources().getColor(R.color.blue));// 非选择的背景
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
