package com.apptravel.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apptravel.Activity.ContentTravelActivity;
import com.apptravel.Entity.Travel;
import com.apptravel.Events.MyRecyclerViewItemListener;
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
    private static final String TRANSITION_NAME = "transition";

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
    public void onBindViewHolder(final MostViewAdapterHolder holder, int position) {
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
                showContentTravelActivity(v, pos);
            }
        });
    }
    private void showContentTravelActivity(View view, int layoutPosition) {
        Intent it = new Intent(mContext, ContentTravelActivity.class);
        it.putExtra(ContentTravelActivity.EXTRA_POSITION, listTravel.get(layoutPosition));

        /*Activity activity = (Activity) mContext;
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, TRANSITION_NAME);
        ActivityCompat.startActivity(activity, it, optionsCompat.toBundle());*/

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            View img = view.findViewById(R.id.iv_most_view);
            View txt = view.findViewById(R.id.tv_most_view);
            img.setTransitionName(mContext.getString(R.string.transition_image));
            txt.setTransitionName(mContext.getString(R.string.transition_text));
            Pair<View, String> p1 = Pair.create(img, img.getTransitionName());
            Pair<View, String> p2 = Pair.create(txt, txt.getTransitionName());
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, p1, p2);
            ActivityCompat.startActivity((Activity) mContext, it, options.toBundle());
            //mContext.startActivity(it, options.toBundle());
        } else{
            mContext.startActivity(it);
        }

    }

    @Override
    public int getItemCount() {
        return listTravel == null ? 0 : listTravel.size();
    }

    public class MostViewAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img;
        private TextView Ten;
        private MyRecyclerViewItemListener itemsClick;

        public MostViewAdapterHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv_most_view);
            Ten = (TextView) itemView.findViewById(R.id.tv_most_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemsClick.onItemClick(view, getLayoutPosition());
        }

        void setItemsClickListener(MyRecyclerViewItemListener itemsClick){
            this.itemsClick = itemsClick;
        }
    }


}
