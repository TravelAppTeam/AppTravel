package com.apptravel.Activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.apptravel.Entity.Travel;
import com.apptravel.R;
import com.bumptech.glide.Glide;

public class ContentTravelActivity extends AppCompatActivity {

    public final static String EXTRA_POSITION = "position";
    public final static int ANIMATION_FADE_IN_ID = R.anim.fade_in;

    private static final String TAG = ContentTravelActivity.class.getSimpleName();

    private View imageView;
    private View txtViewTen;
    private TextView txtViewMota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_travel);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int devHeight = dm.heightPixels;

        imageView = findViewById(R.id.iv_content_travel);
        txtViewTen = findViewById(R.id.tv_content_travel_ten);
        txtViewMota = (TextView) findViewById(R.id.tv_content_travel_mota);
        imageView.getLayoutParams().height = devHeight/3;

        Travel travel = (Travel) getIntent().getSerializableExtra(EXTRA_POSITION);
        addTransitionEvent(travel);

    }

    void addTransitionEvent(Travel travel) {
        if (travel != null) {

            setAllView(travel);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setTransitionName(getString(R.string.transition_image));
                txtViewTen.setTransitionName(getString(R.string.transition_text));
            }
        } else {
            Log.d(TAG, "bundle from Main activity is null");
        }
    }

    void setAllView(Travel travel) {
        ((TextView) txtViewTen).setText(travel.getTen());

        Glide.with(this).load(travel.getImg()).into((ImageView) imageView);

        txtViewMota.setText(travel.getMota());
        addAnimationText(txtViewMota, ANIMATION_FADE_IN_ID);
    }

    void addAnimationText(View text, int animID){
        Animation a = AnimationUtils.loadAnimation(this, animID);
        text.clearAnimation();
        text.startAnimation(a);
    }
}
