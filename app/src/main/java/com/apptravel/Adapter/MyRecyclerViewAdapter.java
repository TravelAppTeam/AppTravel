package com.apptravel.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.apptravel.Entity.Travel;
import com.apptravel.Events.MyRecyclerViewItemListener;
import com.apptravel.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by lldti on 27-Dec-16.
 */

abstract class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecycleAdapterHolder> {
    private static final String TAG = MyRecyclerViewAdapter.class.getSimpleName();
    private final DisplayMetrics dm;
    Context mContext;
    ArrayList<Travel> listTravel;
    protected static final int LITMIT_STRING_LENGTH = 40;

    MyRecyclerViewAdapter(Context mContext, ArrayList<Travel> listTravel) {
        this.mContext = mContext;
        this.listTravel = listTravel;

        dm = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    @Override
    public abstract RecycleAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final RecycleAdapterHolder holder, int position) {
        Travel travel = listTravel.get(position);
        if (travel != null) {
            holder.Ten.setText(travel.getTen());
            Glide.with(mContext).load(travel.getImg()).placeholder(R.drawable.bg_placeholder).into(holder.img);
            String s = travel.getMota();
            if(s != null) {
                if(s.length() > LITMIT_STRING_LENGTH) {
                    s = s.substring(0, LITMIT_STRING_LENGTH) + "...";
                }
                holder.Mota.setText(s);
            }
        } else {
            Log.d(TAG, "position card most view null");
        }

        setSizeImageFromScreenSize(dm, holder);
        holder.setItemsClickListener(new MyRecyclerViewItemListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Log.d(TAG, "onBindView");
                addTransitionToContentTravelActivity(v, pos, holder);
            }
        });
    }

    public Context getmContext() {
        return mContext;
    }

    // intent to new activity with transition or not
    abstract void addTransitionToContentTravelActivity(View view, int layoutPosition, RecycleAdapterHolder holder);
    abstract void setSizeImageFromScreenSize(DisplayMetrics displayMetrics, RecycleAdapterHolder holder);
    @Override
    public int getItemCount() {
        return listTravel == null ? 0 : listTravel.size();
    }

    public DisplayMetrics getDisplayMetrics() {
        return dm;
    }
}
