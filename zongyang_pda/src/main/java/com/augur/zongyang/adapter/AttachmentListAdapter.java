package com.augur.zongyang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.augur.zongyang.R;
import com.augur.zongyang.model.SysFileForm;
import com.augur.zongyang.util.StringUtil;

import java.util.List;

/**
 * Created by yunhu on 2018-01-03.
 */

public class AttachmentListAdapter extends BaseAdapter{

    Context context;
    List<SysFileForm> forms;

    public AttachmentListAdapter(Context context, List<SysFileForm> datas) {
        this.context = context;
        this.forms = datas;
    }

    public void setData(List<SysFileForm> listData) {
        this.forms = listData;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return forms.size();
    }

    @Override
    public SysFileForm getItem(int arg0) {
        // TODO Auto-generated method stub
        return forms.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        HolderView holder;
        LayoutInflater inflate = LayoutInflater.from(context);
        // if (convertView == null) {
        convertView = inflate.inflate(R.layout.item_attachment, null);
        holder = new HolderView();

        holder.fileName = (TextView) convertView
                .findViewById(R.id.fileName);// 文件名称
        holder.uploadPerson = (TextView) convertView
                .findViewById(R.id.uploadPerson);// 上传人
        holder.uploadTime = (TextView) convertView
                .findViewById(R.id.uploadTime);// 上传时间
        convertView.setTag(holder);
        // } else {
        // holder = (HolderView) convertView.getTag();
        // }
        SysFileForm data = forms.get(position);
        holder.fileName.setText(StringUtil.getNotNullString(
                data.getFileName(), "").trim());// 文件名称
        holder.uploadPerson.setText(StringUtil.getNotNullString(
                data.getCdt(), "").trim());// 上传人
        holder.uploadTime.setText(data.getCdt());// 上传时间

        return convertView;
    }

    class HolderView {
        TextView fileName;// 文件名称
        TextView uploadPerson;// 上传人
        TextView uploadTime;// 上传时间
    }


}
