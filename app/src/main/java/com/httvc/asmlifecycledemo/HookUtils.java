package com.httvc.asmlifecycledemo;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookUtils {
    private final static String TARGET_INTENT = "target_intent";

    public static void hookAMS() {
        //创建一个代理对象----IActivityManager
        try {
            //IActivityManager的对象--mInstance的Field --》Singleton类的对象
            // -》IActivityManagerSingleton --》ActivityManager的class

            Class<?> iActivityManagerClass = Class.forName("android.app.IActivityManager");
            Field singletonFile = null;
            if (Build.VERSION.SDK_INT >= 26) {
                Class<?> activityManagerClass = Class.forName("android.app.ActivityManager");
                singletonFile = activityManagerClass.getDeclaredField("IActivityManagerSingleton");
            } else {
                Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
                singletonFile = activityManagerNativeClass.getDeclaredField("gDefault");
            }
            singletonFile.setAccessible(true);
            Object singleton = singletonFile.get(null);
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            final Object mInstance = mInstanceField.get(singleton);
            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{iActivityManagerClass}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    //替换Intent
                    if ("startActivity".equals(method.getName())) {
                        int index = 0;
                        for (int i = 0; i < args.length; i++) {
                            if (args[i] instanceof Intent) {
                                index = i;
                                break;
                            }
                        }
                        //插件
                        Intent intent = (Intent) args[index];

                        //替换
                        Intent proxyIntent = new Intent();
                        proxyIntent.setClassName("com.httvc.asmlifecycledemo", "com.httvc.asmlifecycledemo.Proxy2Activity");
                        proxyIntent.putExtra(TARGET_INTENT, intent);
                        Log.d("sss", "替换成proxy完成");
                        args[index] = proxyIntent;

                    }
                    //obj是IActivityManager对象
                    return method.invoke(mInstance, args);
                }
            });

            //赋值给系统的IActivityManager对象--覆盖
            mInstanceField.set(singleton, proxyInstance);
            Log.d("sss", "Hook Finsih");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void hookHandler(Context context) {
        //mCallback -->Handler对象(mH) -->ActivityThread类的对象-->sCurrentActivityThread对象
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object activityThread = sCurrentActivityThreadField.get(null);

            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            Object mH = mHField.get(activityThread);

            Class<?> handlerClass = Class.forName("android.os.Handler");
            Field mCallbackField = handlerClass.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);

            //目的 Intent -->ActivityClientRecord的对象 --》msg.obj -> Callback 获取msg
            Handler.Callback callback = new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    //直接获取了msg
                    switch (msg.what) {
                        case 100:
                            //ActivityClientRecord对象
                            try {
                                Field intentField = msg.obj.getClass().getDeclaredField("intent");
                                intentField.setAccessible(true);
                                //代理Intent
                                Intent proxyIntent = (Intent) intentField.get(msg.obj);
                                //插件intent
                                Intent intent = proxyIntent.getParcelableExtra(TARGET_INTENT);
                                if (intent != null) {
                                    intentField.set(msg.obj, intent);
                                }
                                Log.d("sss", "替换成plugin完成");
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    return false;
                }
            };
            Log.d("sss", "plugin替换完成");
            //替换系统的callback
            mCallbackField.set(mH, callback);

            //获取mInstrumentation字段
            Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation baseInstrumentation = (Instrumentation) mInstrumentationField.get(activityThread);

            //设置我们自己的mInstrumentation
            Instrumentation proxy= new MyInstrumentation(baseInstrumentation,context);
            //替换
            mInstrumentationField.set(activityThread,proxy);
            Log.e("Main","替换Resource成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
