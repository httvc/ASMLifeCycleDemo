package com.httvc.pluginlibrary;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Main","test this = " + this);
        Log.e("Main","getResource = " + getResources());
        Log.e("Main","getApplication = " + getApplication());
        Log.e("Main","getApplication class = " + getApplication().getClass().getName());

        setContentView(R.layout.activity_main2);
        findViewById(R.id.ss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this,MainActivity.class));
            }
        });

    }

    /*@Override
    public Resources getResources() {
        if (getApplication()!=null && getApplication().getResources()!=null){
            return getApplication().getResources();
        }
        return super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        if (getApplication()!=null && getApplication().getAssets()!=null){
            return getApplication().getAssets();
        }
        return super.getAssets();
    }

    @Override
    public Resources.Theme getTheme() {
        if (getApplication()!=null &&getApplication().getTheme()!=null){
            return getApplication().getTheme();
        }
        return super.getTheme();
    }*/
}
