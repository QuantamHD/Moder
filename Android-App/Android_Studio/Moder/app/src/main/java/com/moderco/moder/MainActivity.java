package com.moderco.moder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;

import com.loopj.android.http.PersistentCookieStore;
import com.moderco.network.CookieHandler;
import com.moderco.network.PostPhotosTask;
import com.moderco.views.PhotoProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity {

	PhotoProfile photoProfile;
	Button yesButton, noButton;
	ImageButton cameraButton, searchButton, menuButton;
	RelativeLayout menu;
	Intent intent;
	
	private String cookie;
	PersistentCookieStore cookieStore;
	
	//For camera stuff
	Uri fileUri;
	File photo;
	private static final int MEDIA_TYPE_IMAGE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private final int PIC_CROP = 2;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        yesButton = (Button) findViewById(R.id.yesButton);
        noButton = (Button) findViewById(R.id.noButton);
        cameraButton = (ImageButton) findViewById(R.id.camera);
        searchButton = (ImageButton) findViewById(R.id.profileIcon);
        menuButton = (ImageButton) findViewById(R.id.gears);
        photoProfile= (PhotoProfile) findViewById(R.id.photoProfile);
        menu = (RelativeLayout) findViewById(R.id.menuBarLayout);
        
        //Set max photoProfile height
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size); //Because we can't use return for some reason?
        photoProfile.setMaxHeight((size.y/3)*2); // two thirds of the height of the screen
        
       //cookies
        intent = getIntent(); //Used for the cookie and stuff
        cookie = intent.getStringExtra(CookieHandler.COOKIE);
        Log.v("MainActivity", cookie);
       
        final Context context = getApplicationContext();
        cameraButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				 // create Intent to take a picture and return control to the calling application
			    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
			    photo = getOutputMediaFile(MEDIA_TYPE_IMAGE);
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

			    // start the image capture Intent
			    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			    
			}
		});
        
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
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	
    	//Camera taking photo
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
        	Log.v("Camera", "Received Camera activity");
            if (resultCode == RESULT_OK) {
            		
            	//Switch to crop screen
            	performCrop(fileUri, getApplicationContext());

            	
                //Image working
            } else if (resultCode == RESULT_CANCELED) {
            	Log.v("Camera", "Photo canceled");
            } else {
                // Camera fucked up.
            	Log.v("Camera", "Camera failed!");
            }
        } else {
        	Log.v("MainActivity", "Well something else happened");
        }
    }

    /**
     * Starts a new PhotoEditActivity to crop the photo
     * @param uri
     * @param context
     */
    private void performCrop(Uri uri, Context context) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            bitmap = scaleDownBitmap(bitmap, 500, context);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            byte[] byteArray = stream.toByteArray();

            Log.v("MainActivity", "Compressed image");
            Intent intent = new Intent(context, PhotoEditActivity.class);
            intent.putExtra("photo", byteArray);
            intent.putExtra(CookieHandler.COOKIE, cookie);
            Log.v("MainActivity", "Got here!");
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    private void uploadPhoto(File file) {
    	if (file == null) {
    		Log.v("MainActivityNullCheck", "FILE IS NULL AGAIN DAMMIT");
    	} else { 
    		PostPhotosTask task = new PostPhotosTask(photo, cookie);
    		task.post(); //That simple bitch.
    	}
    }

    /**
     * Handles the popup menu for the corner button
     * @param v
     */
    public void showPopup(View v) {
    	PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        final Context context = this.getApplicationContext();
        popup.setOnMenuItemClickListener(new OnMenuItemClickListener(){
        	 @Override
        	    public boolean onMenuItemClick(MenuItem item) {
        	        switch (item.getItemId()) {
        	            case R.id.logout:
                            /* Turn off auto login */
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor=prefs.edit();
                            editor.clear(); //Wipe the user and pass from the user
                            editor.putBoolean(LoginActivity.AUTO_LOGIN_PREF, false);
                            editor.commit();

                            /* Start new Activity */
        	            	Intent intent = new Intent(context, LoginActivity.class);
                            startActivity(intent);
        	                return true;
        	            case R.id.profile:
                            Intent intentProfile = new Intent(context, ProfileActivity.class);
                            startActivity(intentProfile);
        	                return true;
        	            default:
        	                return false;
        	        }
        	    }
        });
        inflater.inflate(R.menu.popup, popup.getMenu());
        popup.show();
    }
    
   
    
    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
          return Uri.fromFile(getOutputMediaFile(type));
    }
    
    /** Create a File for saving an image or video
     * Also, this was modified from the android tutorials. */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "ModerPhotos"); 
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
        	boolean works = mediaStorageDir.mkdirs();
            if (!works){
                Log.d("ModerCamera", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");
        } else {
        	Log.v("ModerCamera", "No video allowed!");
            return null; //We shouldn't have to worry bout video
        }

        return mediaFile;
    }
   
}