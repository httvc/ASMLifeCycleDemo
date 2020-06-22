package com.httvc.asmlifecycledemo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.httvc.pluginlibrary.InterfaceService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ProxyService extends Service {
    private String serviceName;
    private InterfaceService interfaceService;
    public ProxyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        init(intent);
       return null;
    }

    private void init(Intent intent) {
        serviceName=intent.getStringExtra("serviceName");
        try {
            //加载service类
            Class<?> aClass = getClassLoader().loadClass(serviceName);
            Constructor<?> constructor = aClass.getConstructor(new Class[]{});
            Object in = constructor.newInstance(new Object[]{});
            interfaceService=(InterfaceService)in;
            interfaceService.attach(this);

            Bundle bundle=new Bundle();
            bundle.putInt("from",1);
            interfaceService.onCreate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (interfaceService==null){
            init(intent);
        }
        return interfaceService.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        interfaceService.onUnbind(intent);
        return super.onUnbind(intent);
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }
}
