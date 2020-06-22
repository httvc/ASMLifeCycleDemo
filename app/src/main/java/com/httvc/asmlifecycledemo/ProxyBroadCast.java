package com.httvc.asmlifecycledemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.httvc.pluginlibrary.InterfaceBroadcast;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ProxyBroadCast extends BroadcastReceiver {
   // private String className;
    private  InterfaceBroadcast interfaceBroadcast;

    public ProxyBroadCast(String className,Context context) {
     //   this.className=className;
        try {
            Class<?> aClass = PluginManager.getInstance().getDexClassLoader().loadClass(className);
            Constructor<?> constructor = aClass.getConstructor(new Class[]{});
            Object in = constructor.newInstance(new Object[]{});
            interfaceBroadcast = (InterfaceBroadcast)in;
            interfaceBroadcast.attach(context);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        interfaceBroadcast.onReceive(context,intent);
    }
}
