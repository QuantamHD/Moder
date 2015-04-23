package com.moderco.moder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.moderco.utility.Moder;
import com.moderco.utility.RegistrationInformation;

import org.apache.http.Header;


public class TermsOfService extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);

        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Intent registrationIntent = getIntent();
        RegistrationInformation info = (RegistrationInformation) registrationIntent.getSerializableExtra(RegistrationActivity.GET_REGISTRATION_INFO);

        final AsyncHttpClient client = Moder.getClient();
        RequestParams params = new RequestParams();
        params.add("email",info.getEmail());
        params.add("pwd1",info.getPassword());
        params.add("pwd2",info.getPassword());
        params.add("gender",info.getGender());
        params.add("age", info.getAge()+"");
        Button button = (Button) findViewById(R.id.accept_tos);

        client.get("https://www.moderapp.com/RegistrationPage",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
