package com.httvc.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.httvc.pluginlibrary.InterfaceActivity;

public class BaseActivity extends AppCompatActivity implements InterfaceActivity {
    protected Activity that;

    @Override
    public void attach(Activity proxyActivity) {
        this.that = proxyActivity;
    }

    @Override
    public void setContentView(View view) {
        if (that != null) {
            that.setContentView(view);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (that != null) {
            that.setContentView(layoutResID);
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (that != null) {
            return that.findViewById(id);
        } else {
            return super.findViewById(id);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        Intent m=new Intent();
        m.putExtra("className", intent.getComponent().getClassName());
        that.startActivity(m);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
