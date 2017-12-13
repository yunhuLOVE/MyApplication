package com.augur.zongyang.activity.mywork;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.augur.zongyang.R;
import com.augur.zongyang.adapter.MyWorkFragmentPagerAdapter;
import com.augur.zongyang.fragment.MyWorkFragment;
import com.augur.zongyang.util.constant.BundleKeyConstant;

import java.util.ArrayList;
import java.util.List;

public class MyWorkActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private ImageView back;

    private TextView tabTitle1;
    private TextView tabTitle2;
    private TextView tabTitle3;
    private ViewPager myViewPager;

    private List<Fragment> list;
    private MyWorkFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work);

        initView();
        initData();
    }

    private void initView() {
        title = findViewById(R.id.title);
        back = findViewById(R.id.iv_back);

        tabTitle1 = findViewById(R.id.tab1);
        tabTitle2 = findViewById(R.id.tab2);
        tabTitle3 = findViewById(R.id.tab3);
        myViewPager = findViewById(R.id.myContainer);
    }

    private void initData() {
        title.setText(getIntent().getExtras().getString(BundleKeyConstant.TITLE,""));
        back.setOnClickListener(this);
        tabTitle1.setOnClickListener(this);
        tabTitle2.setOnClickListener(this);
        tabTitle3.setOnClickListener(this);
        myViewPager.setOnPageChangeListener(new MyPageChangedListener());

        MyWorkFragment fragment1 = MyWorkFragment.newInstance(0);
        MyWorkFragment fragment2 = MyWorkFragment.newInstance(1);
        MyWorkFragment fragment3 = MyWorkFragment.newInstance(2);
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
