package com.apptravel.Databases;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.apptravel.Adapter.MostViewAdapter;
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
import java.util.List;

/**
 * Created by Le on 22-Dec-16.
 */

public class MyFireBaseDatabase {

    public final static String URL_DATABASE = "https://travelapp-e4b5c.firebaseio.com/";

    private final static String TAG = MyFireBaseDatabase.class.getSimpleName();
    private final static String URL_DULICH_TAG = "DuLich";
    private final static int MOSTVIEW_COLUMN = 3;

    private View view;
    private Activity activity;
    private DatabaseReference database;
    private ArrayList<Travel> listTravel;
    private SliderLayout mSlider;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mReAdapter;
    private ChildEventListener recommendedEventListener = new MyChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Travel travel = dataSnapshot.getValue(Travel.class);
            listTravel.add(travel);
            showRecommendedData(travel);
            showMostViewData(listTravel);
        }
    };

    private void showRecommendedData(Travel travel) {
        if (travel != null) {
            CustomTextSliderView sliderView = new CustomTextSliderView(activity);
            sliderView.image(travel.getImg())
                    .description(travel.getTen())
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            mSlider.addSlider(sliderView);
        } else {
            Log.d(TAG, "list travel is null in Recommeded data");
        }
    }

    private void showMostViewData(List<Travel> lstravel){
        if(lstravel != null){
            mReAdapter.notifyDataSetChanged();
        } else{
            Log.d(TAG, "list travel is null in Most View ");
        }
    }

    public MyFireBaseDatabase(Activity activity, View v) {
        this.activity = activity;
        this.view = v;
        database = FirebaseDatabase.getInstance().getReference();
        listTravel = new ArrayList<>();

        mSlider = (SliderLayout) view.findViewById(R.id.sliderfragment);
        settingSliderView();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_most_view_place);
        mReAdapter = new MostViewAdapter(view.getContext(), listTravel);
        settingRecyclerView();
    }

    private void settingRecyclerView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(activity.getApplicationContext(), MOSTVIEW_COLUMN));
        mRecyclerView.setAdapter(mReAdapter);
    }

    private void settingSliderView(){
        mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlider.setDuration(4000);
        mSlider.setCustomAnimation(new DescriptionAnimation());
    }
    public void getData() {
        database.child(URL_DULICH_TAG).addChildEventListener(recommendedEventListener);
    }
}
