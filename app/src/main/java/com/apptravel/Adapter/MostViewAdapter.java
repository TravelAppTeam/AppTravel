package com.apptravel.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apptravel.Entity.Travel;
import com.apptravel.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Le on 23-Dec-16.
 */

public class MostViewAdapter extends RecyclerView.Adapter<MostViewAdapter.MostViewAdapterHolder> {
    private static final String TAG = MostViewAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Travel> listTravel;

    public MostViewAdapter(Context mContext, ArrayList<Travel> listTravel) {
        this.mContext = mContext;
        this.listTravel = listTravel;
    }

    @Override
    public MostViewAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_card_mostview, parent, false);
        return new MostViewAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(MostViewAdapterHolder holder, int position) {
        Travel travel = listTravel.get(position);
        if (travel != null) {
            holder.Ten.setText(travel.getTen());
            Glide.with(mContext).load(travel.getImg()).into(holder.img);
        } else {
            Log.d(TAG, "position card most view null");
        }
    }

    @Override
    public int getItemCount() {
        return listTravel == null ? 0 : listTravel.size();
    }

    public class MostViewAdapterHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView Ten;

        public MostViewAdapterHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv_most_view);
            Ten = (TextView) itemView.findViewById(R.id.tv_most_view);
        }
    }
}
