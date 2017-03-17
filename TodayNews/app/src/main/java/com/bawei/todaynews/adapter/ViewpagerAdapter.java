package com.bawei.todaynews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/10.
 */
public class ViewpagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> list_String;
    private ArrayList<Fragment> list_frag;


    public ViewpagerAdapter(FragmentManager fm, ArrayList<String> list_String,ArrayList<Fragment> list_frag) {
        super(fm);
        this.list_String=list_String;
        this.list_frag=list_frag;
    }


    @Override
    public Fragment getItem(int position) {
        return list_frag.get(position);
    }

    @Override
    public int getCount() {
        return list_frag.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_String.get(position);
    }
}
