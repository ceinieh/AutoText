package com.example.pocdynamictext.cards.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagesAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagesAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FontsFragment tab1 = new FontsFragment();
                return tab1;
            case 1:
                FontSizesFragment tab2 = new FontSizesFragment();
                return tab2;
            case 2:
                AlignmentFragment tab3 = new AlignmentFragment();
                return tab3;
            case 3:
                ColorsFragment tab4 = new ColorsFragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
