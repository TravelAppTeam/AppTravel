package com.apptravel.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.apptravel.Fragment.HomeFragment;
import com.apptravel.Fragment.SearchFragment;
import com.apptravel.R;
import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by vungho on 21/12/2016.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{

    private static final int numPage = 2;
    private String[] namePage;
    private int[] imgResID = {R.drawable.ic_home, R.drawable.ic_search};
    private Context context;

    public ViewPagerAdapter(FragmentManager fm, String[] namePage) {
        super(fm);
        this.namePage = namePage;

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
        return namePage[position];
    }

}
