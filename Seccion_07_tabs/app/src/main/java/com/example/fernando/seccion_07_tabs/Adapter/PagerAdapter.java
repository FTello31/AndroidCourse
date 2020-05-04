package com.example.fernando.seccion_07_tabs.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.fernando.seccion_07_tabs.Fragments.FirstFragment;
import com.example.fernando.seccion_07_tabs.Fragments.SecondFragment;
import com.example.fernando.seccion_07_tabs.Fragments.ThirdFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {


    private int numberOfTabs;


    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
