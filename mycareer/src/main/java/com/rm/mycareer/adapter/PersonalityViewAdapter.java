package com.rm.mycareer.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.rm.mycareer.view.ArePersonalityFragment;
import com.rm.mycareer.view.CanPersonalityFragment;
import com.rm.mycareer.view.DoPersonalityFragment;

/**
 * Created by rapha_000 on 03/05/2014.
 */
public class PersonalityViewAdapter extends FragmentPagerAdapter {

    public PersonalityViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new ArePersonalityFragment();
            case 1:
                // Games fragment activity
                return new CanPersonalityFragment();
            case 2:
                // Movies fragment activity
                return new DoPersonalityFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}