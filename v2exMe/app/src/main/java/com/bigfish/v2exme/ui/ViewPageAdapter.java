package com.bigfish.v2exme.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ViewPageAdapter extends FragmentStatePagerAdapter {

    private HotNewsFragment hotNewsFragment;
    private FastNewsFragment fastNewsFragment;

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);

        hotNewsFragment = new HotNewsFragment();
        fastNewsFragment = new FastNewsFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) return hotNewsFragment;
        return fastNewsFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Object" + (position + 1);
    }


    INewsFragment hotINewsFragment() {
        return hotNewsFragment;
    }

    INewsFragment fastINewsFragment() {
        return fastNewsFragment;
    }
}
