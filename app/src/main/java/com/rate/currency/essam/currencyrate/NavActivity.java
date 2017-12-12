package com.rate.currency.essam.currencyrate;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.rate.currency.essam.currencyrate.adapter.BanksListAdapter;
import com.rate.currency.essam.currencyrate.model.BankListModel;
import com.rate.currency.essam.currencyrate.ui.CurrencyConverterFragment;
import com.rate.currency.essam.currencyrate.ui.GoldCurrencyFragment;
import com.rate.currency.essam.currencyrate.ui.MainFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class NavActivity extends AppCompatActivity {

    private ListView mDrawerList;
    public CustomDrawerAdapter adapter88;
    public List<DrawerItem> dataList;
    ActionBarDrawerToggle mDrawerToggle;

    DrawerLayout drawer;
    Toolbar toolbar ;
    RelativeLayout mDrawerPane;
    private Random mRandom;
    private int countToExit;
    Context context = NavActivity.this;

    ActionBarDrawerToggle actionBarDrawerToggle ;
    private boolean isAllowPressMoreToExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        dataList = new ArrayList<DrawerItem>();

        mDrawerPane = (RelativeLayout) findViewById(R.id.content_frame);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawer ,toolbar,R.string.drawer_open, R.string.drawer_close)
        {
            public void onDrawerClosed(View view) {
               // getActionBar().setTitle(title);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
           //     getActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }
        };
        mRandom = new Random();
        drawer.setDrawerListener(mDrawerToggle);

        dataList.add(new DrawerItem("اسعار العملات/Exchange Rates", R.drawable.banks_icon));
        dataList.add(new DrawerItem("تحويل / exchange", R.drawable.change_icon));
        dataList.add(new DrawerItem("الذهب و فضة/gold and silver", R.drawable.gold_icon));
        dataList.add(new DrawerItem("coming soon", R.drawable.ic_lancher));
        dataList.add(new DrawerItem("coming soon", R.drawable.ic_lancher));
        dataList.add(new DrawerItem("اعطي تقيم", R.drawable.ic_lancher));


        adapter88 = new CustomDrawerAdapter(this, R.layout.item_sliding_menu,
                dataList);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());



        mDrawerList.setAdapter(adapter88);
        displaySelectedScreen(0);



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

    private void displaySelectedScreen(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment= new MainFragment();
                break;
            case 1:
                fragment = new CurrencyConverterFragment();
                break;
            case 2:
                fragment = new GoldCurrencyFragment();
                break;
            case 3:

               break;
            case 4:
                break;
            case 5 :
                Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                }
                break;

        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            mDrawerList.setItemChecked(position, true);
            setTitle(dataList.get(position).getItemName());

            // Close the drawer
            drawer.closeDrawer(GravityCompat.START);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            displaySelectedScreen(position);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Listen Toggle state of navigation Drawer
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }




}