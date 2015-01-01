/*
 * Handles the PhotoProfile as custom view. Mostly I put
 * this in here to get the photo to take up the whole width
 * of the screen while keeping the aspect ratio.
 * 
 * @author: Evan Conrad
 * @date: Friday, December 19th, 2014
 */

package com.moderco.views;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.moderco.network.GetPhotosTask;
import com.moderco.network.URLS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("NewApi")
public class PhotoProfile extends ImageView{
	
	Drawable photo; //TODO: Find a way to get the photo without taking it in as a parameter

	public PhotoProfile(Context context) {
		super(context);
		
		/* Some methods are deprecated, so I used a little check
		 * to only use if it's in the correct version. Hence
		 * the warnings being suppressed. 
		 */
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			setBackgroundDrawable(this.photo); //This is deprecated as of v16
		} else {
			setBackground(this.photo); //This is new
		}
	}
	
	public PhotoProfile(Context context, AttributeSet attrs) {
		super(context, attrs);
		/* Some methods are deprecated, so I used a little check
		 * to only use if it's in the correct version. Hence
		 * the warnings being suppressed. 
		 *
		 */
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			setBackgroundDrawable(this.photo); //This is deprecated as of v16
		} else {
			setBackground(this.photo); //This is new
		}
	}
	
	/**
	 * Loads the photo from the server and assigns it to the photo TODO: Finish this
	 */
	public void changePhoto() {
		Bitmap bitmap = null;
		GetPhotosTask task = new GetPhotosTask();
		task.execute(URLS.MAIN_FEED_URL_STRING);
		bitmap = task.getBitmap();
		setImageBitmap(bitmap);
	}
	
	/**
	 * Resizes the photo while keeping the aspect ratio
	 *
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = width * this.photo.getIntrinsicHeight() / photo.getIntrinsicWidth();
		setMeasuredDimension(width, height);
	}
	*/
	

}
