package com.project.andredantas.thefinalcountdownproject.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.project.andredantas.thefinalcountdownproject.R;

/**
 * Created by Andre Dantas on 2/17/17.
 */

public class CountDownPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 2;
    private Context context;

    public CountDownPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case SIMPLE:
                fragment = new SimpleCountDownFragment();
                break;
            case UPCOMING:
                fragment = new UpcomingCountDownFragment();
                break;
            default:
                fragment = new SimpleCountDownFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String[] tabs = context.getResources().getStringArray(R.array.tabs);

        switch (position){
            case SIMPLE:
                return tabs[SIMPLE];
            case UPCOMING:
                return tabs[UPCOMING];
            default:
                return tabs[SIMPLE];
        }
    }

    public static final int SIMPLE = 0;
    public static final int UPCOMING = 1;


}