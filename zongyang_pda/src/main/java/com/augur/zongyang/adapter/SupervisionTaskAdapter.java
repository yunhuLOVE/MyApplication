package com.augur.zongyang.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.augur.zongyang.R;
import com.augur.zongyang.model.SupervisionProjectForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunhu on 2018-03-01.
 */

public class SupervisionTaskAdapter extends BaseAdapter {

    private Activity activity;

    private List<SupervisionProjectForm> list;

    private int type;

    public SupervisionTaskAdapter(Activity activity, List<SupervisionProjectForm> datas, int type) {
        this.activity = activity;
        if (datas != null)
            list = datas;
        else
            list = new ArrayList<>();
        this.type = type;
    }

    public void setData(List<SupervisionProjectForm> datas) {
        if (datas == null)
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
            viewHolder.construction_unit = convertView.findViewById(R.id.construction_unit);
            viewHolder.link_name = convertView.findViewById(R.id.link_name);
            viewHolder.create = convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SupervisionProjectForm data = list.get(position);

        if (type == 0)
            viewHolder.tv_state.setText("待办");
        if (type == 1)
            viewHolder.tv_state.setText("在办");
        if (type == 2)
            viewHolder.tv_state.setText("已办");
        viewHolder.project_name.setText("项目名称：" + data.getProjectName());
        viewHolder.construction_unit.setText("建设单位：" + data.getApplicant());
        viewHolder.link_name.setText("申报事项：" + data.getMatterName());
        viewHolder.create.setText("申报时间：" + data.getProCreateDate());
        return convertView;
    }

    class ViewHolder {
        TextView tv_state;//状态
        TextView project_name;//项目名称
        TextView construction_unit;
        TextView link_name;
        TextView create;
    }
}
