package com.apptravel.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.apptravel.Fragment.HomeFragment;
import com.apptravel.Fragment.SearchFragment;
import com.apptravel.R;

/**
 * Created by vungho on 21/12/2016.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{

    private static final int numPage = 2;
    private String[] namePage;
    private int[] imgResID = {R.drawable.ic_home_white_36dp, R.drawable.ic_search_white_36dp};
    private Context context;

    public ViewPagerAdapter(FragmentManager fm, String[] namePage, Context context) {
        super(fm);
        this.namePage = namePage;
        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0){
            fragment = new HomeFragment();
        }else if (position == 1){
            fragment = new SearchFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return numPage;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
//        Drawable image = ContextCompat.getDrawable(context, imgResID[position]);
//        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//        SpannableString sb = new SpannableString(" ");
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return sb;
    }

    public View getTabCustomView(int position){
        final int legacyTabIconColor = ContextCompat.getColor(context,
                R.color.md_green_900);
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab_icon, null);
        ImageView imgIconTab = (ImageView)v.findViewById(R.id.img_icon_tab);
        imgIconTab.setBackgroundResource(imgResID[position]);
        return v;
    }

}
