package com.httvc.asmlifecycledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        add(4,2);
    }
    private void add(int i,int j){
        int k=i+j;
        System.out.println(k);
    }
}
