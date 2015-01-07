package com.moderco.moder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Display;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.loopj.android.http.PersistentCookieStore;
import com.moderco.network.CookieHandler;
import com.moderco.network.PostPhotosTask;
import com.moderco.views.PhotoProfile;


public class MainActivity extends Activity {

	PhotoProfile photoProfile;
	Button yesButton, noButton;
	ImageButton cameraButton, searchButton, menuButton;
	RelativeLayout menu;
	Intent intent;
	
	private String cookie;
	PersistentCookieStore cookieStore;
	private String currentPhotoString = "ModerPhoto23508432j093jfew";
	
	//For camera stuff
	Uri fileUri;
	File photo;
	private static final int MEDIA_TYPE_IMAGE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        yesButton = (Button) findViewById(R.id.yesButton);
        noButton = (Button) findViewById(R.id.noButton);
        cameraButton = (ImageButton) findViewById(R.id.camera);
        searchButton = (ImageButton) findViewById(R.id.search);
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
       
        
        cameraButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 // create Intent to take a picture and return control to the calling application
			    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
    
    public void showPopupMenu(View v){
    	PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, popup.getMenu());
        popup.show();
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	Log.v("Camera", "Received Camera activity");
    	
    	
    	
    	
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            	if (data != null) {
            		Bitmap photoBitmap = (Bitmap) data.getExtras().get("data");
            		File file = new File(currentPhotoString + ".png");
            		if (file.exists()) {
            			file.delete();
            			file = new File(currentPhotoString + ".png"); //Recreate File
            		}
            		
            		try {
						OutputStream out = new FileOutputStream(file);
						photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); //I'm even compressing it!
						out.flush();
						out.close();
						
						uploadPhoto(file);
						
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
            	} else {
            		Log.e("MainActivity", "Camera onActivityResult error! Intent data is null!");
            	}
            	
                //Image working
            } else if (resultCode == RESULT_CANCELED) {
            	Log.v("Camera", "Photo canceled");
            } else {
                // Camera fucked up.
            	Log.v("Camera", "Camera failed!");
            }
        } else {
        	Log.v("Camera", "Well something else happened");
        }
    }
    
    private void uploadPhoto(File file) {
    	if (file == null) {
    		Log.v("MOPROBLEMS", "FILE IS NULL AGAIN DAMMIT");
    	} else { 
    		PostPhotosTask task = new PostPhotosTask(photo, cookie);
    		task.post(); //That simple bitch.
    	}
    }
    
    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
          return Uri.fromFile(getOutputMediaFile(type));
    }
    
    /** Create a File for saving an image or video
     * Also, this was ripped from the android tutorials. */
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
