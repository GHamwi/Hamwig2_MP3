package com.example.Hamwig2_MP3;

/**************** Created by George B. Hamwi Homework 5 *******************/

import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    /*  Creates different integers */
    Toolbar mToolbar;
    PagerAdapter mPagerAdapter;
    TabLayout mTabLayout;
    TabItem musicTabItem;
    TabItem albumTabItem;
    TabItem playlistTabItem;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        /* Creates different onclick buttons and what each one does */
        mTabLayout = findViewById(R.id.tabLayout);
        musicTabItem = findViewById(R.id.musicTabItem);
        albumTabItem = findViewById(R.id.albumTabItem);
        playlistTabItem = findViewById(R.id.playlistTabItem);
        mViewPager = findViewById(R.id.pager);

        /* calls the pager class */
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),mTabLayout.getTabCount());

        /* sets up the adapter for pager */
        mViewPager.setAdapter(mPagerAdapter);

        /* Do something when the tab is selected */
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

        /* set current tab position */
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

                /* When the Album Tab Selected */
                if(tab.getPosition() == 1){

                /* When the Music Tab Selected */
                }else if(tab.getPosition() == 2){

                /* When the Playlist Tab Selected */
                }else {
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }
}
