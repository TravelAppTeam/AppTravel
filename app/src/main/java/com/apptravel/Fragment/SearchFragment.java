package com.apptravel.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.apptravel.Adapter.SearchTravelAdapter;
import com.apptravel.Databases.MyFireBaseDatabase;
import com.apptravel.Databases.QueryDatabase;
import com.apptravel.Entity.Travel;
import com.apptravel.Events.AsyncResponse;
import com.apptravel.Events.MySearchTextChange;
import com.apptravel.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements TextWatcher, AsyncResponse {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView mSearchView;
    private EditText edtSearchView;
    private SearchTravelAdapter mReSearchTravelAdapter;
    private CircularProgressView progressView;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mSearchView = (RecyclerView) view.findViewById(R.id.rv_search_travel);
        edtSearchView = (EditText) getActivity().findViewById(R.id.edt_search_view);
//        final MyFireBaseDatabase myFireBaseDatabase = new MyFireBaseDatabase(getActivity(), view, mSearchView);
//        myFireBaseDatabase.getSearchDataByName(true);

        mReSearchTravelAdapter = new SearchTravelAdapter(getContext(), new ArrayList<Travel>());
        mSearchView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSearchView.setAdapter(mReSearchTravelAdapter);

        /*loading anim*/
        progressView = (CircularProgressView) view.findViewById(R.id.cpv_waiting);
        /*When user not yet enter contain to search*/
        new QueryDatabase(progressView).searchAllData("", this);
        edtSearchView.addTextChangedListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //                myFireBaseDatabase.getSearchDataByName(charSequence.toString());

        new QueryDatabase(progressView).searchAllData(s.toString(),this);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onAsyncLoadDone(ArrayList<Travel> listTravel) {
        mReSearchTravelAdapter.setListTravel(listTravel);
        mReSearchTravelAdapter.notifyDataSetChanged();
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
