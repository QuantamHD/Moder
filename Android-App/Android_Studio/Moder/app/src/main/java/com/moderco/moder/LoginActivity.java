package com.moderco.moder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moderco.network.ThreadedLogin;
import com.moderco.utility.CookieHandler;
import com.moderco.network.LoginTask;
import com.moderco.network.RegistrationTask;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends Activity {


    /* login page */
    private boolean loginPageShown = false;
    private Button loginExpansionButton;
    private RelativeLayout loginExpansionLayout;
    private EditText username;
    private EditText password;
    private Button submitButton; // Logs in
    private Button registerButton; // Brings up register page
    private static int requiredPasswordLength = 8;
    private Context context; // For keeping track and stuff
    private TextView moderLogo;
    private ImageView ribbonOne;
    private ImageView ribbonTwo;
    private Animation twirl;
    private Animation hover;
    private final int MAX_LOGIN_TRYS = 5;


    /* registration page */
    private boolean registrationPageShown = false;
    View paper; // Background to registration page
    private EditText usernameRegistration;
    private EditText passwordRegistration;
    private EditText passwordCheckRegistration; // Should match passwordRegistration
    private Button confirmRegistrationButton; // Sends registration info to server
    Button cancelRegistrationButton; // Destroys register page, goes back to
    // login
    private ProgressBar submitSpinner;

    /* Possible error codes */
    private final int PASSWORD_FINE = 0;
    private final int PASSWORDS_NOT_IDENTICAL = 1;
    private final int PASSWORD_TOO_SHORT = 2;

    /* Prefs */
    private static final String USER_PREF = "com.moderco.moder.user";
    private static final String PASS_PREF = "com.moderco.moder.pass";
    public static final String AUTO_LOGIN_PREF = "com.moderco.moder.autologin";
    private static final String FIRST_TIME_USER = "com.moderco.moder.firsttimer";


    /* Toasts */
    private static final String ACCOUNT_CREATED = "Welcome to Moder!";
    private static final String ERROR = "Oops! We're not sure what went wrong. Try using a different network connection.";
    private static final String PASSWORDS_NOT_SAME = "Your passwords do not match!";
    private static final String PASSWORD_TOO_SHORT_TOAST = "Your password needs to be " + Integer.toString(requiredPasswordLength) + " or longer!";
    private static final String LOGIN_INFO_INCORRECT = "Either your password or your email is incorrect!";
    private static final String LOST_CONNECTION = "It appears you lost connection";

    /**
     * Sets up essentially the whole page.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.LoginButton);
        loginButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    vibrator.vibrate(28);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    goToProfile();
                }
                return false;
            }
        });

        final Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    registerButton.setText(R.string.register_now_u);
                    vibrator.vibrate(28);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    registerButton.setText(R.string.register_now);
                    goToRegistration();
                }
                return false;
            }
        });
    }


    /**
     * Executes a client side check to see if the passwords work. Basically
     * making sure they put a password in and that their password and their
     * retyped passwords are the same.
     *
     * @param pwd1 - The editText version
     * @param pwd2 - The editText version
     * @return error codes defined above
     */
    int passwordCheck(EditText pwd1, EditText pwd2) {
        String pwd1Text = pwd1.getText().toString();
        String pwd2Text = pwd2.getText().toString();


        if (!pwd1Text.equals(pwd2Text))
            return PASSWORDS_NOT_IDENTICAL;
        else if (pwd1Text.length() < requiredPasswordLength)
            return PASSWORD_TOO_SHORT;
        else
            return PASSWORD_FINE;
    }

    int loginAttempts = 0;

    /**
     * Handles codes and calls switchScreen if code correct
     */
    void login(String user, String pass) {
        Log.v("LoginActivity", "Attempting Login...");

        loginAttempts++;

        LoginTask task = new LoginTask(user, pass);
        int code = -1;
        new Thread(new ThreadedLogin(user, pass)).start();
        try {
            code = task.execute().get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (code == LoginTask.SUCCESS_CODE) { //Breaks the recursive loop
            Log.v("LoginCookieRaw", task.cookie.getValue());
            switchScreen(CookieHandler.formatCookie(task.cookie)); //Only gets the cookie if successful
            setLoginCreds(user, pass);
        } else if (code == LoginTask.INFO_MISSING_CODE) {
            Log.v("LoginActivity", "Incorrect info.");
            if (loginAttempts <= MAX_LOGIN_TRYS) login(user, pass);
            Toast.makeText(getApplicationContext(), LOGIN_INFO_INCORRECT, Toast.LENGTH_SHORT).show();
        } else if (code == LoginTask.CONNECTION_FAILED_CODE) {
            Log.v("LoginActivity", "Connection failed on login attempt");
            if (loginAttempts <= MAX_LOGIN_TRYS) login(user, pass);
            Toast.makeText(getApplicationContext(), LOST_CONNECTION, Toast.LENGTH_SHORT).show();
        } else {
            Log.v("LoginActivity",
                    "Unknown Error Code: "
                            + Integer.toString(code)
                            + "Check LoginTask.java or LoginActivity.java in Moder Client");
            if (loginAttempts <= MAX_LOGIN_TRYS) login(user, pass);
            Toast.makeText(getApplicationContext(), ERROR, Toast.LENGTH_SHORT).show(); //This shouldn't happen. Hopefully.
        }


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
        Log.v("LoginCookieAFTER", cookie);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(CookieHandler.COOKIE, cookie);
        startActivity(intent);
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

    private void setLoginCreds(String user, String pass) {
        //Save info
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_PREF, user);
        editor.putString(PASS_PREF, pass);
        editor.putBoolean(AUTO_LOGIN_PREF, true);
        editor.apply();
        editor.commit();
    }

    private void loginCheck() {
        Log.v("LoginActivity", "Starting preactivity check");
        SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
        if (prefs.contains(USER_PREF) && prefs.contains(PASS_PREF)) {
            Log.v("LoginActivity", "Previous login info found, starting login...");
            String user = prefs.getString(USER_PREF, "-1");
            String pass = prefs.getString(PASS_PREF, "-1");

            login(user, pass);
        }
    }

}
