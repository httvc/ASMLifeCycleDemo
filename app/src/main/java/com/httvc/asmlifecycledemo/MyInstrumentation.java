package com.httvc.asmlifecycledemo;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class MyInstrumentation extends Instrumentation {
    private final Context mContext;
    private final Instrumentation mInstr;
    public MyInstrumentation(Instrumentation baseInstrumentation, Context context) {
        mContext=context;
        mInstr=baseInstrumentation;
    }

    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        Log.e("Main","走了我的CallActivityOnCreate");
        Log.e("Main","activity="+activity);
        Log.e("Main","Activity class="+activity.getClass());
        Log.e("Main","activity resouce="+activity.getResources());

        try {
            //拿到ContextWrapper类中的字段mBase字段，就是context
            Class<? extends Activity> aClass = activity.getClass();
            Log.e("Main","activity aClass="+aClass);
            Log.e("Main","Activity aClass aClass="+aClass.getSuperclass());
            Log.e("Main","activity aClass aClass aClass="+aClass.getSuperclass().getSuperclass());
            Field mBaseField = Activity.class.getSuperclass().getSuperclass().getDeclaredField("mBase");
            mBaseField.setAccessible(true);

            Context mBase =(Context) mBaseField.get(activity);
            Log.e("Main","mBase="+mBase);

            //拿出Context中的Resource字段
            Class<?> mContextImplClass = Class.forName("android.app.ContextImpl");
            Field mResourcesField = mContextImplClass.getDeclaredField("mResources");
            mResourcesField.setAccessible(true);

            //创建我们自己的Resource
           // String apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pluginlibrary-debug.aar";
            String apkPath="/sdcard/plugin-debug.apk";
            String mPath=mContext.getApplicationContext().getPackageResourcePath();

            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPathMethod.setAccessible(true);

           // addAssetPathMethod.invoke(assetManager,mPath);
            addAssetPathMethod.invoke(assetManager,apkPath);

            Method ensureStringBlocks = AssetManager.class.getDeclaredMethod("ensureStringBlocks");
            ensureStringBlocks.setAccessible(true);
            ensureStringBlocks.invoke(assetManager);

            Resources supResources = mContext.getResources();
            Log.e("main","supResource="+supResources);
            Resources newResouce=new Resources(assetManager,supResources.getDisplayMetrics(),supResources.getConfiguration());

            Resources.Theme mTheme = newResouce.newTheme();
            mTheme.setTo(mContext.getApplicationContext().getTheme());
            mResourcesField.set(mBase,newResouce);
            Log.e("Main","设置getResouce="+activity.getResources());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        super.callActivityOnCreate(activity, icicle);
    }
}
