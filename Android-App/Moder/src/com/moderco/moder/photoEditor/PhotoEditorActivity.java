package com.moderco.moder.photoEditor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.moderco.moder.R;
import com.moderco.network.CookieHandler;

public class PhotoEditorActivity extends FragmentActivity{
	
	Bitmap photo;
	String cookie;

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Get the cookie
        cookie = getIntent().getStringExtra(CookieHandler.COOKIE);
        setContentView(R.layout.activity_photo_editor);
        
	}
}
