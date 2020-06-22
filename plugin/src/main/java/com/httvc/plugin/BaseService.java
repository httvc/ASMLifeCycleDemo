package com.httvc.plugin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.httvc.pluginlibrary.InterfaceService;

public class BaseService extends Service implements InterfaceService {
    private Service that;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void attach(Service proxyService) {
        this.that=proxyService;
    }

    @Override
    public void onCreate() {
        if (that==null) {
            super.onCreate();
        }
    }
}
