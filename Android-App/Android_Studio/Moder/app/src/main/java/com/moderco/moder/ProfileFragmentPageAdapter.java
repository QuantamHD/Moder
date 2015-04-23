package com.moderco.moder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.moderco.fragments.ProfileFragment;
import com.moderco.fragments.RateFragment;

import java.io.File;

/**
 * Created by Ethan on 4/1/2015.
 */
public class ProfileFragmentPageAdapter extends FragmentPagerAdapter {

    private ProfileFragment profileFragment;
    private RateFragment rateFragment;

    public ProfileFragmentPageAdapter(FragmentManager fm) {
        super(fm);
        profileFragment = ProfileFragment.newInstance();
        rateFragment = RateFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return rateFragment;
        else
            return profileFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        if(position == 1) return "Profile";
        return "Rate";
    }

    public void setNewLocalFileProfile(File file){
        profileFragment.addNewCardByFile(file);
    }
}
