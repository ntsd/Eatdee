package me.hotcode.eatdee.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.hotcode.eatdee.fragments.DiaryFragment;
import me.hotcode.eatdee.fragments.HomeFragment;

/**
 * Created by Jiravat on 18/10/2559.
 */

public class MainContainerPagerAdapter extends FragmentPagerAdapter {
    public MainContainerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();

        switch (position) {
            case 0:
                return fragment = new HomeFragment();
            case 1:
                return fragment = new DiaryFragment();
//            case 2:
//                return fragment = new LoginFragment();
////                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://rukdee.com/"));
////                    startActivity(browserIntent);
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
