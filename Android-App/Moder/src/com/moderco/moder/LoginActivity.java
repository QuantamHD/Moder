package com.moderco.moder;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moderco.network.CookieHandler;
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
	private static int requiredPasswordLength = 8;
	private final String USERNAME_HINT = "Enter Email";
	private final String PASSWORD_HINT = "Enter Password";
	Context context; // For keeping track and stuff
	TextView moderLogo;
	ImageView ribbonOne;
	ImageView ribbonTwo;
	Animation twirl;
	Animation hover;
	

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

	
	
	/* Toasts */
	private static final String ACCOUNT_CREATED = "Welcome to Moder!";
	private static final String ERROR = "Something went wrong! We're working on it!";
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
		setContentView(R.layout.activity_login);
		Log.v("General", "Login Started"); // Todo Remove

		moderLogo = (TextView) findViewById(R.id.textView1); //For animation practice
		ribbonOne = (ImageView) findViewById(R.id.ribbon1);
		ribbonTwo = (ImageView) findViewById(R.id.ribbon2);
		twirl = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.twirl);
		hover = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hover);

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
				submitButton.startAnimation(twirl);
				if ((username.getText().toString().length() > 0)
						&& (password.getText().toString().length() > 0)) {
					submitButton.setText("Connecting..."); //Show that it's doing something
					LoginTask task = new LoginTask(username.getText()
							.toString(), password.getText().toString());
					int code = -1;
					
					try {
						
						code = task.execute().get();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					} 
					Log.v("LoginActivity", "Login Code: " + code);
					login(v, code, task);
				}
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
						if (passCheck == PASSWORD_TOO_SHORT) {
							Toast.makeText(getApplicationContext(), PASSWORDS_NOT_SAME, Toast.LENGTH_SHORT).show();;
						} else if (passCheck == PASSWORDS_NOT_IDENTICAL) {
							Toast.makeText(getApplicationContext(), PASSWORD_TOO_SHORT_TOAST, Toast.LENGTH_SHORT).show();
						} else if (passCheck == PASSWORD_FINE) {
							RegistrationTask task = new RegistrationTask(
									usernameRegistration.getText().toString(),
									passwordRegistration.getText().toString(),
									passwordCheckRegistration.getText()
											.toString());
							try {
								int code = task.execute().get();// Uploads it.
								if (code == RegistrationTask.SUCCESS_CODE) {
									//Tell the user all's good.
									Toast.makeText(getApplicationContext(), ACCOUNT_CREATED, Toast.LENGTH_SHORT).show();
									
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
									login(v, loginCode, loginTask);
								}
								
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							} 
						} else {
							Toast.makeText(getApplicationContext(), ERROR, Toast.LENGTH_SHORT).show(); //This shouldn't happen. Hopefully.
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
		
		//For shits and giggles
		moderLogo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				moderLogo.startAnimation(twirl);
				ribbonOne.startAnimation(hover);
				ribbonTwo.startAnimation(hover);
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

		
		 if (pwd1Text != pwd2Text) 
			 return PASSWORDS_NOT_IDENTICAL; 
		 else if (pwd1.length() > requiredPasswordLength) 
			 return PASSWORD_TOO_SHORT;
		 else
			 return PASSWORD_FINE;
	}

	/**
	 * Handles codes and calls switchScreen if code correct
	 * 
	 * @param view
	 */
	public void login(View view, int code, LoginTask task) {
		
		if (code == LoginTask.SUCCESS_CODE) {
			switchScreen(CookieHandler.formatCookie(task.cookie)); //Only gets the cookie if successful
		} else if (code == LoginTask.INFO_MISSING_CODE) {
			Log.v("LoginActivity", "Incorrect info.");
			Toast.makeText(getApplicationContext(), LOGIN_INFO_INCORRECT, Toast.LENGTH_SHORT).show();
		} else if (code == LoginTask.CONNECTION_FAILED_CODE) {
			Log.v("LoginActivity", "Connection failed on login attempt");
			Toast.makeText(getApplicationContext(), LOST_CONNECTION, Toast.LENGTH_SHORT).show();
		} else {
			Log.v("LoginActivity",
					"Unknown Error Code: "
							+ Integer.toString(code)
							+ "Check LoginTask.java or LoginActivity.java in Moder Client");
			Toast.makeText(getApplicationContext(), ERROR, Toast.LENGTH_SHORT).show(); //This shouldn't happen. Hopefully.
		}
		submitButton.setText("Let's Go!"); //Reset it back
	}

	/**
	 * Switches the activity to main activity from the login page.
	 * 
	 * @param cookie
	 */
	private void switchScreen(String cookie) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(CookieHandler.COOKIE, cookie);
		startActivity(intent);
	}

}
