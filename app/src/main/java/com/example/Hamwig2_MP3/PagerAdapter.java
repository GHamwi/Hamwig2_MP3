package com.example.Hamwig2_MP3;

/**************** Created by George B. Hamwi Homework 5 *******************/

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    public PagerAdapter(FragmentManager fm,int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    /* Different tabs on the MainActivity and which ever the user clicks it will go to it */
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new Music();
            case 1:
                return new Album();
            case 2:
                return new Playlist();
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
