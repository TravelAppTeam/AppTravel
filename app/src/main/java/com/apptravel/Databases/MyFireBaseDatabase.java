package com.apptravel.Databases;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.apptravel.Customs.CustomTextSliderView;
import com.apptravel.Entity.Travel;
import com.apptravel.Events.MyChildEventListener;
import com.apptravel.R;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Le on 22-Dec-16.
 */

public class MyFireBaseDatabase {

    public final static String URL_DATABASE = "https://travelapp-e4b5c.firebaseio.com/";
    public final static String URL_DULICH_TAG = "DuLich";

    private final static String TAG = MyFireBaseDatabase.class.getSimpleName();
    private View view;
    private Activity activity;
    private DatabaseReference database;
    private ArrayList<Travel> listTravel;
    SliderLayout mSlider;
    private ChildEventListener recommendedEventListener = new MyChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Travel travel = dataSnapshot.getValue(Travel.class);
            listTravel.add(travel);
            makeSliderView(travel);
        }
    };

    private void makeSliderView(Travel travel) {
        if (travel != null) {
            CustomTextSliderView sliderView = new CustomTextSliderView(activity);
            sliderView.image(travel.getImg())
                    .description(travel.getTen())
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
            mSlider.addSlider(sliderView);
            mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
            mSlider.setDuration(4000);
            mSlider.setCustomAnimation(new DescriptionAnimation());
        } else {
            Log.d(TAG, "list travel is null");
        }
    }

    public MyFireBaseDatabase(Activity activity, View v) {
        this.activity = activity;
        this.view = v;
        database = FirebaseDatabase.getInstance().getReference();
        listTravel = new ArrayList<>();
        mSlider = (SliderLayout) view.findViewById(R.id.sliderfragment);
    }

    public void getAllData() {
        database.child(URL_DULICH_TAG).addChildEventListener(recommendedEventListener);
    }
}
