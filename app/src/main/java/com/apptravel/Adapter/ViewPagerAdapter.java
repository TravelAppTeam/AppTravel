package com.apptravel.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.apptravel.Fragment.HomeFragment;
import com.apptravel.Fragment.SearchFragment;

/**
 * Created by vungho on 21/12/2016.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int numPage = 2;
    private String[] namePage;
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
