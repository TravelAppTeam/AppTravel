package com.apptravel.Activity;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    private TextView txtInfo, txtTel, txtAddress;
    private DisplayMetrics dm;
    private ImageView imgBtnBack, imageView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_travel);



        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Travel travel = (Travel) getIntent().getSerializableExtra(EXTRA_POSITION);
        ((CollapsingToolbarLayout)findViewById(R.id.toolbar_layout)).setTitle(travel.getTen());
        ((AppBarLayout)findViewById(R.id.app_bar_travel)).getLayoutParams().height = dm.heightPixels/2;
        imageView = (ImageView)findViewById(R.id.iv_content_travel);
        txtInfo = (TextView)findViewById(R.id.txt_travel_content_info);
        txtAddress = (TextView)findViewById(R.id.txt_travel_content_address);
        txtTel = (TextView)findViewById(R.id.txt_travel_content_tel);

        addTransitionEvent(travel);

        toolbar = (Toolbar)findViewById(R.id.toolbar_travel);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    void addTransitionEvent(Travel travel) {
        if (travel != null) {

            setAllView(travel);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setTransitionName(getString(R.string.transition_image));
                // txtViewTen.setTransitionName(getString(R.string.transition_text));
                // cardView.setTransitionName(getString(R.string.transition_card));
            }
        } else {
            Log.d(TAG, "bundle from Main activity is null");
        }
    }

    void setAllView(Travel travel) {

        txtTel.setText(travel.getContact());
        txtAddress.setText(travel.getDiaChi());
        txtInfo.setText(travel.getMota());

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/arima_madurai_regular.ttf");
        txtTel.setTypeface(typeface);
        txtAddress.setTypeface(typeface);
        txtInfo.setTypeface(typeface);

        Glide.with(this).load(travel.getImg()).placeholder(R.drawable.bg_placeholder).into((ImageView) imageView);
        addAnimationText(txtInfo, ANIMATION_FADE_IN_ID);
        addAnimationText(txtTel, ANIMATION_FADE_IN_ID);
        addAnimationText(txtAddress, ANIMATION_FADE_IN_ID);
    }

    void addAnimationText(View text, int animID) {
        Animation a = AnimationUtils.loadAnimation(this, animID);
        text.clearAnimation();
        text.startAnimation(a);
    }
}
