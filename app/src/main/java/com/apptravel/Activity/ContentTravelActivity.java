package com.apptravel.Activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apptravel.Entity.Travel;
import com.apptravel.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class ContentTravelActivity extends AppCompatActivity {

    private static final  String TAG = ContentTravelActivity.class.getSimpleName();
    public final static String EXTRA_POSITION = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_travel);

        View imageView = findViewById(R.id.iv_content_travel);
        View txtView = findViewById(R.id.tv_content_travel);
        Travel travel = (Travel) getIntent().getSerializableExtra(EXTRA_POSITION);
        if(travel != null) {
            assert (ImageView) imageView != null;
            Glide.with(this).load(travel.getImg()).into((ImageView) imageView);
            assert ((TextView)txtView) != null;
            ((TextView)txtView).setText(travel.getTen());
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setTransitionName(getString(R.string.transition_image));
                txtView.setTransitionName(getString(R.string.transition_text));
            }
        } else {
            Log.d(TAG, "bundle from Main activity is null");
        }
    }

}
