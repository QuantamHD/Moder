package com.moderco.moder;

import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.cookie.Cookie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.moderco.network.LoginTask;
import com.moderco.network.RegistrationTask;

public class LoginActivity extends Activity {

	/* login page */
	boolean loginPageShown = false;
	Button loginExpansionButton;
	RelativeLayout loginExpansionLayout;
	EditText username;
	EditText password;
	Button submitButton; // Logs in
	Button registerButton; // Brings up register page
	private int requiredPasswordLength = 8;
	private final String USERNAME_HINT = "Enter Email";
	private final String PASSWORD_HINT = "Enter Password";
	Context context; // For keeping track and stuff

	/* registration page */
	boolean registrationPageShown = false;
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

	public final static String COOKIE = "com.moderco.moder.MESSAGE";

	/**
	 * Sets up essentially the whole page.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Log.v("General", "Login Started"); // Todo Remove

		

		/* Login Expansion */
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		submitButton = (Button) findViewById(R.id.submitButton);
		loginExpansionLayout = (RelativeLayout) findViewById(R.id.loginExpandLayout);
		loginExpansionButton = (Button) findViewById(R.id.loginExpandButton);
		context = this; // find context right before for use
		loginExpansionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (loginPageShown) {
					username.setVisibility(View.GONE);
					password.setVisibility(View.GONE);
					submitButton.setVisibility(View.GONE);
				} else {
					username.setVisibility(View.VISIBLE);
					password.setVisibility(View.VISIBLE);
					submitButton.setVisibility(View.VISIBLE);
				}
				loginPageShown = !loginPageShown;
			}
		});

		submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginTask task = new LoginTask(username.getText().toString(),
						password.getText().toString());
				int code = -1;
				try {
					code = task.execute().get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				Log.v("LoginActivity", "Login Code: " + code);
				login(v, code, task.cookie);
			}
		});
		
		/* Registration Expansion */
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
							RegistrationTask task = new RegistrationTask(
									usernameRegistration.getText().toString(),
									passwordRegistration.getText().toString(),
									passwordCheckRegistration.getText()
											.toString());
							try {
								int code = task.execute().get();// Uploads it.
								if (code == RegistrationTask.SUCCESS_CODE) {
									//Now we log the user in with their new account
									LoginTask loginTask = new LoginTask(usernameRegistration.getText().toString(),
											passwordRegistration.getText().toString());
									int loginCode = -1;
									try {
										loginCode = loginTask.execute().get();
									} catch (InterruptedException e) {
										e.printStackTrace();
									} catch (ExecutionException e) {
										e.printStackTrace();
									}
									Log.v("LoginActivity", "Login Code: " + loginCode);
									login(v, loginCode, loginTask.cookie);
								}
								
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							} 
							
							
						}
					}
				});

		registerButton = (Button) findViewById(R.id.button2);
		registerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Start the animation
				if (!registrationPageShown) {
					usernameRegistration.setVisibility(View.VISIBLE);
					passwordRegistration.setVisibility(View.VISIBLE);
					passwordCheckRegistration.setVisibility(View.VISIBLE);
					confirmRegistrationButton.setVisibility(View.VISIBLE);
				} else {
					usernameRegistration.setVisibility(View.GONE);
					passwordRegistration.setVisibility(View.GONE);
					passwordCheckRegistration.setVisibility(View.GONE);
					confirmRegistrationButton.setVisibility(View.GONE);
				}
				registrationPageShown = !registrationPageShown;
			}
		});
	}

	/**
	 * Executes a client side check to see if the passwords work. Basically
	 * making sure they put a password in and that their password and their
	 * retyped passwords are the same.
	 * 
	 * @param pwd1
	 *            - The editText version
	 * @param pwd2
	 *            - The editText version
	 * @return error codes defined above
	 */
	public int passwordCheck(EditText pwd1, EditText pwd2) {
		String pwd1Text = pwd1.getText().toString();
		String pwd2Text = pwd2.getText().toString();

		/* TODO: Check passwords
		 * if (pwd1Text != pwd2Text) return PASSWORDS_NOT_IDENTICAL; else if
		 * (pwd1.length() > requiredPasswordLength) return PASSWORD_TOO_SHORT;
		 * else
		 */
		return PASSWORD_FINE;
	}

	/**
	 * Handles codes and calls switchScreen if code correct
	 * 
	 * @param view
	 */
	public void login(View view, int code, Cookie cookie) {
		if (code == LoginTask.SUCCESS_CODE) {
			switchScreen(cookie.toString());
		} else if (code == LoginTask.INFO_MISSING_CODE) {
			Log.v("LoginActivity", "Incorrect info.");
		} else if (code == LoginTask.CONNECTION_FAILED_CODE) {
			Log.v("LoginActivity", "Connection failed on login attempt");
		} else {
			Log.v("LoginActivity",
					"Unknown Error Code: "
							+ Integer.toString(code)
							+ "Check LoginTask.java or LoginActivity.java in Moder Client");
		}
	}

	/**
	 * Switches the activity to main activity from the login page.
	 * 
	 * @param cookie
	 */
	private void switchScreen(String cookie) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(COOKIE, cookie);
		startActivity(intent);
	}

}
