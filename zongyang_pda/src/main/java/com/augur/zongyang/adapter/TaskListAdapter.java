package com.augur.zongyang.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.augur.zongyang.R;
import com.augur.zongyang.model.TaskDetailInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunhu on 2017-12-14.
 */

public class TaskListAdapter extends BaseAdapter {

    private Activity activity;

    private List<TaskDetailInfoModel> list;

    private int type;

    public TaskListAdapter(Activity activity, List<TaskDetailInfoModel> datas,int type) {
        this.activity = activity;
        if(datas != null)
            list = datas;
        else
            list = new ArrayList<>();
        this.type = type;
    }

    public void setData(List<TaskDetailInfoModel> datas){
        if(datas == null)
            return;
        list = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_work, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_state = convertView.findViewById(R.id.tv_state);
            viewHolder.project_name = convertView.findViewById(R.id.project_name);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TaskDetailInfoModel data = list.get(position);

        if (type == 0)
            viewHolder.tv_state.setText("待办");
        if (type == 1)
            viewHolder.tv_state.setText("在办");
        if (type == 2)
            viewHolder.tv_state.setText("已办");

        if(data.getBusMemo1() != null)
            viewHolder.project_name.setText(data.getBusMemo1());

        return convertView;
    }

    class ViewHolder {
        TextView tv_state;//状态
        TextView project_name;//项目名称
    }
}
