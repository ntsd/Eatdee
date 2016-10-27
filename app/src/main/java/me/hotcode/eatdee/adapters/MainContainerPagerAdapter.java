package me.hotcode.eatdee.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.hotcode.eatdee.fragments.DiaryFragment;
import me.hotcode.eatdee.fragments.FoodListFragment;
import me.hotcode.eatdee.fragments.HomeFragment;
import me.hotcode.eatdee.models.Profile;

/**
 * Created by Jiravat on 18/10/2559.
 */

public class MainContainerPagerAdapter extends FragmentPagerAdapter {
    public MainContainerPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    Profile currentProfile;
    Bundle args;

    public MainContainerPagerAdapter(FragmentManager fm, Profile currentProfile) {
        super(fm);
        this.currentProfile = currentProfile;
        //sent var to fragment home
        args = new Bundle();
        args.putInt("canGetCurrentProfile", 1);
        args.putSerializable("currentProfile",currentProfile);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                fragment.setArguments(args);
                return fragment;
            case 1:
                fragment = new DiaryFragment();
                fragment.setArguments(args);
                return fragment;
            case 2:
                fragment = new FoodListFragment();
                fragment.setArguments(args);
                return fragment;
//            case 3:
//                return fragment = new AboutFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
