package com.moderco.moder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.moderco.network.RegistrationTask;

public class LoginActivity extends Activity {

	/* login page */
	EditText username;
	EditText password;
	Button submitButton; // Logs in
	Button registerButton; // Brings up register page
	private int requiredPasswordLength = 8;

	/* registration page */
	View paper; // Background to registration page
	EditText usernameRegistration;
	EditText passwordRegistration;
	EditText passwordCheckRegistration; // Should match passwordRegistration
	Button confirmRegistrationButton; // Sends registration info to server
	Button cancelRegistrationButton; // Destroys register page, goes back to
										// login

	/* Possible error codes */
	private final int PASSWORD_FINE = 0;
	private final int PASSWORDS_NOT_IDENTICAL = 1;
	private final int PASSWORD_TOO_SHORT = 2;

	
	public final static String EXTRA_MESSAGE = "com.moderco.moder.MESSAGE";

	
	/**
	 * Sets up essentially the whole page.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Log.v("General", "Login Started"); // Todo Remove

		/* Registration Popup */
		paper = findViewById(R.id.paperLayout);
		usernameRegistration = (EditText) findViewById(R.id.emailRegistration);
		passwordRegistration = (EditText) findViewById(R.id.passwordRegistration);
		passwordCheckRegistration = (EditText) findViewById(R.id.passwordRegistrationVerification);

		confirmRegistrationButton = (Button) findViewById(R.id.confirmRegistrationButton);
		confirmRegistrationButton
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						// Make sure passwords are kosher
						int passCheck = passwordCheck(passwordRegistration,
								passwordCheckRegistration);

						// Run Password check
						if (passCheck == PASSWORD_FINE) {
							RegistrationTask task = new RegistrationTask(usernameRegistration.getText().toString(),
									passwordRegistration.getText().toString(), passwordCheckRegistration.getText().toString());
							task.execute(); //Uploads it. 
							paper.setVisibility(View.GONE); // Remove the screen, go back to
						}
						// TODO: Login and change the screen to main feed
					}
				});

		cancelRegistrationButton = (Button) findViewById(R.id.cancelRegistrationButton);
		cancelRegistrationButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				paper.setVisibility(View.GONE); // Remove the screen, go back to
												// login
			}
		});

		/* Login page */
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				login(v);
			}
		});

		registerButton = (Button) findViewById(R.id.button2);
		registerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Start the animation
				paper.setVisibility(View.VISIBLE);
			}
		});
	}

	/**
	 * Executes a client side check to see if the passwords
	 * work. Basically making sure they put a password in
	 * and that their password and their retyped passwords
	 * are the same. 
	 * @param pwd1 - The editText version
	 * @param pwd2 - The editText version
	 * @return error codes defined above
	 */
	public int passwordCheck(EditText pwd1, EditText pwd2) {
		String pwd1Text = pwd1.getText().toString();
		String pwd2Text = pwd2.getText().toString();

		/*if (pwd1Text != pwd2Text)
			return PASSWORDS_NOT_IDENTICAL;
		else if (pwd1.length() > requiredPasswordLength)
			return PASSWORD_TOO_SHORT;
		else */
			return PASSWORD_FINE;
	}

	
	/**
 	* Switches the activity to main activity from the login page.
 	* 
 	* @param view
 	*/
	public void login(View view) {
		// Replace admin with other stuff
		if (username.getText().toString().equals("")
				&& password.getText().toString().equals("")) {
			Intent intent = new Intent(this, MainActivity.class);
			EditText editText = (EditText) findViewById(R.id.username);
			String message = editText.getText().toString();
			intent.putExtra(EXTRA_MESSAGE, message);
			startActivity(intent);
		} else {

		}
	}
}