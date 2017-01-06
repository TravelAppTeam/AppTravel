package com.apptravel.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("csResume", "Loading activity");
        if (!isOnline()) {
            showDialog();
        }else{
            final Intent it = new Intent(this,IntroActivity.class);
            startActivity(it);
        }
    }

    public boolean isOnline() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        },1500);

        ConnectivityManager conManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title));
        builder.setMessage(getString(R.string.dialog_mess));

        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        builder.show();
    }

}
