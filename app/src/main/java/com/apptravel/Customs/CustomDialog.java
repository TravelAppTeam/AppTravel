package com.apptravel.Customs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.apptravel.R;

/**
 * Created by vungho on 28/12/2016.
 */

public class CustomDialog {
    public void showDialog(final Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        //Mapping and event
        Button btnOk = (Button)dialog.findViewById(R.id.btn_ok_dialog);
        Button btnNo = (Button)dialog.findViewById(R.id.btn_no_dialog);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                activity.startActivity(intent);
                dialog.cancel();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
