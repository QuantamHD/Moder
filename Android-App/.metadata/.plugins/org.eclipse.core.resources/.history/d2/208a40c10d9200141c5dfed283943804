package com.moderco.network;

import java.io.File;
import java.net.HttpURLConnection;

import android.os.AsyncTask;

public class SendPhotosTask extends AsyncTask<Void, Void, Integer>{
	
	//codes
	public static final int FILE_DOES_NOT_EXIST = 100;
	
	File photo;
	
	SendPhotosTask(File file) {
		photo = file;
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		int code = -1;
		HttpURLConnection connection = null;
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		
		if (!photo.isFile()) {
			code = FILE_DOES_NOT_EXIST;
		} else {
			
			
		}

		
		return null;
	}

	
}
