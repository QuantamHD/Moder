package com.moderco.moder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.moderco.utility.Moder;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.List;


public class LoginActivity extends Activity {
    private String userEmail;
    private String userPassword;
    private AsyncHttpClient client;

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = new AsyncHttpClient();
        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        AsyncHttpClient.allowRetryExceptionClass(SocketTimeoutException.class);
        setContentView(R.layout.activity_login);
        final PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);
        myCookieStore.clearExpired(new Date());
        Moder.setClient(client);
        List<Cookie> cookieList =myCookieStore.getCookies();
        for(Cookie cookie:cookieList){
            if(cookie.getName().equals("Unique_ID")){
                //goToProfile();
            }
        }

        final EditText email = (EditText) findViewById(R.id.email_text_login);
        final EditText password = (EditText) findViewById(R.id.password_text_login);
        final TextView errorText = (TextView) findViewById(R.id.error_text_login);
        final ImageView moderLogo = (ImageView) findViewById(R.id.moder_logo_login);
        Button loginButton = (Button) findViewById(R.id.accept_tos);
        setupUI(findViewById(R.id.main_parent_login));
        final Animation a = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_img);
        loginButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    errorText.setText("");
                    moderLogo.setAnimation(a);
                    vibrator.vibrate(28);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    String emailTextU = email.getText().toString();
                    String passwordTextU = password.getText().toString();

                    RequestParams params = new RequestParams();
                    params.add("email", emailTextU);
                    params.add("pwd", passwordTextU);
                    client.get("https://www.moderapp.com/login", params, new AsyncHttpResponseHandler() {

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                            String s = new String(response);
                            moderLogo.setAnimation(null);
                            try {
                                JSONObject object = new JSONObject(s);
                                int responseCode = object.getInt("ResponseCode");
                                switch (responseCode){
                                    case 300:{
                                        goToProfile();
                                        break;}
                                    case 100:{
                                        errorText.setText("Your Email and Password combination is incorrect");
                                        vibrator.vibrate(100);
                                        break;
                                    }
                                }
                                Log.v("Output",myCookieStore.getCookies().toString());
                                Log.v("Output", s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        }

                        @Override
                        public void onRetry(int retryNo) {

                        }
                    });

                }
                return false;
            }
        });

        final Button registerButton = (Button) findViewById(R.id.real_register_button);
        registerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    vibrator.vibrate(28);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    goToRegistration();
                }
                return false;
            }
        });


    }

    public  void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Switches the activity to main activity from the log // TEST
     * GetUrlsTask task = new GetUrlsTask(this, cookie);
     * task.execute();
     * // TESTin page.
     *
     * @param cookie
     */
    private void switchScreen(String cookie) {
    }

    private void goToRegistration() {
        Log.v("LoginActivity", "Switching To Registration Page");
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void goToProfile() {
        Log.v("LoginActivity", "Switching To Profile Page");
        Intent intent = new Intent(this, ProfileActivity.class);

        startActivity(intent);
    }
}
