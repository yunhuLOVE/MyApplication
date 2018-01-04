package com.augur.zongyang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augur.zongyang.R;
import com.augur.zongyang.model.MainManuItemData;

import java.util.List;

/**
 * Created by yunhu on 2017-12-13.
 */

public class MainMenuAdapter extends BaseAdapter {

    private String TAG = "MainMenuAdapter";

    private LayoutInflater mInflater;
    private Context context;
    private List<MainManuItemData> datas;

    public MainMenuAdapter(Context context, List<MainManuItemData> datas) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.datas = datas;
    }

    public void setData(List<MainManuItemData> datas){
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return datas.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return datas.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_main_menu, parent,
                    false);
            viewHolder = new ViewHolder();
            viewHolder.relayout = convertView
                    .findViewById(R.id.relativeLayout1);
            viewHolder.mTextView = convertView
                    .findViewById(R.id.textView);
            viewHolder.imageView = convertView
                    .findViewById(R.id.imageView7);
            viewHolder.tv_count = convertView.findViewById(R.id.tv_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MainManuItemData data = datas.get(position);

        viewHolder.mTextView.setText(data.getTextId());
        if (!(datas.get(position).getBgId() + "".trim()).equals("null"))
            viewHolder.relayout.setBackgroundResource(data.getBgId());

        viewHolder.imageView.setImageResource(data.getImageId());
//        Log.e(TAG, "data.count:" + datas.get(position).getCount());
        if (datas.get(position).getCount() > 0) {
            viewHolder.tv_count.setVisibility(View.VISIBLE);
            viewHolder.tv_count.setText(String.valueOf(data.getCount()));
        }


        return convertView;
    }

    private final class ViewHolder {
        RelativeLayout relayout;
        TextView mTextView;
        ImageView imageView;
        TextView tv_count;//案件数目
    }


}
