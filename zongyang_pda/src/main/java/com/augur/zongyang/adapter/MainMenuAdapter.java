package com.augur.zongyang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augur.zongyang.R;
import com.augur.zongyang.model.MainManuItemData;

import java.util.List;

/**
 * Created by yunhu on 2017-12-13.
 */

public class MainMenuAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private Context context;
    private List<MainManuItemData> datas;

    public MainMenuAdapter(Context context, List<MainManuItemData> datas) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.datas = datas;
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
            viewHolder.linear = (LinearLayout) convertView
                    .findViewById(R.id.linearLayout);
            viewHolder.mTextView = (TextView) convertView
                    .findViewById(R.id.textView);
            viewHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.imageView7);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTextView.setText(datas.get(position).getTextId());
        if (!(datas.get(position).getBgId() + "".trim()).equals("null"))
            viewHolder.linear.setBackgroundResource(datas.get(position)
                    .getBgId());

        viewHolder.imageView.setImageResource(datas.get(position).getImageId());

        return convertView;
    }

    private final class ViewHolder {
        LinearLayout linear;
        TextView mTextView;
        ImageView imageView;
    }


}
