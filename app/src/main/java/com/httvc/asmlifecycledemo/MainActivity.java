package com.httvc.asmlifecycledemo;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.httvc.asmlifecycledemo.classload.ISay;
import com.httvc.asmlifecycledemo.classload.SayException;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    ISay say;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TextView hello=(TextView) findViewById(R.id.hello);
        TextView world=(TextView) findViewById(R.id.world);
        Log.e("TAG","MainActivity------->onCreate()");
        say=new SayException();
        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(MainActivity.this,HomeActivity.class));
                //获取hotfix的jar包文件
                final File jarFile=new File(Environment.getExternalStorageDirectory().getPath()
                        +File.separator+"say_something_hotfix.jar");
                if (!jarFile.exists()){
                    say=new SayException();
                    Toast.makeText(MainActivity.this, say.saySomething(), Toast.LENGTH_SHORT).show();
                }else {
                    DexClassLoader dexClassLoader=new DexClassLoader(jarFile.getAbsolutePath(),
                            getExternalCacheDir().getAbsolutePath(),null,getClassLoader());
                    try{
                        //加载 SayHotFix类
                        Class clazz=dexClassLoader.loadClass("com.httvc.asmlifecycledemo.classload.SayHotFit");
                        //强转成ISay，ISay的包名需要和hotfix jar包中一致
                        ISay iSay=(ISay) clazz.newInstance();
                        Toast.makeText(MainActivity.this, iSay.saySomething(), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        world.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   try {
              //      ClassLoader pluginClassLoader = new DexClassLoader("/sdcard/plugin-debug.apk",getCacheDir().getAbsolutePath(),null,getClassLoader());
              //      Class<?> clazz=pluginClassLoader.loadClass("com.httvc.plugin.Test");
                //    Class<?> clazzz=Class.forName("com.httvc.plugin.MainActivy");

                    Class<?>  clazz = Class.forName("com.httvc.pluginlibrary.Test");
                    Method print=clazz.getMethod("print");
                    print.invoke(null);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }*/
                Intent intent =new Intent();
                intent.setComponent(new ComponentName("com.httvc.plugin","com.httvc.plugin.MainActivity"));
                startActivity(intent);
            }
        });

        ClassLoader classLoader = getClassLoader();
        while (classLoader!=null){
            Log.d("MainActivityclassLoader",classLoader+"");
            classLoader = classLoader.getParent();
        }

        ClassLoader c1=MainActivity.class.getClassLoader();
        Log.d("MainActivity cl is ....",c1+"");

        ClassLoader parent = c1.getParent();
        Log.d("MainActivity parent is",parent+"");

        ClassLoader boot_strap = parent.getParent();
        Log.d("MainActivity boot_strap",boot_strap+"");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            new Thread(){
                @Override
                public void run() {
                    //创建一个属于我们自己插件的ClassLoader，我们分析过只能使用DexClassLoader
                    String cachePath = MainActivity.this.getCacheDir().getAbsolutePath();
                    //String apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/chajian_demo.apk";
                    String apkPath ="/sdcard/plugin-debug.apk";
                    DexClassLoader mClassLoader = new DexClassLoader(apkPath, cachePath,cachePath, getClassLoader());
                    MyHookHelper.inject(mClassLoader);
                    try {
                        HookUtils.hookAMS();
                        HookUtils.hookHandler(getApplicationContext());
                    } catch (Exception e) {
                        Log.e("Main","加载异常了 = " + e.getMessage());
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"加载完成",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
