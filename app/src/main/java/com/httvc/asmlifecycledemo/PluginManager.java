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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

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
        String name="plugin-debug.apk";
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

        parseReceivers(context,path);
    }

    //解析插件静态广播
    private void parseReceivers(Context context, String path) {
        try {
            Class<?> packageParserClass = Class.forName("android.content.pm.PackageParser");
            Method parsePackageMethod = packageParserClass.getDeclaredMethod("parsePackage", File.class, int.class);
            Object packageParser = packageParserClass.newInstance();
            Object packageObj=parsePackageMethod.invoke(packageParser,new File(path),PackageManager.GET_ACTIVITIES);

            Field receiversField=packageObj.getClass().getDeclaredField("receivers");
            List receivers=(List)receiversField.get(packageObj);

            Class<?> componentClass = Class.forName("android.content.pm.PackageParser$Component");
            Field intentsField = componentClass.getDeclaredField("intents");

            Class<?> packageParser$ActivityClass = Class.forName("android.content.pm.PackageParser$Activity");
            Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");
            Object defaltUserState = packageUserStateClass.newInstance();

            Method generateReceiverInfo = packageParserClass.getDeclaredMethod("generateActivityInfo", packageParser$ActivityClass, int.class, packageUserStateClass, int.class);

            Class<?> userHandler = Class.forName("android.os.UserHandle");
            Method getCallingUserId = userHandler.getDeclaredMethod("getCallingUserId");
            int userId = (int)getCallingUserId.invoke(null);

            for(Object activity:receivers){
                ActivityInfo info=(ActivityInfo) generateReceiverInfo.invoke(packageParser,activity,0,defaltUserState,userId);
                List<? extends IntentFilter> intentFilters = (List<? extends IntentFilter>) intentsField.get(activity);
                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) dexClassLoader.loadClass(info.name).newInstance();

                for (IntentFilter intentFilter:intentFilters){
                    context.registerReceiver(broadcastReceiver,intentFilter);
                }
            }
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
        } catch (NoSuchFieldException e) {
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
