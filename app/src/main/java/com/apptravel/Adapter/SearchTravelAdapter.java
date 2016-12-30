package com.apptravel.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptravel.Activity.ContentTravelActivity;
import com.apptravel.Entity.Travel;
import com.apptravel.R;

import java.util.ArrayList;

/**
 * Created by lldti on 27-Dec-16.
 */

public class SearchTravelAdapter extends MyRecyclerViewAdapter {

    private static final String TAG = SearchTravelAdapter.class.getSimpleName();

    public SearchTravelAdapter(Context mContext, ArrayList<Travel> listTravel) {
        super(mContext, listTravel);
    }

    @Override
    public RecycleAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_card_search_travel, parent, false);
        return new RecycleAdapterHolder(view);
    }

    @Override
    void addTransitionToContentTravelActivity(View view, int layoutPosition, RecycleAdapterHolder holder) {
        Log.d(TAG, "Search clicked");
        Intent it = new Intent(mContext, ContentTravelActivity.class);
        it.putExtra(ContentTravelActivity.EXTRA_POSITION, listTravel.get(layoutPosition));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            holder.img.setTransitionName(mContext.getString(R.string.transition_image));
            holder.Ten.setTransitionName(mContext.getString(R.string.transition_text));

            Pair<View, String> p1 = Pair.create((View) holder.img, holder.img.getTransitionName());
            Pair<View, String> p2 = Pair.create((View) holder.Ten, holder.Ten.getTransitionName());

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, p1, p2);
            ActivityCompat.startActivity((Activity) mContext, it, options.toBundle());
            //mContext.startActivity(it, options.toBundle());
        } else {
            mContext.startActivity(it);
        }
    }

    @Override
    void setSizeImageFromScreenSize(DisplayMetrics displayMetrics, RecycleAdapterHolder holder) {
        //
        int size = displayMetrics.widthPixels / 5;
        holder.img.getLayoutParams().height = size;
        holder.img.getLayoutParams().width = size;
    }

    @Override
    public void onBindViewHolder(RecycleAdapterHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        DisplayMetrics dm = getDisplayMetrics();
        if (dm != null) {
            holder.img.getLayoutParams().height = (dm.widthPixels / 4) * 3 / 4; // searchfragment view image 1/4 screen
            holder.img.getLayoutParams().width = dm.widthPixels / 4; // width = 1.3 height
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
