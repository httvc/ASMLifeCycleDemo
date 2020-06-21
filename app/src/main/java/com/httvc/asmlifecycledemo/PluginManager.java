package com.httvc.asmlifecycledemo;

/*public class PluginManager {
    private static volatile  PluginManager instance=null;
    private PluginManager(){}
    public static PluginManager getInstance(){
        if (instance==null){
            synchronized (PluginManager.class){
                if (instance==null){
                    instance=new PluginManager();
                }
            }
        }
        return instance;
    }
}*/

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class  PluginManager{

    private Resources resources;
    private DexClassLoader dexClassLoader;
    private PackageInfo packageArchiveInfo;

    private PluginManager(){}
    private static class PluginManagerInstance{
      private static final PluginManager instance=new PluginManager();
    }

    public static PluginManager getInstance(){
        return PluginManagerInstance.instance;
    }

    public void loadPath(Context context){
        File fileDir=context.getDir("plugin",Context.MODE_PRIVATE);
        String name="plugin.apk";
        String path=new File(fileDir,name).getAbsolutePath();

        PackageManager packageManager = context.getPackageManager();
        packageArchiveInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);

        //获取Activity
        File dex = context.getDir("dex", Context.MODE_PRIVATE);
        dexClassLoader = new DexClassLoader(path, dex.getAbsolutePath(), null, context.getClassLoader());

       //resouce
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager,path);

            resources = new Resources(assetManager, context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Resources getResources(){
        return resources;
    }

    public DexClassLoader getDexClassLoader(){
        return dexClassLoader;
    }

    public PackageInfo getPackInfo(){
        return packageArchiveInfo;
    }
}
