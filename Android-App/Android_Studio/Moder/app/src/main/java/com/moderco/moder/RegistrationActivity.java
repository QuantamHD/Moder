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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.moderco.utility.RegistrationInformation;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistrationActivity extends Activity {

    private Pattern pattern;
    private Matcher matcher;
    public static final String GET_REGISTRATION_INFO = "RegInfo";
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        pattern = Pattern.compile(EMAIL_PATTERN);

        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        final EditText age= (EditText) findViewById(R.id.emailText);
        final EditText password = (EditText) findViewById(R.id.passwordText);
        final EditText confirmPassword = (EditText) findViewById(R.id.passwordConfirm);
        final EditText email = (EditText) findViewById(R.id.age_field);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final TextView errorText = (TextView) findViewById(R.id.error_text_register);


        Button loginButton = (Button) findViewById(R.id.Signup);
        loginButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    vibrator.vibrate(28);
                    errorText.setText("");
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    String emailText = email.getText().toString();
                    String ageText = age.getText().toString();
                    String passwordText = email.getText().toString();
                    String passwordConfirmText = email.getText().toString();
                    String gender = spinner.getSelectedItem().toString();
                    Log.v("Gender",gender);

                    if(!validate(emailText)){

                        errorText.setText("You must enter a valid email");
                        return false;
                    }
                    if(!passwordText.equals(passwordConfirmText)){
                        errorText.setText("Your passwords must match");
                        return false;
                    }

                    int ageNum = -1;
                    try {
                        ageNum = Integer.parseInt(ageText);
                        if(ageNum <= 0){
                            errorText.setText("Your age cannot be a negative number");
                            return false;
                        }
                        if(ageNum < 13){
                            errorText.setText("You have to 13 or older to use this app");
                            return false;
                        }
                    }catch (NumberFormatException ex){
                        ex.printStackTrace();
                        errorText.setText("You must enter a valid age");
                        return false;
                    }
                    RegistrationInformation information = new RegistrationInformation();
                    information.setAge(ageNum);
                    information.setEmail(emailText);
                    information.setGender(gender);
                    information.setPassword(passwordText);

                    goToTos(information);
                }
                return false;
            }
        });

    }

    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }

    private void goToTos(RegistrationInformation information){
        Log.v("RegistrationActivity", "Switching to the TOS page");
        Intent intent = new Intent(this, TermsOfService.class);
        intent.putExtra(GET_REGISTRATION_INFO,information);
        startActivity(intent);
    }


}
