package com.apptravel.Databases;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.apptravel.Adapter.MostViewAdapter;
import com.apptravel.Adapter.SearchTravelAdapter;
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
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Le on 22-Dec-16.
 */

public class MyFireBaseDatabase {

    public final static String URL_DATABASE = "https://travelapp-e4b5c.firebaseio.com/";

    private final static String TAG = MyFireBaseDatabase.class.getSimpleName();
    private final static String URL_DULICH_TAG = "DuLich";
    private final static String URL_DULICH_RECOMMENDED_TAG = "DuLichDeXuat";
    private final static String URL_DULICH_NAME = "Ten";
    private final static int MOSTVIEW_COLUMN = 3;
    private final static int SEARCH_TRAVEL_COLUMN = 1;

    private View view;
    private Activity activity;
    private DatabaseReference database;
    private ArrayList<Travel> listRecommendedTravel;
    private ArrayList<Travel> listMostViewTravel;
    private ArrayList<Travel> listSearchTravel;
    private SliderLayout mSlider;

    private RecyclerView mReMostView;
    private RecyclerView.Adapter mReMostViewAdapter;
    private RecyclerView mReSearchTravel;
    private RecyclerView.Adapter mReSearchTravelAdapter;

    private ChildEventListener recommendedEvent = new MyChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Travel travel = dataSnapshot.getValue(Travel.class);
            listRecommendedTravel.add(travel);
            showRecommendedData(travel);
        }
    };
    private ChildEventListener mostViewEvent = new MyChildEventListener(){
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Travel travel = dataSnapshot.getValue(Travel.class);
            listMostViewTravel.add(travel);
            showMostViewData(listMostViewTravel);
        }
    } ;

    private ChildEventListener searchEvent = new MyChildEventListener(){
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Travel travel = dataSnapshot.getValue(Travel.class);
            listSearchTravel.add(travel);
            showSearchTravelData(listSearchTravel);
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
            mReMostViewAdapter.notifyDataSetChanged();
        } else{
            Log.d(TAG, "list travel is null in Most View ");
        }
    }

    private void showSearchTravelData(List<Travel> lstravel){
        if(lstravel != null){
            mReSearchTravelAdapter.notifyDataSetChanged();
        } else{
            Log.d(TAG, "list travel is null in Search Travel");
        }
    }

    public MyFireBaseDatabase(Activity activity, View v) {
        this.activity = activity;
        this.view = v;
        database = FirebaseDatabase.getInstance().getReference();
        listRecommendedTravel = new ArrayList<>();
        listMostViewTravel = new ArrayList<>();
        listSearchTravel = new ArrayList<>();

        mSlider = (SliderLayout) view.findViewById(R.id.sliderfragment);
        settingSliderView();

        mReMostView = (RecyclerView) view.findViewById(R.id.rv_most_view_place);
        mReMostViewAdapter = new MostViewAdapter(view.getContext(), listMostViewTravel);
        settingRecyclerView(mReMostView, MOSTVIEW_COLUMN, mReMostViewAdapter);

//        mReSearchTravel = (RecyclerView) view.findViewById(R.id.rv_most_view_place);
//        mReSearchTravelAdapter = new SearchTravelAdapter(view.getContext(), listSearchTravel);
//        settingRecyclerView(mReSearchTravel, SEARCH_TRAVEL_COLUMN,mReSearchTravelAdapter );
    }

    private void settingRecyclerView(RecyclerView rv, int numberColumn, RecyclerView.Adapter adapter) {
        rv.setLayoutManager(new GridLayoutManager(activity.getApplicationContext(), numberColumn));
        rv.setAdapter(adapter);
    }

    private void settingSliderView(){
        mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlider.setDuration(4000);
        mSlider.setCustomAnimation(new DescriptionAnimation());
    }

    public void getDataRecommended() {
        database.child(URL_DULICH_RECOMMENDED_TAG).addChildEventListener(recommendedEvent);
    }
    public void getDataMostView() {
        database.child(URL_DULICH_TAG).addChildEventListener(mostViewEvent);
    }

    public void getSearchDataByName(String queryContent){
        listSearchTravel = new ArrayList<>();
        Query myQuery = database.child(URL_DULICH_TAG).child(queryContent).orderByChild(URL_DULICH_NAME);
        myQuery.addChildEventListener(searchEvent);
    }
}
