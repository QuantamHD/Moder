package com.moderco.moder;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import com.moderco.network.GetProfileTask;
import com.moderco.utility.CookieHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ProfileActivity extends FragmentActivity{

    private String cookie;
    private ViewGroup group;

	@Override
	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

    }

    private void addPhotoButton(String url) {

    }
}
