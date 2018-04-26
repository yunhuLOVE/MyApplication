package com.augur.zongyang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augur.zongyang.R;
import com.augur.zongyang.model.MainManuItemData;

import java.util.List;

/**
 * Created by yunhu on 2018-03-02.
 */

public class SupervisionMenuAdapter extends BaseAdapter {

    private String TAG = "SupervisionMenuAdapter";

    private LayoutInflater mInflater;
    private Context context;
    private List<MainManuItemData> datas;

    public SupervisionMenuAdapter(Context context, List<MainManuItemData> datas) {
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
            convertView = mInflater.inflate(R.layout.item_supervision_menu, parent,
                    false);
            viewHolder = new ViewHolder();
            viewHolder.relayout = convertView
                    .findViewById(R.id.relativeLayout1);
            viewHolder.mTextView = convertView
                    .findViewById(R.id.textView);
            viewHolder.tv_count = convertView.findViewById(R.id.tv_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MainManuItemData data = datas.get(position);

        viewHolder.mTextView.setText(data.getTextId());
        if (!(datas.get(position).getBgId() + "".trim()).equals("null"))
            viewHolder.relayout.setBackgroundResource(data.getBgId());

        if (datas.get(position).getCount() > 0) {
            viewHolder.tv_count.setVisibility(View.VISIBLE);
            viewHolder.tv_count.setText(String.valueOf(data.getCount()));
        }


        return convertView;
    }

    private final class ViewHolder {
        RelativeLayout relayout;
        TextView mTextView;
        TextView tv_count;//案件数目
    }
}
