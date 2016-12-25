package com.apptravel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apptravel.Entity.DrawerMenuInfo;
import com.apptravel.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vungho on 22/12/2016.
 */

public class DrawerMenuAdapter extends ArrayAdapter<DrawerMenuInfo> {
    private Context context;
    private int layout;
    private ArrayList<DrawerMenuInfo> list;


    public DrawerMenuAdapter(Context context, int layout,ArrayList<DrawerMenuInfo> list) {
        super(context, layout, list);
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder myView;
        View view = convertView;
        if(view == null) {
            view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layout, null);
            myView = new ViewHolder();
            myView.imgAvatar = (ImageView) view.findViewById(R.id.imgDrawerMenu);
            myView.txtName = (TextView) view.findViewById(R.id.txtDrawerMenu);
            view.setTag(myView);
        } else{
            myView = (ViewHolder) view.getTag();
        }

        DrawerMenuInfo item = list.get(position);
        myView.txtName.setText(item.getName());
        Glide.with(this.getContext()).load(item.getId()).centerCrop().fitCenter().into(myView.imgAvatar);

        return view;
    }
    private class ViewHolder{
        private ImageView imgAvatar;
        private TextView txtName;

    }
}
