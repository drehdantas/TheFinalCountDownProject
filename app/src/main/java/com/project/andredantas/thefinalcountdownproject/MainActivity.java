package com.project.andredantas.thefinalcountdownproject;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.andredantas.thefinalcountdownproject.ui.CountDownPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public CountDownPagerAdapter mPagerAdapter;

    @Bind(R.id.countdown_pager)
    ViewPager mPager;
    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViewPager();
    }

    public void initViewPager() {
        mPagerAdapter = new CountDownPagerAdapter(this,  getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(mPager);
    }
}
