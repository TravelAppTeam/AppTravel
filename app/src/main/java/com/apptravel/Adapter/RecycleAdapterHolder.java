package com.apptravel.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apptravel.Events.MyRecyclerViewItemListener;
import com.apptravel.R;

/**
 * Created by lldti on 27-Dec-16.
 */

public class RecycleAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView img;
    public TextView Ten;
    private MyRecyclerViewItemListener itemsClick;

    public RecycleAdapterHolder(View itemView) {
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
