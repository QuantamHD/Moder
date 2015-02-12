package com.moderco.moder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.moderco.network.GetProfileTask;
import com.moderco.utility.CookieHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ProfileActivity extends Activity{

    private String cookie;
    private ViewGroup group;

	@Override
	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        cookie = getIntent().getStringExtra(CookieHandler.COOKIE);

        GetProfileTask task = new GetProfileTask(cookie);
        try {
            JSONObject jObject = task.execute().get();
            //Adding profile photos
            JSONArray uuid_photo = jObject.getJSONArray("UUID_Photo");
            for (int i = 0; i < uuid_photo.length(); i++) {

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addPhotoButton(String url) {

    }

    public void backToMain(View v) {
        Intent intentProfile = new Intent(getApplicationContext(), MainActivity.class);
        intentProfile.putExtra(CookieHandler.COOKIE, cookie);
        startActivity(intentProfile);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
	
}
