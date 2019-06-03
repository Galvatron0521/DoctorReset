package com.shenkangyun.doctor.MainPage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/21.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    List<String> list;
    List<Fragment> fragments = new ArrayList<>();

    public MyPagerAdapter(FragmentManager fm, List<String> list, List<Fragment> fragments) {
        super(fm);
        this.list = list;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }
}