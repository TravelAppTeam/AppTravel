package com.apptravel.Databases;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.apptravel.Models.Travel;
import com.apptravel.R;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private ChildEventListener childEventListener = new MyChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Travel tv = dataSnapshot.getValue(Travel.class);
            listTravel.add(tv);
        }
    };

    public MyFireBaseDatabase(Activity activity, View v) {
        database = FirebaseDatabase.getInstance().getReference();
        listTravel = new ArrayList<>();
        this.activity = activity;
        view = v;
    }

    public void getAllData() {
        database.child(URL_DULICH_TAG).addChildEventListener(childEventListener);
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SliderLayout mSlider = (SliderLayout) view.findViewById(R.id.sliderfragment);
                Log.d(TAG, listTravel.size() + "");
                for (Travel x : listTravel) {
                    Log.d(TAG, x.getImg());
                    TextSliderView textSliderView = new TextSliderView(activity);

                    textSliderView.image(x.getImg())
                            .description(x.getTen())
                            .setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
                    mSlider.addSlider(textSliderView);
                }
                //TextSliderView gan text vao anh chuyen dong
                mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
                //mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mSlider.setDuration(4000);
                mSlider.setCustomAnimation(new DescriptionAnimation());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
