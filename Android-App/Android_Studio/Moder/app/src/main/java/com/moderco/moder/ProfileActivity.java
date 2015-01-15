package com.moderco.moder;

import android.app.Activity;
import android.os.Bundle;

public class ProfileActivity extends Activity{

	@Override
	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        //Do other stuff
	}
	
}
