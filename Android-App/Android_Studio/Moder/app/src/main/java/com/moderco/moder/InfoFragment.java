package com.moderco.moder;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Evan on 1/16/15.
 */
public class InfoFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.abc_fade_out, R.anim.expand_fragment);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_info, container, false);
    }
}
