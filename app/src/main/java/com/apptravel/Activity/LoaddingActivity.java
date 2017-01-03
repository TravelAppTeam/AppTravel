package com.apptravel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.apptravel.R;
import com.bumptech.glide.Glide;

public class LoaddingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadding);
        ImageView imgLoadding= (ImageView)findViewById(R.id.img_loadding);

        Glide
                .with(this)
                .load(R.drawable.img_loadding)
                .centerCrop()
                .into(imgLoadding);
        Thread loaddingThread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    Intent mainConntent = new Intent(LoaddingActivity.this, MainActivity.class);
                    startActivity(mainConntent);
                    finish();
                }
            }
        }   ;
        loaddingThread.start();
    }

}
