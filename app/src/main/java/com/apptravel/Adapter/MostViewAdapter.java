package com.apptravel.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptravel.Activity.ContentTravelActivity;
import com.apptravel.Entity.Travel;
import com.apptravel.R;

import java.util.ArrayList;

/**
 * Created by Le on 23-Dec-16.
 */

public class MostViewAdapter extends MyRecyclerViewAdapter {

    public MostViewAdapter(Context mContext, ArrayList<Travel> listTravel) {
        super(mContext, listTravel);
    }

    @Override
    public RecycleAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_card_mostview, parent, false);
        return new RecycleAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleAdapterHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    void addTransitionToContentTravelActivity(View view, int layoutPosition, RecycleAdapterHolder holder) {
        Intent it = new Intent(mContext, ContentTravelActivity.class);
        it.putExtra(ContentTravelActivity.EXTRA_POSITION, listTravel.get(layoutPosition));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            holder.img.setTransitionName(mContext.getString(R.string.transition_image));
            holder.Ten.setTransitionName(mContext.getString(R.string.transition_text));
            Pair<View, String> p1 = Pair.create((View)holder.img, holder.img.getTransitionName());
            Pair<View, String> p2 = Pair.create( (View) holder.Ten,  holder.Ten.getTransitionName());
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, p1, p2);
            ActivityCompat.startActivity((Activity) mContext, it, options.toBundle());
            //mContext.startActivity(it, options.toBundle());
        } else{
            mContext.startActivity(it);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
