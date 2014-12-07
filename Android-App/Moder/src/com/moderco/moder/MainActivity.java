package com.moderco.moder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

	
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
