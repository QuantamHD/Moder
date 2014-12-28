package com.moderco.moder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import com.moderco.views.PhotoProfile;


public class MainActivity extends Activity {

	PhotoProfile photoProfile;
	Button yesButton, noButton;
	Intent intent;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.
        		ThreadPolicy.Builder().permitAll().build();
        		StrictMode.setThreadPolicy(policy); 
        setContentView(R.layout.activity_main);
        yesButton = (Button) findViewById(R.id.yesButton);
        noButton = (Button) findViewById(R.id.noButton);
        photoProfile= (PhotoProfile) findViewById(R.id.photoProfile);
        
        intent = getIntent(); //Used for the intent getting
        
		//Yes Button
        yesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				photoProfile.changePhoto();
			}
		}); 
        
      //No Button
        noButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				photoProfile.setImageResource(R.drawable.test2);
				
			}
		}); 
        
    }
    
    
    //Pulled this from stackExchange
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
   
}
