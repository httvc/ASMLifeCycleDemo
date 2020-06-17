package com.httvc.asmlifecycledemo;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LoadUtil.loadClass(this);
        HookUtils.hookAMS();
        HookUtils.hookHandler();
    }
}
