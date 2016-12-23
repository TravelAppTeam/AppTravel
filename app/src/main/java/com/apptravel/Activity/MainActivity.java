package com.apptravel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.apptravel.Adapter.DrawerMenuAdapter;
import com.apptravel.Adapter.ViewPagerAdapter;
import com.apptravel.Entity.DrawerMenuInfo;
import com.apptravel.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final static String INTRO_BUNDLE_KEY= "intro bundle key";

    private ListView listUser;
    private ListView listAbout;
    private DrawerMenuAdapter userAdapter;
    private DrawerMenuAdapter aboutAdapter;
    private ArrayList<DrawerMenuInfo> userInfos;
    private ArrayList<DrawerMenuInfo> aboutInfos;
    public static Toolbar toolbar;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getStringArray(R.array.sliding_tab)[0]);

        configDrawer();
        configViewPager();



        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        //Event of List view menu
        listAbout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawer.closeDrawer(GravityCompat.START);
                switch (position){
                    case 0:
                        Intent appIntent = new Intent(MainActivity.this, IntroActivity.class);
                        appIntent.putExtra(INTRO_BUNDLE_KEY,true);
                        startActivity(appIntent);
                        break;
                    case 1:
                        Intent usIntent = new Intent(MainActivity.this, AboutUsActivity.class);
                        startActivity(usIntent);
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml

        return super.onOptionsItemSelected(item);
    }

    private void configViewPager() {
        mViewPager = (ViewPager)findViewById(R.id.viewPager);
        mTabLayout = (TabLayout)findViewById(R.id.tabLayout);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.sliding_tab), this);

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
    private void configDrawer(){
        //Config user list
        listUser = (ListView)findViewById(R.id.list_user);
        userInfos = new ArrayList<DrawerMenuInfo>();
        userInfos.add(new DrawerMenuInfo(R.drawable.ic_user,
                    getString(R.string.menu_user)));
        userInfos.add(new DrawerMenuInfo(R.drawable.ic_facebook,
                    getString(R.string.menu_facebook)));
        userInfos.add(new DrawerMenuInfo(R.drawable.ic_youtube,
                    getString(R.string.menu_youtube)));
        userAdapter = new DrawerMenuAdapter(this, R.layout.item_menu, userInfos);
        listUser.setAdapter(userAdapter);

        //config about list
        listAbout = (ListView)findViewById(R.id.list_about);
        aboutInfos = new ArrayList<DrawerMenuInfo>();
        aboutInfos.add(new DrawerMenuInfo(R.drawable.ic_about_app,
                    getString(R.string.menu_about_app)));
        aboutInfos.add(new DrawerMenuInfo(R.drawable.ic_about_us,
                    getString(R.string.menu_about_us)));
        aboutAdapter = new DrawerMenuAdapter(this, R.layout.item_menu, aboutInfos);
        listAbout.setAdapter(aboutAdapter);
    }


}
