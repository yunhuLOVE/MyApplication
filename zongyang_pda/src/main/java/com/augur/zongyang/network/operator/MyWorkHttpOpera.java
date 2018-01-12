package com.augur.zongyang.network.operator;

import android.content.Context;

import com.augur.zongyang.manager.JsonManager;
import com.augur.zongyang.model.CustomTreeForm;
import com.augur.zongyang.model.NextLinkPersonModel;
import com.augur.zongyang.model.Response;
import com.augur.zongyang.model.TaskDetailInfoModel;
import com.augur.zongyang.model.UploadFile;
import com.augur.zongyang.model.http.request.RequestMethod;
import com.augur.zongyang.model.result.ProjectInfoResult;
import com.augur.zongyang.model.result.SendBaseInfoResult;
import com.augur.zongyang.model.result.SysFileFormResult;
import com.augur.zongyang.model.result.TaskListResult;
import com.augur.zongyang.network.operator.base.MyBaseHttpOpera;
import com.augur.zongyang.util.MyFileUtil;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yunhu on 2017-12-19.
 */

public class MyWorkHttpOpera extends MyBaseHttpOpera {
    public MyWorkHttpOpera(Context context,
                           OnNetResultListener onNetResultListener) {
        super(context, onNetResultListener);
    }

    /*
    待办列表
     */
    public TaskListResult getNotToDoTaskList(String loginName){

        String url = api.getAPI_GET_TASK_LIST_NOT_TO_DO();
        Type type = new TypeToken<TaskListResult>() {
        }.getType();
        Map<Object, Object> paramMap = new HashMap<Object, Object>();
        paramMap.put("loginName", loginName);

        return this.getResultObject(url,paramMap, RequestMethod.GET,type);
    }

    /*
    在办列表
     */
    public TaskListResult getDoingTaskList(String loginName){

        String url = api.getAPI_GET_TASK_LIST_DOING();
        Type type = new TypeToken<TaskListResult>() {
        }.getType();
        Map<Object, Object> paramMap = new HashMap<Object, Object>();
        paramMap.put("loginName", loginName);

        return this.getResultObject(url,paramMap, RequestMethod.GET,type);
    }

    /*
    项目详情
     */
    public ProjectInfoResult getProjectInfo(Long id){
        String url = api.getAPI_GET_PROJECT_INFO();
        Type type = new TypeToken<ProjectInfoResult>() {
        }.getType();
        Map<Object, Object> paramMap = new HashMap<Object, Object>();
        paramMap.put("id", id.toString());

        return this.getResultObject(url,paramMap, RequestMethod.GET,type);
    }

    /*
    签收接口
     */
    public TaskDetailInfoModel getSignTask(Long taskInstDbid, String loginName){
        String url = api.getAPI_GET_TASK_SIGN();
        Type type = new TypeToken<TaskDetailInfoModel>() {
        }.getType();
        Map<Object, Object> paramMap = new HashMap<Object, Object>();
        paramMap.put("taskInstDbid", taskInstDbid);
        paramMap.put("loginName", loginName);

        return this.getResultObject(url,paramMap, RequestMethod.GET,type);
    }

    /*
    获取发送基础数据（是否显示下一环节，获取环节信息）
     */
    public List<SendBaseInfoResult> getSendBaseInfo(Long taskInstDbid){
        String url = api.get_SEND_BASE_INFO();
        Type type = new TypeToken<List<SendBaseInfoResult>>() {
        }.getType();
        Map<Object, Object> paramMap = new HashMap<>();
        paramMap.put("taskInstDbid", taskInstDbid);

        return this.getResultObject(url,paramMap, RequestMethod.GET,type);
    }

    /*
    获取下一环节参与人列表
     */
    public List<NextLinkPersonModel> getNextLinkPersonList(Long taskInstDbid,String linkName){

        String url = api.get_NEXT_LINK_PERSON_LIST();
        Type type = new TypeToken<List<CustomTreeForm>>() {
        }.getType();
        Map<String ,String > paramMap = new HashMap<>();
        paramMap.put("taskInstDbid",taskInstDbid.toString());
        paramMap.put("destActivityName",linkName);
        return this.getResultObject(url,paramMap, RequestMethod.GET,type);

    }

    /*
    发送在办环节信息
     */
    public Response getSendLinkDoingContent(Map<String ,String > paramMap){
        String url = api.get_SENG_LINK_DOING_CONTENT();
        return this.getResultObject(url,paramMap, RequestMethod.GET,Response.class);
    }

    /*
    获取附件目录
     */
    public List<CustomTreeForm> getAttachmentCatalog(Map<Object, Object> paramMap){
        String url = api.getATTACHMENT_CATELOG();
        Type type = new TypeToken<List<CustomTreeForm>>() {
        }.getType();
        return this.getResultObject(url,paramMap, RequestMethod.GET,type);
    }

    // 获取附件列表
    public SysFileFormResult getAttachmentList(String entityId, String entity,
                                               int page, int limit) {
        String url = api.getAPI_GET_ATTACHMENT_LIST_DATA();
        Type type = new TypeToken<SysFileFormResult>() {
        }.getType();
        Map<Object, Object> paramMap = new HashMap<Object, Object>();
        paramMap.put("entityId", entityId);
        paramMap.put("entity", entity);
        paramMap.put("page", page + "");
        paramMap.put("limit", limit + "");
        return getResultObject(url, paramMap, RequestMethod.GET, type);
    }

    // 上传文件
    public Response getUploadFile(Long userId, String entityId, String entity,
                                  String path, String fileName) {
        String url = api.getAPI_GET_UPLOAD_FILE();
        Map<Object, Object> paramMap = new HashMap<Object, Object>();
        paramMap.put("userId", userId + "");
        paramMap.put("entity", entity);
        paramMap.put("entityId", entityId);
        paramMap.put(
                "fileStr",
                JsonManager.getInstance(context).getJson(
                        getUploadFiles(path, fileName)));
        return this.getResultObject(url, paramMap, RequestMethod.POST,
                Response.class);
    }

    public List<UploadFile> getUploadFiles(String path, String fileName) {
        List<UploadFile> uploadFiles = new ArrayList<UploadFile>();
        if (path != null) {
            UploadFile uploadFile = new UploadFile();
            uploadFile.setContentType(MyFileUtil.getFileType((new File(path))
                    .getName()));
            if (fileName != null) {
                uploadFile.setName(fileName);
            } else {
                uploadFile.setName((new File(path)).getName());
            }
            uploadFile.setBase64Str(MyFileUtil.encodeBase64File(path));
            uploadFiles.add(uploadFile);
        }

        return uploadFiles;
    }

    // 删除附件文件
    public Response getDeleteFile(Long userId, String  sysFileId) {
        String url = api.getAPI_GET_DELETE_FILE();
        Map<Object, Object> paramMap = new HashMap<Object, Object>();
        paramMap.put("userId", userId + "");
        paramMap.put("sysFileIds", sysFileId);
        return this.getResultObject(url, paramMap, RequestMethod.POST,
                Response.class);
    }

}
