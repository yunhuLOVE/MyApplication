package com.augur.zongyang.activity.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.augur.zongyang.R;
import com.augur.zongyang.bean.CurrentUser;
import com.augur.zongyang.bean.OmUserData;
import com.augur.zongyang.common.SettingPreference;
import com.augur.zongyang.manager.ToastManager;
import com.augur.zongyang.activity.menu.MainMenuActivity;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.OmUserHttpOpera;
import com.augur.zongyang.network.operator.base.BaseHttpOpera;
import com.augur.zongyang.task.GenericTask;
import com.augur.zongyang.task.TaskAdapter;
import com.augur.zongyang.task.TaskListener;
import com.augur.zongyang.task.TaskParams;
import com.augur.zongyang.task.TaskResult;
import com.augur.zongyang.util.constant.AppConstant;
import com.augur.zongyang.util.db.SqliteTemplate;
import com.augur.zongyang.widget.Dialog_Setting;

import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private Context context;//上下文
    private AutoCompleteTextView et_username;//用户名输入框
    private EditText et_psw;//密码输入框
    private CheckBox cb_rememberPsw;//记住密码
    private Button btn_login;//登录按钮
    private TextView tv_version;//版本号
    private ImageView iv_setting;//设置按钮

    private GenericTask mLoginTask;
    private ProgressDialog mydialog;

    private boolean isFirst = true;
    public static final String SETTING_NEW = "setting_NEWs";
    public static final String REGIST = "REGIST";
    public static final String Key = "Key";
    public static final String LOGINNAME = "name";
    public static final String PASSWORD = "password";
    public static final String LASTNAME = "lastname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initData();
    }

    /*
    初始化界面控件
     */
    private void initView() {

        et_username = findViewById(R.id.et_username);
        et_psw = findViewById(R.id.et_psw);
        cb_rememberPsw = findViewById(R.id.cb_rememberPsw);
        btn_login = findViewById(R.id.btn_login);
        tv_version = findViewById(R.id.tv_version);
        iv_setting = findViewById(R.id.iv_setting);
    }

    /*
    初始化数据
     */
    private void initData() {

        try {
            context = this;
            //默认记住密码
            cb_rememberPsw.setChecked(true);
            //设置监听
            setListening();
            //填充用户名密码
            this.initSetName();
            //设置版本号
            tv_version.setText("V" + this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_login:
                    doLogin();
                    break;

                case R.id.iv_setting:

                   Dialog dialog =  Dialog_Setting.getDialog(LoginActivity.this);
                    break;
            }
        }
    };

    private void setListening() {

        btn_login.setOnClickListener(listener);
        iv_setting.setOnClickListener(listener);

        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_psw.getText().clear();
                ;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFirst) {
                    isFirst = false;
                    initNameAdapter();
                }
                LoginActivity.this.setPassword();
            }
        });
    }


    /*
    填充用户名密码
     */
    private void initSetName() {
        if (et_username == null)
            return;
        et_username.setText(getLoginNameFromSharedPreferences(this));
        this.setPassword();
    }

    /*
        获取最新保存用户名
     */
    public static String getLoginNameFromSharedPreferences(Context context) {
        SharedPreferences setting = context
                .getSharedPreferences(SETTING_NEW, 0);
        String lastnameString = setting.getString(LASTNAME, "");
        String[] lastname = lastnameString.split(":");
        int len = lastname.length - 1;
        return lastname[len];
    }

    /*
    设置用户名控件适配器
     */
    private void initNameAdapter() {
        SharedPreferences setting = getSharedPreferences(SETTING_NEW, 0);
        String namesString = setting.getString(LOGINNAME, "");
        String[] names = namesString.split(":");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, names);
        et_username.setAdapter(adapter); // 显示最后登录账号
        et_username.setThreshold(1);
        et_username.setDropDownVerticalOffset(2);
        this.setPassword();
    }

    /*
    填充密码框
     */
    private void setPassword() {
        SharedPreferences setting = getSharedPreferences(SETTING_NEW, 0);
        String namesString = setting.getString(LOGINNAME, "");
        String passwordString = setting.getString(PASSWORD, "");
        String[] names = namesString.split(":");
        String[] passwords = passwordString.split(":");
        String username = et_username.getText().toString();
        if (passwords != null && passwords.length > 1) {
            for (int i = 0; i < names.length; i++) {
                if (username.equals(names[i])) {
                    et_psw.setText(passwords[i]);
                }
            }
        }
    }

    // Login task.
    private void doLogin() {
        String mIdString = et_username.getText().toString();
        String mPwdString = et_psw.getText().toString();
        if (TextUtils.isEmpty(mIdString) || TextUtils.isEmpty(mPwdString)) {
            ToastManager.getInstance(getApplicationContext()).shortToast(
                    "用户或密码不能为空");
            return;
        }

        if (mLoginTask != null
                && mLoginTask.getStatus() == GenericTask.Status.RUNNING) {
            return;
        } else {
            mLoginTask = new LoginTask();
            mLoginTask.setListener(mLoginTaskListener);

            TaskParams params = new TaskParams();
            params.put("username", mIdString);
            params.put("password", mPwdString);
            mLoginTask.execute(params);
        }
    }

    private final TaskListener mLoginTaskListener = new TaskAdapter() {

        @Override
        public void onPreExecute(GenericTask task) {
            onLoginBegin(task);
        }

        @Override
        public void onProgressUpdate(GenericTask task, Object param) {
            updateProgress((String) param);
        }

        @Override
        public void onPostExecute(GenericTask task, TaskResult result) {
            if (task.isCancelled()) {
                return;
            }
            if (result == TaskResult.OK) {
                onLoginSuccess();
            } else {
                onLoginFailure(((LoginTask) task).getMsg());
            }
        }

        @Override
        public String getName() {
            // TODO Auto-generated method stub
            return "Login";
        }
    };

    private void enableLogin() {
        et_username.setEnabled(true);
        et_psw.setEnabled(true);
        btn_login.setEnabled(true);
    }

    private void disableLogin() {
        et_username.setEnabled(false);
        et_psw.setEnabled(false);
        btn_login.setEnabled(false);
    }

    private void updateProgress(String progress) {
        if (mydialog != null) {
        } else {
            Toast.makeText(LoginActivity.this, progress, Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void onLoginSuccess() {
        enableLogin();
        mydialog.dismiss();
        new GoToMainActivityAsyncTask().execute();
    }

    private void onLoginBegin(final GenericTask task) {
        disableLogin();
        mydialog = new ProgressDialog(this);
        // 设置进度条风格，风格为圆形，旋转的
        mydialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置ProgressDialog 标题
        // mydialog.setTitle("登录");
        // 设置ProgressDialog 提示信息
        mydialog.setMessage("登录中,请稍后...");
        // 设置ProgressDialog 是否可以按退回按键取消
        mydialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enableLogin();
                task.cancel(true);
            }
        });
        // 让ProgressDialog显示
        mydialog.show();
    }

    private void onLoginFailure(String reason) {
        Toast.makeText(this, reason, Toast.LENGTH_SHORT).show();
        if (mydialog != null) {
            mydialog.dismiss();
        }
        enableLogin();
    }

    private class LoginTask extends GenericTask {
        private String msg = getString(R.string.login_status_failure);

        public String getMsg() {
            return msg;
        }

        @Override
        protected TaskResult _doInBackground(TaskParams... params) {
            TaskParams param = params[0];
            publishProgress(getString(R.string.login_status_logging_in) + "...");
            try {
                String username = param.getString("username");
                String password = param.getString("password");
                OmUserHttpOpera op = NetworkHelper.getInstance(
                        LoginActivity.this).getHttpOpera(OmUserHttpOpera.class);

                op.setOnNetResultListener(new BaseHttpOpera.OnNetResultListener() {
                    @Override
                    public void onNetNotConnect() {
                        LoginActivity.this.notNetWorkHandler.obtainMessage()
                                .sendToTarget();
                    }
                });
                String userName = username.trim();
                String psw = password.trim();
                String uname = URLEncoder.encode(userName, "UTF-8");
                Boolean isRightUser = false;
                String loginResult = "";
                if (AppConstant.OLDLOGIN) {
                    String checkResult = op.loginChecked(username.trim(),
                            password.trim());
                    if ("IOException".equals(checkResult)) {
                        msg = getString(R.string.login_status_ip_failure);
                        publishProgress(msg);
                        return TaskResult.FAILED;
                    } else if ("false".equals(checkResult)) {
                        msg = getString(R.string.login_status_failure);
                        publishProgress(msg);
                        return TaskResult.FAILED;
                    } else {
                        isRightUser = true;
                    }
                } else {

                    isRightUser = NetworkHelper.getInstance(LoginActivity.this)
                            .getHttpOpera(OmUserHttpOpera.class)
                            .checkOmUser(uname, psw);

                }
                if (isRightUser == null || !isRightUser) {

                    if ("IOException".equals(loginResult)) {
                        msg = getString(R.string.login_status_ip_failure);
                    }
                    if ("SocketTimeoutException".equals(loginResult)) {
                        msg = getString(R.string.connection_timeout);
                    }else{
                        msg = getString(R.string.login_status_failure);
                    }
                    publishProgress(msg);
                    return TaskResult.FAILED;
                } else {

                    OmUserData omUser = op.getUserInfo(uname);// 根据登录名获取用户信息
                    if (omUser != null
                            && omUser.getUserCode() != null) {
                        CurrentUser.getInstance().setCurrentUser(omUser);//
                        if (cb_rememberPsw.isChecked()) {
                            SharedPreferences setting = getSharedPreferences(
                                    SETTING_NEW, 0);
                            SharedPreferences.Editor editor = setting.edit();
                            String namesString = setting.getString(LOGINNAME,
                                    "");
                            String[] names = namesString.split(":");

                            String passwordString = setting.getString(PASSWORD,
                                    "");
                            String[] passwords = passwordString.split(":");
                            String passw = et_psw.getText().toString();

                            for (int i = 0; i < names.length; i++) {
                                if (username.equals(names[i])) {
                                    editor.remove(LASTNAME).commit();
                                    String lastnameString2 = setting.getString(
                                            LASTNAME, "");
                                    editor.putString(LASTNAME, lastnameString2
                                            + ":" + username);
                                    if (!(passw.equals(passwords[i]))) {
                                        // 用户修改了密码
                                        editor.remove(LOGINNAME).commit();
                                        editor.remove(PASSWORD).commit();
                                        String namesString2 = setting
                                                .getString(LOGINNAME, "");
                                        String passwordString2 = setting
                                                .getString(PASSWORD, "");
                                        editor.putString(LOGINNAME,
                                                namesString2 + ":" + username);
                                        editor.putString(PASSWORD,
                                                passwordString2 + ":" + passw);
                                    }
                                    editor.commit();
                                    return TaskResult.OK;
                                }
                            }
                            editor.putString(LOGINNAME, namesString + ":"
                                    + username);
                            editor.putString(PASSWORD, passwordString + ":"
                                    + passw);
                            editor.remove(LASTNAME).commit();
                            String lastnameString = setting.getString(LASTNAME,
                                    "");
                            editor.putString(LASTNAME, lastnameString + ":"
                                    + username);
                            editor.commit();
                            return TaskResult.OK;
                        }
                    } else {
                        msg = "用户名密码错误";
                        publishProgress(msg);
                        return TaskResult.FAILED;
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                msg = getString(R.string.login_status_network_or_connection_error);
                publishProgress(msg);
                return TaskResult.FAILED;
            }
            return TaskResult.OK;
        }
    }

    private final Handler notNetWorkHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(LoginActivity.this, "网络未连接，请检查网络设置后重试",
                    Toast.LENGTH_SHORT).show();
        }
    };

    public class GoToMainActivityAsyncTask extends
            AsyncTask<Void, Void, Integer> {

        ProgressDialog dialog;
        private final static int SUCCESS = 0;
        private final static int FAILE = 1;

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            switch (result) {
                case SUCCESS:
                    gotoMainView();
                    break;
                case FAILE:
                    LoginActivity.this.onLoginFailure("数据加载失败，请重新登陆！");
                    break;
            }
            dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("登录成功，请稍后...");
            dialog.setCancelable(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    GoToMainActivityAsyncTask.this.cancel(true);
                }
            });
            dialog.show();
        }

        Handler handler = new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final int what = msg.what;
                Bundle msgBundle = msg.getData();
                switch (what) {
                    case R.id.tittle:
                        dialog.setMessage("正在准备" + msgBundle.getString("msg")
                                + "数据，请稍后...");
                        break;
                    case R.id.toast:
                        Toast.makeText(LoginActivity.this,
                                msgBundle.getString("msg") + "数据加载成功",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        @Override
        protected void onCancelled() {
            if (this.isCancelled()) {

                SqliteTemplate.closeDatabase();
                LoginActivity.this.onLoginFailure("数据库未加载完成，请重新登录!");
            }
        }

        private void showDialogMessage(String msg) {
            Bundle bundle = new Bundle();
            bundle.putString("msg", msg);
            Message message = this.handler.obtainMessage(R.id.tittle);
            message.setData(bundle);
            message.sendToTarget();
        }

        private void showToastMessage(String msg) {
            Bundle bundle = new Bundle();
            bundle.putString("msg", msg);
            Message message = this.handler.obtainMessage(R.id.toast);
            message.setData(bundle);
            message.sendToTarget();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            SettingPreference settingPreference = new SettingPreference(
                    LoginActivity.this);
            boolean isOpen = settingPreference.getPreferences().getBoolean(
                    "gps_enable", true);
            boolean isUpdateDB = settingPreference.getPreferences().getBoolean(
                    SettingPreference.DB_UPDATE, true);
            if (isUpdateDB) {// 是否更新数据库
//                if (!this.updateDBData()) {
//                    return FAILE;
//                }
            }

            return SUCCESS;
        }
    }

        public  void regist(Context context) {
            SharedPreferences setting = context
                    .getSharedPreferences(SETTING_NEW, 0);
            setting.edit().putBoolean(REGIST, true).commit();
        }

        public  void unRegist(Context context) {
            SharedPreferences setting = context
                    .getSharedPreferences(SETTING_NEW, 0);
            setting.edit().putBoolean(REGIST, false).commit();
        }

        public  boolean isRegist(Context context) {
            SharedPreferences setting = context
                    .getSharedPreferences(SETTING_NEW, 0);
            return setting.getBoolean(REGIST, false);
        }

        private void gotoMainView() {
            // TODO Auto-generated method stub
            this.regist(this);
            if (et_username != null) {
                updateProgress("");
                et_username.setText("");
                et_psw.setText("");
            }

            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            startActivityForResult(intent, 0);
        }
}
