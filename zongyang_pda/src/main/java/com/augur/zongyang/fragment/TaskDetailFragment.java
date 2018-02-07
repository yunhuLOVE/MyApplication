package com.augur.zongyang.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.augur.zongyang.R;
import com.augur.zongyang.bean.CurrentUser;
import com.augur.zongyang.manager.ToastManager;
import com.augur.zongyang.model.ButtonModel;
import com.augur.zongyang.model.ProjectInfoModel;
import com.augur.zongyang.model.ReceiveForm;
import com.augur.zongyang.model.Response;
import com.augur.zongyang.model.TaskDetailInfoModel;
import com.augur.zongyang.model.TaskSignInfoModel;
import com.augur.zongyang.model.result.ProjectInfoResult;
import com.augur.zongyang.model.result.ReceiveResult;
import com.augur.zongyang.model.result.SendBaseInfoResult;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.util.StringUtil;
import com.augur.zongyang.util.asynctask.CommonUploadDataToNetAsyncTask;
import com.augur.zongyang.util.asynctask.GetDataFromNetAsyncTask;
import com.augur.zongyang.util.constant.BundleKeyConstant;
import com.augur.zongyang.widget.Dialog_sendNextLink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskDetailFragment extends Fragment implements View.OnClickListener {

    private String TAG = "TaskDetailFragment";

    private View fragmentView;

    private Activity activity;

    //项目列表中任务信息
    private TaskDetailInfoModel taskData;

    //签收信息
    private TaskSignInfoModel taskSignInfo;

    //项目详细信息
    private ProjectInfoModel projectInfo;

    private ReceiveForm receiveForm = null;

    //发送中相关环节信息
    private TaskDetailInfoModel nextLinkInfo;

    //是否显示收件按钮
    private boolean isReceive;

    /*
    案件类型 0：待办，1：在办，2：已办
     */
    private int type;

    private LinearLayout linearLayout1;
    private Button btn_opinion;//审批意见
    private Button btn_submit;//提交按钮

    private EditText et_projectCode;//项目编号
    private EditText et_projectName;//项目名称
    private EditText et_mainClass;//项目类别
    private EditText et_subClass;//项目小类
    private EditText et_construction_content;//建设内容
    private EditText et_legal_representative;//法人代表
    private EditText et_construction_unit;//建设单位
    private EditText et_unit_address;//单位地址
    private EditText et_competent_department;//主管部门
    private EditText et_land_nature;//用地性质
    private EditText et_construction_land_area;//建设用地面积
    private EditText et_construction_area;//建筑面积
    private EditText et_investment_total;//总投资
    private EditText et_funding_source;//资金来源
    private EditText et_area;//所属区域
    private EditText et_choose_location;//拟选位置
    private EditText et_age_begin;//建设开始年限
    private EditText et_age_complete;//建设完成年限
    private EditText et_land_ownership;//土地权属
    private EditText et_project_summary;//工程概况
    private EditText et_contact;//联系人
    private EditText et_contact_phone;//联系电话


    public TaskDetailFragment() {
    }

    public static TaskDetailFragment newInstance(int position) {
        TaskDetailFragment fragment = new TaskDetailFragment();
//        Bundle args = new Bundle();
//        args.putInt(BundleKeyConstant.TYPE, position);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();

        type = activity.getIntent().getExtras().getInt(BundleKeyConstant.TYPE, -1);

        taskData = (TaskDetailInfoModel) activity.getIntent().getExtras().getSerializable(BundleKeyConstant.DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_task_detail, container, false);

        initView();
        getProjectInfo();

        return fragmentView;
    }

    private void initView() {

        linearLayout1 = fragmentView.findViewById(R.id.linearLayout1);
        btn_opinion = fragmentView.findViewById(R.id.btn_opinion);
        btn_submit = fragmentView.findViewById(R.id.btn_submit);

        et_projectCode = fragmentView.findViewById(R.id.projectCode);//项目编号
        et_projectName = fragmentView.findViewById(R.id.projectName);//项目名称
        et_mainClass = fragmentView.findViewById(R.id.mainClass);//项目类别
        et_subClass = fragmentView.findViewById(R.id.subClass);//项目小类
        et_construction_content = fragmentView.findViewById(R.id.construction_content);//建设内容
        et_legal_representative = fragmentView.findViewById(R.id.legal_representative);//法人代表
        et_construction_unit = fragmentView.findViewById(R.id.construction_unit);//建设单位
        et_unit_address = fragmentView.findViewById(R.id.unit_address);//单位地址
        et_competent_department = fragmentView.findViewById(R.id.competent_department);//主管部门
        et_land_nature = fragmentView.findViewById(R.id.land_nature);//用地性质
        et_construction_land_area = fragmentView.findViewById(R.id.construction_land_area);//建设用地面积
        et_construction_area = fragmentView.findViewById(R.id.construction_area);//建筑面积
        et_investment_total = fragmentView.findViewById(R.id.investment_total);//总投资
        et_funding_source = fragmentView.findViewById(R.id.funding_source);//资金来源
        et_area = fragmentView.findViewById(R.id.area);//所属区域
        et_choose_location = fragmentView.findViewById(R.id.choose_location);//拟选位置
        et_age_begin = fragmentView.findViewById(R.id.limit_age_begin_construction);//建设开始年限
        et_age_complete = fragmentView.findViewById(R.id.limit_age_complete_construction);//建设完成年限
        et_land_ownership = fragmentView.findViewById(R.id.land_ownership);//土地权属
        et_project_summary = fragmentView.findViewById(R.id.project_summary);//工程概况
        et_contact = fragmentView.findViewById(R.id.contact);//联系人
        et_contact_phone = fragmentView.findViewById(R.id.contact_phone);//联系电话

//        if (type != 1)
//            linearLayout1.setVisibility(View.GONE);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_opinion:
                opinionDialog();
                break;

            case R.id.btn_submit:
                //收件
                if (isReceive) {
                    if (!taskData.getTemplateCode().equals("WF_fahs")) {
                        getNextLinkInfoReceive();
                    }
                    showReceiveDialog();

                } else {
                    if (submitDialog != null)
                        submitDialog.show();
                    else
                        getSubmitDialog().show();
                }

                break;
        }
    }

    private void initData() {
        signTask();
        initBaseData();

        btn_opinion.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private void initBaseData() {
        try {
            et_projectCode.setText(projectInfo.getProjectCode());
            et_projectName.setText(projectInfo.getProjectName());
            et_mainClass.setText(projectInfo.getMainClassCn());
            et_subClass.setText(projectInfo.getSubClass());
            et_construction_content.setText(projectInfo.getDesignOrg());
            et_legal_representative.setText(projectInfo.getApplicantOrgCode());
            et_construction_unit.setText(projectInfo.getApplicant());
            et_unit_address.setText(projectInfo.getIdcard());
            et_competent_department.setText(projectInfo.getResponsibleOrg());
            et_land_nature.setText(projectInfo.getLandCharacter());
            et_construction_land_area.setText(projectInfo.getBuildLandAreaSum().toString());
            et_construction_area.setText(projectInfo.getBuildAreaSum().toString());
            et_investment_total.setText(projectInfo.getInvestSum().toString());
            et_funding_source.setText(projectInfo.getFinancialSource());
            et_area.setText(projectInfo.getDistrictCn());
            et_choose_location.setText(projectInfo.getSite());
            et_age_begin.setText(projectInfo.getStartYear());
            et_age_complete.setText(projectInfo.getEndYear());
            et_land_ownership.setText(projectInfo.getBuildingDroit());
            et_project_summary.setText(projectInfo.getDescription());
            et_contact.setText(projectInfo.getContact());
            et_contact_phone.setText(projectInfo.getMobile());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /*
    按钮信息
     */
    private void getButtonInfo() {
        new GetDataFromNetAsyncTask<>(activity, "",
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<List<ButtonModel>, String>() {
                    @Override
                    public List<ButtonModel> getResult(String... params) {

                        Map<Object, Object> paramMap = new HashMap<>();
                        if (taskSignInfo != null && taskSignInfo.getTemplateCode() != null)
                            paramMap.put("templateCode", taskSignInfo.getTemplateCode());
                        paramMap.put("viewName", "dzb");
                        if (taskSignInfo != null)
                            paramMap.put("processDefId", taskSignInfo.getProcessDefId());
                        paramMap.put("activityName", taskData.getActivityName());
                        paramMap.put("procInstId",taskData.getProcInstId());

                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(MyWorkHttpOpera.class)
                                .getButtonInfo(paramMap);
                    }

                    @Override
                    public void onSuccess(List<ButtonModel> buttons) {

                        for (ButtonModel model : buttons) {
                            if (model.getElementName().equals("收件")) {
                                isReceive = true;
                                btn_submit.setText("收件");
                                break;
                            }

                        }
                    }

                    @Override
                    public void onFail() {

                    }
                }).execute();
    }

    /*
    案件详细信息
     */
    private void getProjectInfo() {
        new GetDataFromNetAsyncTask<>(activity, "",
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<ProjectInfoResult, String>() {
                    @Override
                    public ProjectInfoResult getResult(String... params) {

                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(MyWorkHttpOpera.class)
                                .getProjectInfo(taskData.getMasterEntityKey());
                    }

                    @Override
                    public void onSuccess(ProjectInfoResult taskListResult) {
                        if (taskListResult.getProjectInfo() != null) {
                            List<ProjectInfoModel> list = taskListResult.getProjectInfo();
                            projectInfo = list.get(0);
                            initData();
                        }


                    }

                    @Override
                    public void onFail() {

                    }
                }).execute();
    }

    /*
    任务签收
     */
    private void signTask() {

        new GetDataFromNetAsyncTask<>(activity, "",
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<TaskSignInfoModel, String>() {
                    @Override
                    public TaskSignInfoModel getResult(String... params) {

                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(MyWorkHttpOpera.class)
                                .getSignTask(taskData.getTaskInstDbid(),
                                        CurrentUser.getInstance()
                                                .getCurrentUser()
                                                .getLoginName());
                    }

                    @Override
                    public void onSuccess(TaskSignInfoModel taskListResult) {
                        taskSignInfo = taskListResult;
                        getButtonInfo();
                    }

                    @Override
                    public void onFail() {

                    }
                }).execute();

    }

    AlertDialog submitDialog;

    /*
    数据提交弹窗
     */
    private AlertDialog getSubmitDialog() {
        submitDialog = new AlertDialog.Builder(activity)
                .setTitle("是否发送")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //获取下一环节信息
                        getNextLinksInfo();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        return submitDialog;
    }

    /*
    下一环节信息(收件)
     */
    private void getNextLinkInfoReceive() {

        new GetDataFromNetAsyncTask<>(activity, "",
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<ReceiveResult, String>() {
                    @Override
                    public ReceiveResult getResult(String... params) {
                        Map<Object, Object> paramMap = new HashMap<>();
                        paramMap.put("taskInstDbId", taskData.getTaskInstDbid().toString());
                        paramMap.put("procInstId", taskData.getProcInstId());
                        paramMap.put("userId", CurrentUser.getInstance().getCurrentUser().getUserId().toString());
                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(MyWorkHttpOpera.class)
                                .getNextLinkInfo(paramMap);
                    }

                    @Override
                    public void onSuccess(ReceiveResult result) {
                        receiveForm = result.getForm();
                    }

                    @Override
                    public void onFail() {

                    }
                }).execute();

    }

    /*
    保存收件意见
     */
    private void saveReceiveOpinion(final String opinion, final String result) {

        new GetDataFromNetAsyncTask<>(activity, "",
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<ReceiveResult, String>() {
                    @Override
                    public ReceiveResult getResult(String... params) {
                        Map<Object, Object> paramMap = new HashMap<>();
                        try {
                            paramMap.put("taskInstDbId", StringUtil.getNotNullString(taskData.getTaskInstDbid(), ""));
                            paramMap.put("procInstId", StringUtil.getNotNullString(taskData.getProcInstId(), ""));
                            if (CurrentUser.getInstance().getCurrentUser() != null)
                                paramMap.put("userId", StringUtil.getNotNullString(CurrentUser.getInstance().getCurrentUser().getUserId(), ""));

                            paramMap.put("activityChineseName", taskData.getActivityChineseName());
                            paramMap.put("activityName", StringUtil.getNotNullString(taskData.getActivityName(), ""));
                            paramMap.put("templateCode", StringUtil.getNotNullString(taskSignInfo.getTemplateCode(), ""));
                            paramMap.put("projectId", StringUtil.getNotNullString(taskData.getMasterEntityKey(), ""));
                            if (receiveForm != null) {
                                paramMap.put("handler", StringUtil.getNotNullString(receiveForm.getHandler(), ""));
                                paramMap.put("handlerDays", StringUtil.getNotNullString(receiveForm.getHandlerDays(), ""));
                            }
                            paramMap.put("processResult", StringUtil.getNotNullString(result, ""));
                            paramMap.put("handlingDetail", opinion);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }


                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(MyWorkHttpOpera.class)
                                .geSaveReceiveOpinion(paramMap);
                    }

                    @Override
                    public void onSuccess(ReceiveResult result) {
                        Toast.makeText(activity, "数据发送成功", Toast.LENGTH_SHORT).show();
                        receiveForm = result.getForm();
                        getNextLinksInfo();
                    }

                    @Override
                    public void onFail() {

                    }
                }).execute();
    }

    /*
    获取下一环节列表信息（是否显示下一环节，获取环节信息）
     */

    private void getNextLinksInfo() {
        new GetDataFromNetAsyncTask<>(activity, null,
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<List<SendBaseInfoResult>, String>() {
                    @Override
                    public List<SendBaseInfoResult> getResult(String... params) {
                        Map<Object, Object> paramMap = new HashMap<>();
                        if (CurrentUser.getInstance().getCurrentUser() != null)
                            paramMap.put("userId", StringUtil.getNotNullString(CurrentUser.getInstance().getCurrentUser().getUserId(), ""));
                        paramMap.put("taskInstDbid", taskData.getTaskInstDbid().toString());
                        if (isReceive && taskSignInfo.getTemplateCode().equals("WF_fahs"))
                            paramMap.put("tyslFlag", "true");
                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(MyWorkHttpOpera.class)
                                .getSendBaseInfo(paramMap);
                    }

                    @Override
                    public void onSuccess(List<SendBaseInfoResult> sendResult) {

                        if (sendResult != null && sendResult.get(0) != null) {

//                            if(submitDialog != null)
//                                submitDialog = null;
                            SendBaseInfoResult resultInfo = sendResult.get(0);
                            nextLinkInfo = sendResult.get(0).getResult();
                            //需要下一环节
                            if (resultInfo.getMessage() == null || resultInfo.getMessage().equals("")) {
                                Dialog_sendNextLink mDialog = new Dialog_sendNextLink(activity, nextLinkInfo, taskData);
                                mDialog.getDialog();
                            } else {
                                ToastManager.getInstance(activity).shortToast("信息发送成功," + resultInfo.getMessage());
                                activity.finish();
                            }
                        }
                    }

                    @Override
                    public void onFail() {

                    }
                }).execute();
    }

    private void opinionDialog() {
        final EditText et_opinion = new EditText(activity);

        AlertDialog dialog = new AlertDialog.Builder(activity).setTitle("审批意见").setView(et_opinion)
                .setPositiveButton("发送", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        uploadOpinion(et_opinion.getText().toString());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
    }

    /*
    发送审批意见
     */
    private void uploadOpinion(final String opinion) {
        new CommonUploadDataToNetAsyncTask<String>(activity, "数据发送", "发送成功",
                "发送失败",
                new CommonUploadDataToNetAsyncTask.CommonUploadDataToNetAsyncTaskListener<String>() {

                    @Override
                    public Response doUpload(String... String) {
                        Map<String, String> paramMap = new HashMap<>();

                        paramMap.put("taskInstDbid", taskData.getTaskInstDbid().toString());
                        paramMap.put("handleComments", opinion);

                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(MyWorkHttpOpera.class)
                                .getSendExamineOpinion(paramMap);
                    }

                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onFail() {
                    }
                }).execute();
    }

    private void showReceiveDialog() {
        final AlertDialog dialog;
        LayoutInflater inflater = activity.getLayoutInflater();

        View customDialogView = inflater.inflate(R.layout.dialog_receive, null);

        dialog = new AlertDialog.Builder(activity).create();

        //不加这行代码，会导致无法弹出软键盘
        dialog.setView(activity.getLayoutInflater().inflate(R.layout.dialog_receive, null));

        dialog.show();

        dialog.getWindow().setContentView(customDialogView);

        dialog.setCanceledOnTouchOutside(false);

        final Spinner sp_result = customDialogView.findViewById(R.id.sp_result);

        final EditText et_opinion = customDialogView.findViewById(R.id.et_opinion);

        //发送
        Button btn_send = customDialogView.findViewById(R.id.btn_send);
        Button btn_back = customDialogView.findViewById(R.id.btn_back);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                saveReceiveOpinion(et_opinion.getText().toString(), sp_result.getSelectedItem().toString());
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
