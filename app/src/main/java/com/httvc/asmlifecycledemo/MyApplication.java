package com.httvc.asmlifecycledemo;

import android.app.Application;

import com.httvc.asmlifecycledemo.classload.LoadUtil;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LoadUtil.loadClass(this);
    }
}
