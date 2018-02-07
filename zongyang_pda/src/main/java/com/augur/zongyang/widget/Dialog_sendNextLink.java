package com.augur.zongyang.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.augur.zongyang.R;
import com.augur.zongyang.bean.CurrentUser;
import com.augur.zongyang.model.LinkInfoModel;
import com.augur.zongyang.model.NextLinkPersonModel;
import com.augur.zongyang.model.Response;
import com.augur.zongyang.model.TaskDetailInfoModel;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.util.SpinnerUtil;
import com.augur.zongyang.util.StringUtil;
import com.augur.zongyang.util.asynctask.CommonUploadDataToNetAsyncTask;
import com.augur.zongyang.util.asynctask.GetDataFromNetAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程发送（提交）
 * Created by yunhu on 2018-01-11.
 */

public class Dialog_sendNextLink implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Activity activity;

    //环节信息
    private TaskDetailInfoModel nextLinkInfo;

    //项目列表中任务信息
    private TaskDetailInfoModel taskData;

    private AlertDialog dialog;

    //关闭按钮
    private ImageView iv_back;

    //下一环节
    private Spinner sp_nextLink;

    //下一环节参与者
    private Spinner sp_nextLinkPerson;

    //发送
    private Button btn_send;

    //返回
    private Button btn_back;

    //环节数据集合
    private List<LinkInfoModel> linkInfos;
    //环节名称集合
    private List<String> linkNames;

    //下一环节参与人数据集合
    private List<NextLinkPersonModel> persons;
    //下一环节参与人名称集合
    private List<String> personNames;

    public Dialog_sendNextLink(Activity activity, TaskDetailInfoModel nextLinkInfo, TaskDetailInfoModel taskData) {
        this.activity = activity;
        this.nextLinkInfo = nextLinkInfo;
        this.taskData = taskData;
    }

    public Dialog getDialog() {
        if (activity == null)
            return null;

        LayoutInflater inflater = activity.getLayoutInflater();

        View customDialogView = inflater.inflate(R.layout.dialog_next_link_send, null);

        dialog = new AlertDialog.Builder(activity).create();

        //不加这行代码，会导致无法弹出软键盘
        dialog.setView(activity.getLayoutInflater().inflate(R.layout.dialog_next_link_send, null));

        dialog.show();

        dialog.getWindow().setContentView(customDialogView);

        dialog.setCanceledOnTouchOutside(false);

        setViewAttr(customDialogView);

        return dialog;
    }

    /*
    设置弹窗属性(内容)
     */
    private void setViewAttr(View view) {
        //返回
        iv_back = view.findViewById(R.id.stop);

        //下一环节
        sp_nextLink = view.findViewById(R.id.sp_next_link);
        //下一环节参与者
        sp_nextLinkPerson = view.findViewById(R.id.sp_next_link_person);

        //发送
        btn_send = view.findViewById(R.id.btn_send);
        btn_back = view.findViewById(R.id.btn_back);

        initData();
        setListener();
    }

    private void setListener() {
        iv_back.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        //下一环节
        sp_nextLink.setOnItemSelectedListener(this);
        //下一环节参与者
        sp_nextLinkPerson.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stop://关闭
                if (dialog != null)
                    dialog.dismiss();
                break;
            case R.id.btn_send://发送
                sendDoingContent();
                break;
            case R.id.btn_back://返回
                if (dialog != null)
                    dialog.dismiss();
                break;
        }
    }

    /*
    parent:当前Spinner
    position == id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.sp_next_link:
                //更新下一环节参与人
                setNextinkPersonSpinnerAdapter(linkInfos.get(position).getName());
                break;
            case R.id.sp_next_link_person:

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /*
    初始化数据(下一环节、下一环节参与者)
     */
    private void initData() {
        if (nextLinkInfo == null)
            return;
        try {
            //下一环节
            setNextLinkSpinnerAdapter();
            //下一环节参与者
            setNextinkPersonSpinnerAdapter(linkInfos.get(sp_nextLink.getSelectedItemPosition()).getName());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /*
    配置下一环节
     */
    private void setNextLinkSpinnerAdapter() {
        linkInfos = new ArrayList<>();
        linkNames = new ArrayList<>();
        if (nextLinkInfo.getNextTransitions() != null && nextLinkInfo.getNextTransitions().get(0).getDestination() != null)
            linkInfos.add(nextLinkInfo.getNextTransitions().get(0).getDestination());
        if (nextLinkInfo.getFreeActivities() != null)
            linkInfos.addAll(nextLinkInfo.getFreeActivities());
        for (LinkInfoModel model : linkInfos) {
            linkNames.add(StringUtil.getNotNullString(model.getChineseName(), ""));
        }

        sp_nextLink.setAdapter(SpinnerUtil.getCenterAdapter(activity, linkNames));
        sp_nextLink.setSelection(0);
    }

    /*
    配置下一环节参与人
     */
    private void setNextinkPersonSpinnerAdapter(final String linkName) {
        new GetDataFromNetAsyncTask<>(activity, null,
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<List<NextLinkPersonModel>, String>() {
                    @Override
                    public List<NextLinkPersonModel> getResult(String... params) {

                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(MyWorkHttpOpera.class)
                                .getNextLinkPersonList(taskData.getTaskInstDbid(), linkName);
                    }

                    @Override
                    public void onSuccess(List<NextLinkPersonModel> result) {

                        if (result != null && result.get(0) != null && result.get(0).getChildren() != null) {
                            persons = result.get(0).getChildren();

                            personNames = new ArrayList<>();
                            for (NextLinkPersonModel model : persons) {
                                personNames.add(StringUtil.getNotNullString(model.getText(), ""));
                            }
                            sp_nextLinkPerson.setAdapter(SpinnerUtil.getCenterAdapter(activity, personNames));
                        }
                    }

                    @Override
                    public void onFail() {

                    }
                }).execute();
    }

    /*
    发送环节信息
     */
    private void sendDoingContent() {

        new CommonUploadDataToNetAsyncTask<String>(activity, "数据发送", "发送成功",
                "发送失败",
                new CommonUploadDataToNetAsyncTask.CommonUploadDataToNetAsyncTaskListener<String>() {

                    @Override
                    public Response doUpload(String... String) {
                        Map<String, String> paramMap = new HashMap<>();

                        paramMap.put("taskInstDbid", taskData.getTaskInstDbid().toString());
                        if(sp_nextLink != null && sp_nextLink.getAdapter() != null){
                            //下一环节名称(英文)
                            paramMap.put("destActivityName", linkInfos.get(sp_nextLink.getSelectedItemPosition()).getName());
                            //下一环节名称(中文)
                            paramMap.put("destActivityChineseName", sp_nextLink.getSelectedItem().toString());
                        }

                        paramMap.put("templateCode", taskData.getTemplateCode());
                        paramMap.put("procInstId", taskData.getProcInstId());
                        if(sp_nextLinkPerson != null && sp_nextLinkPerson.getAdapter() != null){
                            //下一环节参与人ID
                            NextLinkPersonModel person = persons.get(sp_nextLinkPerson.getSelectedItemPosition());
                            paramMap.put("selectedAssignees", person.getType() + "#" +person.getUserId());
                        }

                        //当前用户ID
                        paramMap.put("loginUserId", CurrentUser.getInstance().getCurrentUser().getUserId().toString());

                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(MyWorkHttpOpera.class)
                                .getSendLinkDoingContent(paramMap);
                    }

                    @Override
                    public void onSuccess() {
                        dialog.dismiss();
                        activity.finish();
                    }

                    @Override
                    public void onFail() {
                    }
                }).execute();
    }

}
