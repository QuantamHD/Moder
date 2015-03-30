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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class RegistrationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Button loginButton = (Button) findViewById(R.id.Signup);
        loginButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    vibrator.vibrate(28);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    goToTos();
                }
                return false;
            }
        });

    }

    private void goToTos(){
        Log.v("RegistrationActivity", "Switching to the TOS page");
        Intent intent = new Intent(this, TermsOfService.class);
        startActivity(intent);
    }
}
