package com.apptravel.Customs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apptravel.R;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

/**
 * Created by Le on 22-Dec-16.
 */

public class CustomTextSliderView extends BaseSliderView {
    public CustomTextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.custom_text_slider,null);
        ImageView target = (ImageView)v.findViewById(R.id.iv_image_slider);
        TextView description = (TextView)v.findViewById(R.id.tv_text_slider);
        description.setText(getDescription());
        bindEventAndShow(v, target);
        return v;
    }
}
