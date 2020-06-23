package com.httvc.asmlifecycledemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class Main2Activity extends AppCompatActivity {
    static final String ACTION="com.httvc.asmlifecycledemo.Receivel.PLUGIN_ACTION";
    BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "我是宿主，收到你的消息，握手完成", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        registerReceiver(mReceiver,new IntentFilter(ACTION));

        mHandler=new MyHandler(this);
        Message msg=Message.obtain();
        msg.what=1;
        mHandler.sendMessage(msg);
    }

    public void load(View view){
        loadPlugin();
    }

    private void loadPlugin() {
        File filesDir = this.getDir("plugin", Context.MODE_PRIVATE);
        String name="plugin-debug.apk";
        String filePath=new File(filesDir,name).getAbsolutePath();
        File file=new File(filePath);
        if (file.exists()){
            file.delete();
        }
        FileInputStream input=null;//文件输入流(从本地文件读取数据)
        FileOutputStream output=null;//文件输出流(把数据写入本地文件)
        try {
            //new File(Environment.getExternalStorageDirectory().getAbsolutePath(),name)
            File file1 = new File("/sdcard", name);
            input=new FileInputStream(file1);
            output=new FileOutputStream(filePath);//文件输出流
            int len=0;
            byte[] buffer=new byte[1024];
            while ((len=input.read(buffer))!=-1){
                output.write(buffer,0,len);
            }
            File f=new File(filePath);
            if(f.exists()){
                Toast.makeText(this, "dex overwrite", Toast.LENGTH_SHORT).show();
            }
            PluginManager.getInstance().loadPath(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void click(View view){
        Intent intent=new Intent(this,ProxyActivity.class);
        intent.putExtra("className",PluginManager.getInstance().getPackInfo().activities[0].name);
        startActivity(intent);
    }

    public void sendBroadCas(View view){
        Intent intent=new Intent();
        intent.setAction("com.httvc.plugin.StaticReceiver");
        sendBroadcast(intent);
    }

    /*private static Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };*/
    //改造
    private Handler mHandler;
    private static class MyHandler extends Handler{
        private WeakReference<Main2Activity> weakReference;
        public MyHandler(Main2Activity activity){
            weakReference=new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Main2Activity activity = weakReference.get();
            if (activity!=null){
                if (msg.what==1){
                    //逻辑处理
                }
            }
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mHandler.removeCallbacksAndMessages(null);
    }

    private static class MyThread extends Thread{
        private WeakReference<Main2Activity> weakReference;
        MyThread(Main2Activity activity){
            weakReference=new WeakReference<>(activity);
        }

        @Override
        public void run() {
            Main2Activity activity = weakReference.get();
            if (activity!=null){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            super.run();
        }
    }
}
