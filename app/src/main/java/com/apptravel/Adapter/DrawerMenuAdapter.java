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
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(layout, null);

        ImageView imgAvatar = (ImageView)convertView.findViewById(R.id.imgDrawerMenu);
        TextView txtName = (TextView)convertView.findViewById(R.id.txtDrawerMenu);

        DrawerMenuInfo item = list.get(position);
        txtName.setText(item.getName());
        Picasso.with(this.getContext()).load(item.getId()).centerCrop().fit().into(imgAvatar);

        return convertView;
    }
}
