package com.moderco.moder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity {

	
	ImageView photoProfile;
	Button yesButton, noButton;
	View photoBorder;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yesButton = (Button) findViewById(R.id.yesButton);
        noButton = (Button) findViewById(R.id.noButton);
        photoProfile= (ImageView) findViewById(R.id.photoProfile);
        photoBorder = (View) findViewById(R.id.rect);
        
        //Set border originally
        photoBorder.getLayoutParams().height = photoProfile.getDrawable().getIntrinsicHeight() + 20;
		photoBorder.getLayoutParams().width = photoProfile.getDrawable().getIntrinsicWidth() + 20;
        
		//Yes Button
        yesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				photoProfile.setImageResource(R.drawable.test2);
				
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


   
}
