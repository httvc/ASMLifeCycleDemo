package com.httvc.plugin;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainss);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   startActivity(new Intent(that,HomeActivitiy.class));
             //   startService(new Intent(that,OneService.class));

                //动态注册广播
                IntentFilter intentFilter=new IntentFilter();
                intentFilter.addAction("com.httvc.plugin.MainActivity");
                registerReceiver(new MyReceiver(),intentFilter);
            }
        });

        findViewById(R.id.sendBroad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction("com.httvc.plugin.MainActivity");
                sendBroadcast(intent);
            }
        });
    }
}
