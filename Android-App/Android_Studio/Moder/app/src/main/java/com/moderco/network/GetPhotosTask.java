/**
 * Grabs photos from a URL. Currently can only do one photo at a time.
 * @author Evan
 * @date December 26th, 2014
 */

package com.moderco.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class GetPhotosTask extends AsyncTask<String, Void, Bitmap>{
	
	Bitmap returnedBitmap;
	
	public GetPhotosTask() {
	}

	/**
	 * @pre this.execute() has been called
	 * @return the bitmap
	 */
	public Bitmap getBitmap()
	{
		return returnedBitmap;
	}
	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap = null;
		try {
			//currently just test pics
			bitmap = BitmapFactory.decodeStream((InputStream)new URL(params[0]).getContent());
			returnedBitmap = bitmap;
			return bitmap;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap; //Things really didn't go smoothly. 
	}

}