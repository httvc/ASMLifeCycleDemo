package com.httvc.asmlifecycledemo;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void load(View view){
        loadPlugin();
    }

    private void loadPlugin() {
        File filesDir = this.getDir("plugin", Context.MODE_PRIVATE);
        String name="plugin.apk";
        String filePath=new File(filesDir,name).getAbsolutePath();
        File file=new File(filePath);
        if (file.exists()){
            file.delete();
        }
        FileInputStream input=null;//文件输入流(从本地文件读取数据)
        FileOutputStream output=null;//文件输出流(把数据写入本地文件)
        try {
            input=new FileInputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath(),name));
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
}
