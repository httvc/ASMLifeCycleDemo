package com.httvc.asmlifecycledemo;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.httvc.pluginlibrary.InterfaceActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ProxyActivity extends AppCompatActivity {
    //需要加载插件的全类名
    private String className;
    private InterfaceActivity interfaceActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("leo","onCreate:我是代理Activity");
        className=getIntent().getStringExtra("className");
        try {
            Class<?> aClass = getClassLoader().loadClass(className);
            Constructor<?> constructor = aClass.getConstructor(new Class[]{});
            Object in = constructor.newInstance(new Object[]{});
            interfaceActivity = (InterfaceActivity)in;
            interfaceActivity.attach(this);

            //如果需要参数，通过bundle传数据
            Bundle bundle=new Bundle();
            interfaceActivity.onCreate(bundle);
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
        //  setContentView(R.layout.activity_proxy);
    }

    @Override
    public void startActivity(Intent intent) {
        String className=intent.getStringExtra("className");
        Intent intent1=new Intent(this,ProxyActivity.class);
        intent1.putExtra("className",className);
        super.startActivity(intent1);
    }

    //重写加载类
    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    //重写加载资源
    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    @Override
    protected void onStart() {
        super.onStart();
        interfaceActivity.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        interfaceActivity.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        interfaceActivity.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        interfaceActivity.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        interfaceActivity.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        interfaceActivity.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        interfaceActivity.onSaveInstanceState(outState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return interfaceActivity.onTouchEvent(event);
    }
}