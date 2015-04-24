package com.moderco.moder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.moderco.utility.Moder;
import com.moderco.utility.RegistrationInformation;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


public class TermsOfService extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);

        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Intent registrationIntent = getIntent();
        final RegistrationInformation info = (RegistrationInformation) registrationIntent.getSerializableExtra(RegistrationActivity.GET_REGISTRATION_INFO);
        final ImageView imglogo12 = (ImageView) findViewById(R.id.ie);
        final AsyncHttpClient client = Moder.getClient();
        final RequestParams params = new RequestParams();
        params.add("email", info.getEmail());
        params.add("pwd1", info.getPassword());
        params.add("pwd2", info.getPassword());
        params.add("gender", info.getGender());
        params.add("age", info.getAge() + "");
        Button button = (Button) findViewById(R.id.opopopopopo);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    imglogo12.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.new_animation));
                    vibrator.vibrate(28);
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.v("happens","happens");
                    client.get("https://www.moderapp.com/RegistrationPage", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String json = new String(responseBody);
                            JSONObject object = new JSONObject();
                            final RequestParams params2 = new RequestParams();
                            params2.add("email", info.getEmail());
                            params2.add("pwd", info.getPassword());
                            client.get("https://www.moderapp.com/login", params2, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    String json2 = new String(responseBody);
                                    try {
                                        JSONObject object2 = new JSONObject(json2);
                                        Log.v("Response Code", object2.getInt("ResponseCode") + "");
                                        if (object2.getInt("ResponseCode") == 300) {
                                            SharedPreferences.Editor editor= Moder.getPrefs().edit();
                                            editor.putString("email",info.getEmail());
                                            editor.putString("pwd",info.getPassword());
                                            editor.commit();

                                            goToProfile();

                                        } else {
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    Log.v("Failed","Little Fail");
                                }
                            });

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.v("Failed","Big Fail");
                        }


                    });

                }
                return false;
            }
        });

    }
    private void goToProfile() {
        Log.v("TOS", "Switching To Profile Page");
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
