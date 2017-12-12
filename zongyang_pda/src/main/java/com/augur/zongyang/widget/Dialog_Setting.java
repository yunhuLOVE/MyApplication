package com.augur.zongyang.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.augur.zongyang.R;

/**
 * Created by yunhu on 2017-12-11.
 */

public class Dialog_Setting {

    private Activity activity;

    public Dialog_Setting(Activity activity){

        this.activity = activity;


    }

    public static Dialog getDialog(Activity activity){
        if(activity == null)
            return null;
        LayoutInflater inflater = activity.getLayoutInflater();

        View customDialog = inflater.inflate(R.layout.dialog_setting,null);

        final Dialog dialog = new AlertDialog.Builder(activity).create();

        dialog.show();

        dialog.getWindow().setContentView(customDialog);

        dialog.setCanceledOnTouchOutside(false);

        Window window = dialog.getWindow();
//        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);




        return dialog;
    }
}
