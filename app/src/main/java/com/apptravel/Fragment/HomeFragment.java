package com.apptravel.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptravel.Activity.ContentTravelActivity;
import com.apptravel.Adapter.MostViewAdapter;
import com.apptravel.Customs.CustomTextSliderView;
import com.apptravel.Databases.MyFireBaseDatabase;
import com.apptravel.Databases.QueryDatabase;
import com.apptravel.Entity.Travel;
import com.apptravel.Events.AsyncResponse;
import com.apptravel.R;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements AsyncResponse, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = HomeFragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SliderLayout mSlider;
    private RecyclerView mReMostView;
    private DisplayMetrics displayMetrics;
    private CustomTextSliderView sliderView;
    private QueryDatabase queryDatabase;
    private MostViewAdapter ReMostViewAdapter;
    private SwipeRefreshLayout refreshView;
    private ArrayList<Travel> currentListTravel;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mSlider = (SliderLayout) view.findViewById(R.id.sliderfragment);
        settingSliderView();

        refreshView = (SwipeRefreshLayout) view.findViewById(R.id.refreshpage_swipe);
        refreshView.setOnRefreshListener(this);
        refreshView.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        CircularProgressView progressView = (CircularProgressView) view.findViewById(R.id.cpv_waiting);
        progressView.startAnimation();
        queryDatabase = new QueryDatabase(progressView);

        mReMostView = (RecyclerView) view.findViewById(R.id.rv_most_view_place);
        ReMostViewAdapter = new MostViewAdapter(getContext(), new ArrayList<Travel>());
        mReMostView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), MyFireBaseDatabase.MOSTVIEW_COLUMN));
        mReMostView.setAdapter(ReMostViewAdapter);

        queryDatabase.loadAllData(this);

//        MyFireBaseDatabase myFireBaseDatabase = new MyFireBaseDatabase(getActivity(), view, mSlider, mReMostView);
//        myFireBaseDatabase.getDataMostView();
//        myFireBaseDatabase.getDataRecommended();
    }

    private void settingRecyclerView(RecyclerView rv, int numberColumn, ArrayList<Travel> listMostViewTravel) {
        if (rv != null) {
            Log.i(TAG, "In setting recycler view");

        } else {
            Log.e(TAG, "Recycler Most View is null");
        }
    }


    private void settingSliderView() {
        mSlider.getLayoutParams().height = displayMetrics.heightPixels / 3;
        mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlider.setDuration(4000);
        mSlider.setCustomAnimation(new DescriptionAnimation());
    }

    private void addSliderView(final ArrayList<Travel> listTravel) {

        if (listTravel != null) {
            for (Travel travel : listTravel) {
                sliderView = new CustomTextSliderView(getActivity());
                sliderView.image(travel.getImg())
                        .description(travel.getTen())
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        Intent it = new Intent(getActivity(), ContentTravelActivity.class);
                        it.putExtra(ContentTravelActivity.EXTRA_POSITION, listTravel.get(mSlider.getCurrentPosition()));
                        startActivity(it);
                    }
                });
                if (mSlider != null)
                    mSlider.addSlider(sliderView);
            }
        } else {
            Log.d(TAG, "list travel is null in Recommeded data");
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAsyncLoadDone(ArrayList<Travel> listTravel) {
        this.currentListTravel = listTravel;
        ArrayList scList = new ArrayList();
        if (listTravel != null && listTravel.size() > 0) {
            for (int i = 0; i < 3; ++i) {
                scList.add(listTravel.get(i));
            }
            addSliderView(scList);
            ReMostViewAdapter.setListTravel(listTravel);
            ReMostViewAdapter.notifyDataSetChanged();
        } else {
            Log.d(TAG, "list travel is null");
        }
    }

    @Override
    public void onRefresh() {
        if ((currentListTravel.size() < 1 && currentListTravel != null)) {
            refreshView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshView.setRefreshing(false);
                    queryDatabase.loadAllData(HomeFragment.this);
                }
            }, 1500);
        } else {
            refreshView.setRefreshing(false);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
