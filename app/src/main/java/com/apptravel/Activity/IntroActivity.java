package com.apptravel.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.apptravel.R;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

public class IntroActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener {

    private static final String INTRO_PREF_ID = "FirstLaunch";
    int[] listImg = {R.drawable.intro_img_1, R.drawable.intro_img_2, R.drawable.intro_img_3};
    private SliderLayout mSlider;
    private ImageView imNext;
    private boolean isLastIndicator = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        boolean isFirstLaunch = loadSavedPreferances();
        Log.d("test", isFirstLaunch + "");
        if(isFirstLaunch)
            savePrefIntro();
        else
            changeActivity();

        mSlider = (SliderLayout) findViewById(R.id.slider);
        imNext = (ImageView) findViewById(R.id.im_next);
        addSlider();

    }

    private void changeActivity() {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
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

    private boolean loadSavedPreferances() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(INTRO_PREF_ID, true);
    }

    private void savePrefIntro() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("FirstLauch", false);
        editor.apply();
        editor.commit();
    }

    public void onSkip(View v) {
        changeActivity();
    }

    public void onNext(View v) {
        if (isLastIndicator)
            changeActivity();
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
