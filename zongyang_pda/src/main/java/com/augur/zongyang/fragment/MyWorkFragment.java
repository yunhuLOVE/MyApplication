package com.augur.zongyang.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augur.zongyang.R;
import com.augur.zongyang.util.constant.BundleKeyConstant;

/**
 * Created by yunhu on 2017-12-13.
 */

public class MyWorkFragment extends Fragment {

    private View fragmentView;

    public static MyWorkFragment newInstance(int position){
        MyWorkFragment mFragment = new MyWorkFragment();

        Bundle args = new Bundle();
        args.putInt(BundleKeyConstant.POSITION,position);

        switch (position){
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
        }

        mFragment.setArguments(args);

        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_my_work,container,false);

        initView();
        initView();

        return fragmentView;
    }

    private void initView(){

    }

    private void initData(){

    }
}
