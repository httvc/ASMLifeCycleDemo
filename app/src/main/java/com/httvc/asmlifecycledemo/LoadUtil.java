package com.httvc.asmlifecycledemo;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

public class LoadUtil {
    private final static String apkPath="/sdcard/plugin-debug.apk";
    public static void loadClass(Context context){
        /**
         * 目的：拿到宿主的dexElements,拿到插件的dexElements,然后合并
         * dexElements 的对象 -> DexPathList类的对象->BaseDexClassLoader的对象
         */

        try {
            //DexPathList的field对象
            Class<?> classLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField=classLoaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);

            //dexElements的field对象
            Class<?> dexPathListClass = Class.forName("dalvik.system.DexPathList");
            Field dexElementsField=dexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);

            //宿主
            //BaseDexClassLoader
            ClassLoader pathClassLoader = context.getClassLoader();

            //DexPathList类的对象
            Object hostPathList = pathListField.get(pathClassLoader);

            //dexElements 对象
            Object[] hostDexElements=(Object[]) dexElementsField.get(hostPathList);

            //插件
            //BaseDexClassLoader对象
            //第二个参数8.0及之后可以null，第四个参数7.0之后可以为null
             File apkFile=new File(Environment.getExternalStorageDirectory().getPath()
                    +File.separator+"plugin-debug.apk");
            ClassLoader pluginClassLoader = new DexClassLoader(apkPath,context.getCacheDir().getAbsolutePath(),null,pathClassLoader);

            //DexPathList类的对象
            Object pluginPathList = pathListField.get(pluginClassLoader);

            //dexElements 对象
            Object[] pluginDexElements=(Object[]) dexElementsField.get(pluginPathList);

            //创建数组 new Elements[]
            Object[] newDexElements=(Object[]) Array.newInstance(hostDexElements.getClass().getComponentType(),
                    hostDexElements.length+pluginDexElements.length);

            Log.d("sssssssss","插件已加载");
            //填充数据
            System.arraycopy(hostDexElements,0,newDexElements,0,hostDexElements.length);
            System.arraycopy(pluginDexElements,0,newDexElements,hostDexElements.length,pluginDexElements.length);

            //赋值
            dexElementsField.set(hostPathList,newDexElements);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
