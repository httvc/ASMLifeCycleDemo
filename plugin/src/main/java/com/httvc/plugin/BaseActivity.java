package com.httvc.plugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.httvc.pluginlibrary.InterfaceActivity;

public class BaseActivity extends AppCompatActivity implements InterfaceActivity {
    protected Activity that;

    @Override
    public void attach(Activity proxyActivity) {
        this.that = proxyActivity;
    }

    @Override
    public void setContentView(View view) {
        if (that != null) {
            that.setContentView(view);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (that != null) {
            that.setContentView(layoutResID);
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        if (that==null){
            super.onCreate(saveInstanceState);
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return (T) (that==null ?super.findViewById(id):that.findViewById(id));
    }

    @Override
    public void startActivity(Intent intent) {
        Intent m=new Intent();
        m.putExtra("className", intent.getComponent().getClassName());
        that.startActivity(m);
    }

    @Override
    public ComponentName startService(Intent intent) {
        Intent m=new Intent();
        m.putExtra("serviceName",intent.getComponent().getClassName());
        return that.startService(m);
    }

    //重新广播注册
    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return that.registerReceiver(receiver, filter);
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        that.unregisterReceiver(receiver);
    }

    //重写广播发送
    @Override
    public void sendBroadcast(Intent intent) {
        that.sendBroadcast(intent);
    }

    @Override
    public void onStart() {
        if (that==null){
            super.onStart();
        }
    }

    @Override
    public void onResume() {
        if (that==null){
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (that==null){
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (that==null){
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (that==null){
            super.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (that==null){
            super.onSaveInstanceState(outState);
        }
    }
}
