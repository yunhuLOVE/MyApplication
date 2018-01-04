package com.augur.zongyang.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.augur.zongyang.R;
import com.augur.zongyang.manager.ToastManager;

/**
 * Created by yunhu on 2017-12-11.
 */

public class Dialog_Setting {

    private Activity activity;

    public Dialog_Setting(Activity activity) {

        this.activity = activity;


    }

    public static Dialog getDialog(Activity activity) {
        if (activity == null)
            return null;

        LayoutInflater inflater = activity.getLayoutInflater();

        View customDialog = inflater.inflate(R.layout.dialog_setting, null);

        final AlertDialog dialog = new AlertDialog.Builder(activity).create();

        //不加这行代码，会导致无法弹出软键盘
        dialog .setView(activity.getLayoutInflater().inflate(R.layout.dialog_setting, null));

        dialog.show();

        dialog.getWindow().setContentView(customDialog);

        dialog.setCanceledOnTouchOutside(false);

//        Window window = dialog.getWindow();
////        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);

        setViewAttr(customDialog,dialog,activity);

        return dialog;
    }

    /*
    设置dialog界面属性
     */
    public static void setViewAttr(View view, final Dialog dialog, final Activity activity) {

        //返回
        ImageView iv_back = view.findViewById(R.id.imageView);
        //地址输入
        final EditText et_url = view.findViewById(R.id.editText);
        //保存
        Button btn_save = view.findViewById(R.id.button);

        SharedPreferences sp = activity.getSharedPreferences("sp_ip", Context.MODE_PRIVATE);

        String ip = sp.getString("ip", activity.getString(R.string.default_base_url));

        et_url.setText(ip);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = activity.getSharedPreferences("sp_ip", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("ip", et_url.getText().toString());
                boolean isSuccess = editor.commit();
                if(isSuccess) {
                    ToastManager.getInstance(activity).shortCenterToast("保存成功！");
                    dialog.dismiss();
                }
                else
                    ToastManager.getInstance(activity).shortCenterToast("保存失败，请重试！");
            }
        });

    }


}
