package com.apptravel.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.apptravel.R;
import com.bumptech.glide.Glide;

public class LoadingActivity extends AppCompatActivity {

    public static LoadingActivity instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        instance = this;

        ImageView imgLoading = (ImageView)findViewById(R.id.imgLoading);

        Glide
                .with(this)
                .load(R.drawable.img_loadding)
                .centerCrop()
                .into(imgLoading);
        final Intent it = new Intent(this,IntroActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(it);
            }
        },1500);
    }
}
