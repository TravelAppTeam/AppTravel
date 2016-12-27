package com.apptravel.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.apptravel.Entity.Travel;
import com.apptravel.Events.MyRecyclerViewItemListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by lldti on 27-Dec-16.
 */

abstract class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecycleAdapterHolder> {
    private static final String TAG = MostViewAdapter.class.getSimpleName();
    Context mContext;
    ArrayList<Travel> listTravel;

    MyRecyclerViewAdapter(Context mContext, ArrayList<Travel> listTravel) {
        this.mContext = mContext;
        this.listTravel = listTravel;
    }

    @Override
    public abstract RecycleAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final RecycleAdapterHolder holder, int position) {
        Travel travel = listTravel.get(position);
        if (travel != null) {
            holder.Ten.setText(travel.getTen());
            Glide.with(mContext).load(travel.getImg()).into(holder.img);
        } else {
            Log.d(TAG, "position card most view null");
        }
        holder.setItemsClickListener(new MyRecyclerViewItemListener() {
            @Override
            public void onItemClick(View v, int pos) {
                addTransitionToContentTravelActivity(v, pos, holder);
            }
        });
    }

    // intent to new activity with transition or not
    abstract void addTransitionToContentTravelActivity(View view, int layoutPosition, RecycleAdapterHolder holder);

    @Override
    public int getItemCount() {
        return listTravel == null ? 0 : listTravel.size();
    }
}
