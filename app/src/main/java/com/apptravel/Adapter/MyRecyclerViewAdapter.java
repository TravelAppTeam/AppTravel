package com.apptravel.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.apptravel.Databases.DownLoadImageWeb;
import com.apptravel.Entity.Travel;
import com.apptravel.Events.ImageLoadedEvent;
import com.apptravel.Events.MyRecyclerViewItemListener;
import com.apptravel.Events.RoundedCornerTransformation;
import com.apptravel.R;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
    private static final int ROUND_CORNER_SIZE = 15;

    public void setListTravel(ArrayList<Travel> listTravel) {
        this.listTravel = listTravel;
    }

    MyRecyclerViewAdapter(Context mContext, ArrayList<Travel> listTravel) {
        this.mContext = mContext;
        this.listTravel = listTravel;

        dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    @Override
    public abstract RecycleAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final RecycleAdapterHolder holder, int position) {
        final Travel travel = listTravel.get(position);
        if (travel != null) {
            final RoundedCornerTransformation transformation = new RoundedCornerTransformation(mContext, ROUND_CORNER_SIZE, 0);
            holder.Ten.setText(travel.getTen());
//            if (travel.getLowimg() != null)
//                Glide.with(mContext).load(travel.getLowimg()).centerCrop().placeholder(R.drawable.bg_placeholder)
//                        .bitmapTransform(transformation).into(holder.img);
//            new DownLoadImageWeb(new ImageLoadedEvent() {
//                @Override
//                public void onImageLoaded(final Drawable drawable) {
//                    Drawable mDrawable = drawable;
//                    if (drawable == null) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            mDrawable = mContext.getDrawable(R.drawable.bg_placeholder);
//                        } else {
//                            mDrawable = mContext.getResources().getDrawable(R.drawable.bg_placeholder);
//                        }
//                    }
//                    final Drawable finalMDrawable = mDrawable;
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Glide.with(mContext).load(travel.getImg()).centerCrop().placeholder(finalMDrawable)
//                                    .bitmapTransform(transformation).crossFade().into(holder.img);
//                        }
//                    }, 1000);
//                }
//            }).execute(travel.getLowimg());
            Glide.with(mContext).load(travel.getImg()).centerCrop().placeholder(R.drawable.bg_placeholder)
                    .bitmapTransform(transformation).crossFade().into(holder.img);
            String s = travel.getMota();
            if (s != null) {
                if (s.length() > LITMIT_STRING_LENGTH) {
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
