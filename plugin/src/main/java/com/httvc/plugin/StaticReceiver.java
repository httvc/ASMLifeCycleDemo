package com.httvc.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StaticReceiver extends BroadcastReceiver {
    static final String ACTION="com.httvc.asmlifecycledemo.Receivel.PLUGIN_ACTION";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("StaticReceiver","我是插件，收到宿主的消息 静态注册的广播 收到宿主的消息");
        context.sendBroadcast(new Intent(ACTION));
    }
}
