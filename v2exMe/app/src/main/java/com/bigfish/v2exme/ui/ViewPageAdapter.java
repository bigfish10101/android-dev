package com.bigfish.v2exme.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ViewPageAdapter extends FragmentStatePagerAdapter {

    private HotNewsFragment hotNewsFragment;
    private FastNewsFragment fastNewsFragment;
    private NodesFragment nodesFragment;

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);

        hotNewsFragment = new HotNewsFragment();
        fastNewsFragment = new FastNewsFragment();
        nodesFragment = new NodesFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) return hotNewsFragment;
        if (position == 1) return fastNewsFragment;
        return nodesFragment;
    }

    @Override
    public int getCount() {
        return 3;
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

    INewsFragment nodeINewsFrgment() { return nodesFragment; }

    public void addFragmentTapListener(IFragmentTapListener iFragmentTapListener){

        if (hotNewsFragment != null) {
            hotNewsFragment.addFragmentTapListener(iFragmentTapListener);
        }

        if (fastNewsFragment != null) {
            fastNewsFragment.addFragmentTapListener(iFragmentTapListener);
        }

        if (nodesFragment != null) {
            nodesFragment.addFragmentTapListener(iFragmentTapListener);
        }
    }
}
