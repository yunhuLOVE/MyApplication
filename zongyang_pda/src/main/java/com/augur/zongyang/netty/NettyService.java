package com.augur.zongyang.netty;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.augur.zongyang.R;
import com.augur.zongyang.activity.mywork.MyWorkTabActivity;
import com.augur.zongyang.bean.CurrentUser;
import com.augur.zongyang.manager.JsonManager;
import com.augur.zongyang.util.constant.BundleKeyConstant;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class NettyService extends Service implements NettyListener {

    private String TAG = "NettyService";

    private NetworkReceiver receiver;

    private MyHandler handler = new MyHandler(this);

    private ScheduledExecutorService mScheduledExecutorService;

    private void shutdown() {
        if (mScheduledExecutorService != null) {
            mScheduledExecutorService.shutdown();
            mScheduledExecutorService = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        receiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        // 自定义心跳，每隔20秒向服务器发送心跳包
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                byte[] requestBody = "heartbeat".getBytes();
//                byte[] requestBody = {1, 5};

                NettyClient.getInstance().sendMsgToServer(requestBody, new ChannelFutureListener() {    //3
                    @Override
                    public void operationComplete(ChannelFuture future) {
                        if (future.isSuccess()) {                //4
                            Log.e(TAG, "Write heartbeat successful");

                        } else {
                            Log.e(TAG, "Write heartbeat error");
                            NettyClient.getInstance().reconnect();
                        }
                    }
                });
            }
        }, 20, 20, TimeUnit.SECONDS);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        connect();
        return START_NOT_STICKY;
    }

    private void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NettyClient.getInstance().setListener(NettyService.this);
                String ip = getResources().getString(R.string.base_ip);
                String port = getResources().getString(R.string.netty_port);
                NettyClient.getInstance().connect(NettyConstant.PORT, NettyConstant.IP);//连接服务器;
            }
        }).start();
    }

    @Override
    public void onServiceStatusConnectChanged(int statusCode) {
        if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {
            Log.e(TAG, "tcp connect successful");
            //发送用户数据
            authenticData();
        } else {
            Log.e(TAG, "tcp connect error");
        }
    }

    /*
    向服务端发送用户数据
     */
    private void authenticData() {

        Log.e(TAG, "authenticData");

        try {
            CurrentUser user = CurrentUser.getInstance();

            Map<String, String> params = new HashMap<>();
            params.put("type", "CONNECT");
            params.put("userId", user.getCurrentUser().getUserId().toString());
//            String jsonStr = JsonManager.getInstance(this).getJson(params);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            if (user != null)
                oos.writeObject(params);
            //得到person对象的byte数组
            byte[] authByteArray = bos.toByteArray();

            NettyClient.getInstance().sendMsgToServer(("userId:" + CurrentUser.getInstance().getCurrentUser().getUserId() + "\n").getBytes(), new ChannelFutureListener() {    //3
                @Override
                public void operationComplete(ChannelFuture future) {
                    if (future.isSuccess()) {
                        Log.e(TAG, "Write auth successful");
                    } else {
                        Log.e(TAG, "Write auth error");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    接收服务端推送数据
     */
    @Override
    public void onMessageResponse(Object obj) {

        try {
            String body = (String) obj;

            Log.e(TAG, "message:" + body);

            ResponseMessage message = JsonManager.getInstance(NettyService.this).getObject(body, ResponseMessage.class);

            if (message != null && message.getStatus() != null && message.getStatus().equals("db"))
                sendNotification(0);

        } catch (Exception e) {
            e.printStackTrace();
        }

//        handler.sendEmptyMessage(-1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        shutdown();
        NettyClient.getInstance().setReconnectNum(0);
        NettyClient.getInstance().disconnect();
    }

    public class NetworkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                        || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    connect();
                }
            }
        }
    }

    private class MyHandler extends Handler {

        private final WeakReference<NettyService> mService;

        private MyHandler(NettyService service) {
            this.mService = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NettyService service = mService.get();
            if (service != null && msg.what >= 0) {
                sendNotification(msg.what);
            }
        }
    }

    private void sendNotification(int flag) {
        String channelId = "channel_1";
        String channelName = "channel_name_1";
        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder;
        //android 版本 为8.0及以上
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(false); //是否在桌面icon右上角展示小红点
            channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
            channel.enableVibration(false);
            channel.setSound(null, null);
            notifyManager.createNotificationChannel(channel);
            builder = new Notification.Builder(this, channelId);
        } else {
            builder = new Notification.Builder(this);
        }
        //实例化NotificationCompat.Builde并设置相关属性

        //设置小图标
        builder.setSmallIcon(R.mipmap.zy_icon)
                //设置通知标题
                .setContentTitle("枞阳多规")
                //设置通知内容
                .setContentText("您有1件待办事项，请办理。")
                //用户点击就自动消失
                .setAutoCancel(true);
        // 添加声音提示

        //给 Notification 设置 Action
        Intent intent = new Intent();
        Bundle bundle;

        switch (flag) {
            case 0:
                bundle = new Bundle();
                bundle.putInt(BundleKeyConstant.TYPE, 0);
                bundle.putString(BundleKeyConstant.TITLE, "待办");
                intent.putExtras(bundle);
                intent.setClass(this, MyWorkTabActivity.class);
                break;

            case 1:
                bundle = new Bundle();
                bundle.putInt(BundleKeyConstant.TYPE, 1);
                bundle.putString(BundleKeyConstant.TITLE, "在办");
                intent.putExtras(bundle);
                intent.setClass(this, MyWorkTabActivity.class);
                break;

            case 3:

                break;
        }

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        Notification notification = builder.build();
        notification.defaults |= Notification.DEFAULT_SOUND;


        /*
        //获取一个用于启动 Activity 的 PendingIntent 对象
public static PendingIntent getActivity(Context context, int requestCode, Intent intent, int flags);

//获取一个用于启动 Service 的 PendingIntent 对象
public static PendingIntent getService(Context context, int requestCode, Intent intent, int flags);

//获取一个用于向 BroadcastReceiver 广播的 PendingIntent 对象
public static PendingIntent getBroadcast(Context context, int requestCode, Intent intent, int flags)
         */

        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        //.setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        notifyManager.notify(1, notification);

    }
}
