package com.augur.zongyang.tree.bean;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.augur.zongyang.R;

import java.util.List;

/**
 * Created by yunhu on 2017-12-25.
 */

public class SimpleTreeAdapter<T> extends TreeListViewAdapter {

    private Activity activity;

    private Handler mHandler;

    private int type;//1.在办

    public SimpleTreeAdapter(ListView mTree, Activity context, List<T> datas, int defaultExpandLevel, Handler handle, int type)
            throws IllegalArgumentException, IllegalAccessException {
        super(mTree, context, datas, defaultExpandLevel);
        this.activity = context;
        this.mHandler = handle;
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.tree_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.upload = convertView.findViewById(R.id.id_treenode_upload);
            viewHolder.icon = convertView.findViewById(R.id.id_treenode_icon);
            viewHolder.label = convertView.findViewById(R.id.id_treenode_label);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //子节点为叶子节点时，显示上传文件按钮
        if (type == 1 && node.getItemId().equals("dir") &&
                (node.getChildren().size() == 0 ||
                        (node.getChildren().size() > 0 && node.getChildren().get(0).isLeaf())
                ))
            viewHolder.upload.setVisibility(View.VISIBLE);
        else {
            viewHolder.upload.setVisibility(View.INVISIBLE);

        }

        if (node.getIcon() == -1) {
            viewHolder.icon.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(node.getIcon());
        }

        final String id = node.getId();
        viewHolder.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 2;
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
//                Toast.makeText(activity, "点击了上传按钮", Toast.LENGTH_SHORT).show();
            }
        });


        /*
        历史意见信息，保存到itemId字段
         */
        if (node.getItemId() != null && !node.getItemId().equals("dir") && !node.getItemId().equals("")) {
            viewHolder.label.setTextSize(12);
            viewHolder.label.setTextColor(activity.getResources().getColor(R.color.blue));
            viewHolder.label.setText(node.getItemId());
        }else{
            if ( node.isLeaf()) {//!(node.getProjectId() != null && !node.getProjectId().equals("opinion")) &&
                viewHolder.label.setTextSize(12);
                viewHolder.label.setTextColor(activity.getResources().getColor(R.color.blue));
            }else{
                viewHolder.label.setTextColor(activity.getResources().getColor(R.color.black));
                viewHolder.label.setText(node.getName());
            }
            viewHolder.label.setText(node.getName());
        }

        return convertView;
    }

    private final class ViewHolder {
        ImageView upload;
        ImageView icon;
        TextView label;
    }


}
