package com.augur.zongyang.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by yunhu on 2017-12-13.
 */

public class MyWorkFragmentPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;
    private List<Fragment> mList;

    public MyWorkFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        mFragmentManager = fm;
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }


}
