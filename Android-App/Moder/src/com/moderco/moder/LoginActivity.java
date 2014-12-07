package com.moderco.moder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity{
	
	EditText username;
	EditText password;
	Button submitButton;
	public final static String EXTRA_MESSAGE = "com.moderco.moder.MESSAGE";

	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.activity_login);
	     Log.v("General", "Cry");
	     
	     username = (EditText) findViewById(R.id.username);
	     password = (EditText) findViewById(R.id.password);
	     submitButton = (Button) findViewById(R.id.submitButton);
	     submitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				login(v);
			}
		});
	}
	
	public void login(View view){
		//Replace admin with other stuff
		if(username.getText().toString().equals("admin") && 
		password.getText().toString().equals("admin")){
			Intent intent = new Intent(this, MainActivity.class);
		    EditText editText = (EditText) findViewById(R.id.username);
		    String message = editText.getText().toString();
		    intent.putExtra(EXTRA_MESSAGE, message);
		    startActivity(intent);
		}else{
		
		}	
	}
}
