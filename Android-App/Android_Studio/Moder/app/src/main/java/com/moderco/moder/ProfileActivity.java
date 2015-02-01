package com.moderco.moder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.moderco.utility.CookieHandler;

public class ProfileActivity extends Activity{

    private String cookie;

	@Override
	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        cookie = getIntent().getStringExtra(CookieHandler.COOKIE);

        //Do other stuff
	}

    public void backToMain(View v) {
        Intent intentProfile = new Intent(getApplicationContext(), MainActivity.class);
        intentProfile.putExtra(CookieHandler.COOKIE, cookie);
        startActivity(intentProfile);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
	
}
