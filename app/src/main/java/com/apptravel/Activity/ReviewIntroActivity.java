package com.apptravel.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apptravel.R;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

/**
 * Created by vungho on 04/01/2017.
 */

public class ReviewIntroActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener{

    int[] listImg = {R.drawable.intro_img_1, R.drawable.intro_img_2, R.drawable.intro_img_3};
    private SliderLayout mSlider;
    private ImageView imNext;
    private TextView txtSkip;
    private boolean isLastIndicator = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mSlider = (SliderLayout) findViewById(R.id.slider);
        imNext = (ImageView) findViewById(R.id.im_next);
        txtSkip = (TextView) findViewById(R.id.tv_skip);
        addSlider();

        imNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext();
            }
        });

        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSkip();
            }
        });
    }

    private void addSlider() {
        for (int x : listImg) {
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView.image(x)
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            mSlider.addSlider(textSliderView);
        }
        //TextSliderView gan text vao anh chuyen dong
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        //mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
        mSlider.setCurrentPosition(0);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.addOnPageChangeListener(this);
    }

    private void onSkip() {
       super.onBackPressed();
    }

    private void onNext() {
        if (isLastIndicator)
            super.onBackPressed();
        else
            mSlider.moveNextPosition();
    }

    @Override
    protected void onStop() {
        mSlider.stopAutoCycle();
        super.onStop();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d("Position", position % 3 + " " + listImg.length);
        if (position % 3 == listImg.length - 1) {
            isLastIndicator = true;
            imNext.setImageResource(R.drawable.ic_done_white_36dp);
        } else {
            isLastIndicator = false;
            imNext.setImageResource(R.drawable.ic_play_arrow_white_36dp);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
