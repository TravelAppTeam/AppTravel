package com.apptravel.Databases;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.apptravel.Activity.ContentTravelActivity;
import com.apptravel.Adapter.MostViewAdapter;
import com.apptravel.Adapter.SearchTravelAdapter;
import com.apptravel.Customs.CustomTextSliderView;
import com.apptravel.Entity.Travel;
import com.apptravel.Events.MyChildEventListener;
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

    public final static int MOSTVIEW_COLUMN = 3;
    public final static int SEARCH_TRAVEL_COLUMN = 1;
    public final static String URL_DATABASE = "https://travelapp-e4b5c.firebaseio.com/";

    private final static String TAG = MyFireBaseDatabase.class.getSimpleName();
    private final static String URL_DULICH_TAG = "DuLich";
    private final static String URL_DULICH_RECOMMENDED_TAG = "DuLichDeXuat";
    private final static String URL_DULICH_NAME = "Ten";
    private SearchTravelAdapter mReSearchTravelAdapter;
    private MostViewAdapter mReMostViewAdapter;


    private View view;
    private Activity activity;
    private DatabaseReference database;
    private ArrayList<Travel> listRecommendedTravel;
    private ArrayList<Travel> listMostViewTravel;
    private ArrayList<Travel> listSearchTravel;
    private SliderLayout mSlider;

    private ChildEventListener recommendedEvent = new MyChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Travel travel = dataSnapshot.getValue(Travel.class);
            listRecommendedTravel.add(travel);
            showRecommendedData(travel);
        }
    };

    private ChildEventListener mostViewEvent = new MyChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Travel travel = dataSnapshot.getValue(Travel.class);
            listMostViewTravel.add(travel);
            showMostViewData(listMostViewTravel);
        }
    };

    private ChildEventListener searchEvent = new MyChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Travel travel = dataSnapshot.getValue(Travel.class);
            listSearchTravel.add(travel);
            showSearchTravelData(listSearchTravel);
        }
    };
    private CustomTextSliderView sliderView;

    private void showRecommendedData(Travel travel) {
        if (travel != null) {
            sliderView = new CustomTextSliderView(activity);
            sliderView.image(travel.getImg())
                    .description(travel.getTen())
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Intent it = new Intent(activity, ContentTravelActivity.class);
                    it.putExtra(ContentTravelActivity.EXTRA_POSITION, listRecommendedTravel.get(mSlider.getCurrentPosition()));
                    activity.startActivity(it);
                }
            });
            mSlider.addSlider(sliderView);
        } else {
            Log.d(TAG, "list travel is null in Recommeded data");
        }
    }

    private void showMostViewData(List<Travel> lstravel) {
        if (lstravel != null) {
            mReMostViewAdapter.notifyDataSetChanged();
        } else {
            Log.d(TAG, "list travel is null in Most View ");
        }
    }

    private void showSearchTravelData(List<Travel> lstravel) {
        if (lstravel != null) {
            mReSearchTravelAdapter.notifyDataSetChanged();
        } else {
            Log.d(TAG, "list travel is null in Search Travel");
        }
    }

    private MyFireBaseDatabase(Activity activity) {
        this.activity = activity;
        database = FirebaseDatabase.getInstance().getReference();
        listRecommendedTravel = new ArrayList<>();
        listMostViewTravel = new ArrayList<>();
        listSearchTravel = new ArrayList<>();
    }

    public MyFireBaseDatabase(final Activity activity, View v, SliderLayout sliderLayout, RecyclerView recyclerView) {
        // using for Home fragment
        this(activity);
        this.view = v;
        this.mSlider = sliderLayout;
        mReMostViewAdapter = new MostViewAdapter(v.getContext(), listMostViewTravel);
        settingRecyclerView(recyclerView, MOSTVIEW_COLUMN, mReMostViewAdapter);
    }

    public MyFireBaseDatabase(Activity activity, View v, RecyclerView recyclerView) {
        // using for Search Fragment
        this(activity);
        this.view = v;
        mReSearchTravelAdapter = new SearchTravelAdapter(view.getContext(), listSearchTravel);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        recyclerView.setAdapter(mReSearchTravelAdapter);
        //settingRecyclerView(recyclerView, SEARCH_TRAVEL_COLUMN, mReSearchTravelAdapter);
    }

    private void settingRecyclerView(RecyclerView rv, int numberColumn, RecyclerView.Adapter adapter) {
        rv.setLayoutManager(new GridLayoutManager(activity.getApplicationContext(), numberColumn));
        rv.setAdapter(adapter);
    }

    public void getDataRecommended() {
        database.child(URL_DULICH_RECOMMENDED_TAG).addChildEventListener(recommendedEvent);
    }

    public void getDataMostView() {
        database.child(URL_DULICH_TAG).addChildEventListener(mostViewEvent);
    }

    public void getSearchDataByName(String queryContent) {
        listSearchTravel = new ArrayList<>();
        Log.d("Query content is: {}", queryContent);
        Query myQuery = database.orderByChild(URL_DULICH_NAME).equalTo(queryContent);
        myQuery.addChildEventListener(searchEvent);
        Log.d("list string length {}", listSearchTravel.size() + "");
    }

    public void getSearchDataByName(boolean bool) {
        if (bool)
            database.child(URL_DULICH_TAG).addChildEventListener(searchEvent);
    }
}
