package com.augur.zongyang.activity.mywork;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.augur.zongyang.R;
import com.augur.zongyang.adapter.MyWorkFragmentPagerAdapter;
import com.augur.zongyang.fragment.AttachmentCatalogFragment;
import com.augur.zongyang.fragment.TaskDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class TaskDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tabTitle1;
    private TextView tabTitle2;
    private TextView tabTitle3;
    private ViewPager myViewPager;

    private List<Fragment> list;
    private MyWorkFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        initView();
        initData();
    }

    private void initView() {
        tabTitle1 = findViewById(R.id.tab1);
        tabTitle2 = findViewById(R.id.tab2);
        tabTitle3 = findViewById(R.id.tab3);
        myViewPager = findViewById(R.id.myContainer);
    }

    private void initData() {
        tabTitle1.setOnClickListener(this);
        tabTitle2.setOnClickListener(this);
        tabTitle3.setOnClickListener(this);
        myViewPager.setOnPageChangeListener(new MyPageChangedListener());

        TaskDetailFragment fragment1 = TaskDetailFragment.newInstance(0);

        AttachmentCatalogFragment fragment2 = AttachmentCatalogFragment.newInstance();
        TaskDetailFragment fragment3 = TaskDetailFragment.newInstance(2);
        list = new ArrayList<>();
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        adapter = new MyWorkFragmentPagerAdapter(getSupportFragmentManager(),list);
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(0);
        tabTitle1.setBackgroundColor(getResources().getColor(R.color.blue_deep));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tab1:
                myViewPager.setCurrentItem(0);
                tabTitle1.setBackgroundColor(getResources().getColor(R.color.blue_deep));
                tabTitle2.setBackgroundColor(getResources().getColor(R.color.blue));
                tabTitle3.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case R.id.tab2:
                myViewPager.setCurrentItem(1);
                tabTitle1.setBackgroundColor(getResources().getColor(R.color.blue));
                tabTitle2.setBackgroundColor(getResources().getColor(R.color.blue_deep));
                tabTitle3.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case R.id.tab3:
                myViewPager.setCurrentItem(2);
                tabTitle1.setBackgroundColor(getResources().getColor(R.color.blue));
                tabTitle2.setBackgroundColor(getResources().getColor(R.color.blue));
                tabTitle3.setBackgroundColor(getResources().getColor(R.color.blue_deep));
                break;
        }
    }

    public class MyPageChangedListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageSelected(int position) {

            switch (position){
                case 0:
                    tabTitle1.setBackgroundColor(getResources().getColor(R.color.blue_deep));
                    tabTitle2.setBackgroundColor(getResources().getColor(R.color.blue));
                    tabTitle3.setBackgroundColor(getResources().getColor(R.color.blue));
                    break;
                case 1:
                    tabTitle1.setBackgroundColor(getResources().getColor(R.color.blue));
                    tabTitle2.setBackgroundColor(getResources().getColor(R.color.blue_deep));
                    tabTitle3.setBackgroundColor(getResources().getColor(R.color.blue));
                    break;
                case 2:
                    tabTitle1.setBackgroundColor(getResources().getColor(R.color.blue));
                    tabTitle2.setBackgroundColor(getResources().getColor(R.color.blue));
                    tabTitle3.setBackgroundColor(getResources().getColor(R.color.blue_deep));
                    break;
            }
        }
    }

}
