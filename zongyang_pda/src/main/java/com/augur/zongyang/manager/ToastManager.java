package com.augur.zongyang.manager;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by yunhu on 2017-12-11.
 */

public class ToastManager {

    private static ToastManager a;
    private final Context b;

    public static ToastManager getInstance(Context paramContext)
    {
        if (a == null) {
            a = new ToastManager(paramContext);
        }
        return a;
    }

    private ToastManager(Context paramContext)
    {
        this.b = paramContext;
    }

    public void shortCenterToast(String param){
        Toast toast = Toast.makeText(this.b, param, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void longCenterToast(String param){
        Toast toast = Toast.makeText(this.b, param, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void shortToast(int paramInt)
    {
        Toast.makeText(this.b, paramInt, Toast.LENGTH_SHORT).show();
    }

    public void shortToast(String paramString)
    {
        Toast.makeText(this.b, paramString, Toast.LENGTH_SHORT).show();
    }

    public void longToast(String paramString)
    {
        Toast.makeText(this.b, paramString, Toast.LENGTH_LONG).show();
    }

    public void longToast(int paramInt)
    {
        Toast.makeText(this.b, paramInt, Toast.LENGTH_LONG).show();
    }

}
