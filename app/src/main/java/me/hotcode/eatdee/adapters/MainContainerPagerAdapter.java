package me.hotcode.eatdee.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.io.Serializable;
import java.util.List;

import me.hotcode.eatdee.fragments.DiaryFragment;
import me.hotcode.eatdee.fragments.FoodListFragment;
import me.hotcode.eatdee.fragments.HomeFragment;
import me.hotcode.eatdee.models.ListFood;
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

    public MainContainerPagerAdapter(FragmentManager fm, Profile currentProfile, List<ListFood> listOfListFood) {
        super(fm);
        this.currentProfile = currentProfile;
        //sent var to fragment home
        args = new Bundle();
        args.putInt("canGetCurrentProfile", 1);
        args.putSerializable("currentProfile",currentProfile);
        args.putSerializable("listOfListFood", (Serializable) listOfListFood);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                fragment.setArguments(args);
                return fragment;
//            case 1:
//                fragment = new DiaryFragment();
//                fragment.setArguments(args);
//                return fragment;
            case 1:
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
        return 2;
    }
}
