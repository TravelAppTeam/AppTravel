package com.apptravel.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.apptravel.Adapter.DrawerMenuAdapter;
import com.apptravel.Adapter.ViewPagerAdapter;
import com.apptravel.Entity.DrawerMenuInfo;
import com.apptravel.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final static String INTRO_BUNDLE_KEY = "intro bundle key";

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

    private boolean doubleClickBack = false;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getStringArray(R.array.sliding_tab)[0]);
        edtSearch = (EditText) findViewById(R.id.edt_search_view);

        settingEdtSearch();
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
                switch (position) {
                    case 0:
                        Intent appIntent = new Intent(MainActivity.this, IntroActivity.class);
                        appIntent.putExtra(INTRO_BUNDLE_KEY, true);
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

    private void settingEdtSearch() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        edtSearch.getLayoutParams().width = 2 * dm.widthPixels / 3;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int postion = mTabLayout.getSelectedTabPosition();
            if (postion != 0) {
                mViewPager.setCurrentItem(0);
            } else {
                if (doubleClickBack) {
                    super.onBackPressed();
                    finish();
                    IntroActivity.instance.finish();
                    return;
                }

                doubleClickBack = true;
                Snackbar.make(findViewById(R.id.drawer_layout), R.string.close_app_now, Snackbar.LENGTH_LONG)
                        .setAction(R.string.exit_now, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                                IntroActivity.instance.finish();
                                LoadingActivity.instance.finish();
                            }
                        }).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleClickBack = false;
                    }
                }, 2000);

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isOnline()) {
            showDialog();
            if (mTabLayout.getSelectedTabPosition() == 0) {
                edtSearch.setVisibility(View.GONE);
            } else {
                edtSearch.setVisibility(View.VISIBLE);
            }
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
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                getResources().getStringArray(R.array.sliding_tab), this);

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_home_white_48dp);
//        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_search_white_48dp);

        final int legacyTabIconColor = ContextCompat.getColor(getApplication(),
                R.color.md_green_900);
        final int selectTabIconColor = ContextCompat.getColor(getApplication(),
                R.color.md_white_1000);

        for (int i=0; i<mTabLayout.getTabCount(); i++){
            TabLayout.Tab tab = mTabLayout.getTabAt(i)
                    .setCustomView(mViewPagerAdapter.getTabCustomView(i));
        }

        ((ImageView)mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.img_icon_tab))
                .getBackground().setColorFilter(selectTabIconColor, PorterDuff.Mode.SRC_IN);

        ((ImageView)mTabLayout.getTabAt(1).getCustomView().findViewById(R.id.img_icon_tab))
                .getBackground().setColorFilter(legacyTabIconColor, PorterDuff.Mode.SRC_IN);

        ((ImageView)mTabLayout.getTabAt(2).getCustomView().findViewById(R.id.img_icon_tab))
                .getBackground().setColorFilter(legacyTabIconColor, PorterDuff.Mode.SRC_IN);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        edtSearch.setVisibility(View.GONE);
                        toolbar.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        edtSearch.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.GONE);
                        break;
                    case 2:
                        edtSearch.setVisibility(View.GONE);
                        toolbar.setVisibility(View.VISIBLE);
                        break;
                }
                //tab.getIcon().setColorFilter(selectTabIconColor, PorterDuff.Mode.SRC_IN);
                ImageView imageView = (ImageView)(tab.getCustomView()).findViewById(R.id.img_icon_tab);
                imageView.getBackground().setColorFilter(selectTabIconColor, PorterDuff.Mode.SRC_IN);
                if (imageView.getDrawable() == null){
                    Log.i("image", "NULL");
                }                          else{
                    Log.i("image", "Not null");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab.getIcon().setColorFilter(legacyTabIconColor, PorterDuff.Mode.SRC_IN);
                ((ImageView)tab.getCustomView().findViewById(R.id.img_icon_tab)).getBackground().
                       setColorFilter(legacyTabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void configDrawer() {
        //Config user list
        listUser = (ListView) findViewById(R.id.list_user);
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
        listAbout = (ListView) findViewById(R.id.list_about);
        aboutInfos = new ArrayList<DrawerMenuInfo>();
        aboutInfos.add(new DrawerMenuInfo(R.drawable.ic_about_app,
                getString(R.string.menu_about_app)));
        aboutInfos.add(new DrawerMenuInfo(R.drawable.ic_about_us,
                getString(R.string.menu_about_us)));
        aboutAdapter = new DrawerMenuAdapter(this, R.layout.item_menu, aboutInfos);
        listAbout.setAdapter(aboutAdapter);
    }

    public boolean isOnline() {
        ConnectivityManager conManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title));
        builder.setMessage(getString(R.string.dialog_mess));

        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        builder.show();
    }
}
