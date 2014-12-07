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
import android.widget.ImageView;


public class MainActivity extends Activity {

	ImageView photoProfile;
	Button yesButton, noButton;
	View photoBorder;
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
        photoProfile= (ImageView) findViewById(R.id.photoProfile);
        photoBorder = (View) findViewById(R.id.rect);
        
        intent = getIntent(); //Used for the intent getting
        
        
        //Set border originally
        photoBorder.getLayoutParams().height = photoProfile.getDrawable().getIntrinsicHeight() + 20;
		photoBorder.getLayoutParams().width = photoProfile.getDrawable().getIntrinsicWidth() + 20;
        
		//Yes Button
        yesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bitmap = null;
				try {
					bitmap = BitmapFactory.decodeStream((InputStream)new URL("http://env-7765194.whelastic.net/image").getContent());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
				photoProfile.setImageBitmap(bitmap);
				
				//reset border
				photoBorder.getLayoutParams().height = photoProfile.getDrawable().getIntrinsicHeight() + 20;
				photoBorder.getLayoutParams().width = photoProfile.getDrawable().getIntrinsicWidth() + 20;
			}
		}); 
        
      //No Button
        noButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				photoProfile.setImageResource(R.drawable.test2);
				
				//reset border
				photoBorder.getLayoutParams().height = photoProfile.getDrawable().getIntrinsicHeight() + 20;
				photoBorder.getLayoutParams().width = photoProfile.getDrawable().getIntrinsicWidth() + 20;
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
