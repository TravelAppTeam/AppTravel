package com.apptravel.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apptravel.R;

public class LoadingActivity extends AppCompatActivity {

    public static LoadingActivity instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        instance = this;
        final Intent it = new Intent(this,IntroActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(it);
            }
        },2000);
    }
}
