package com.moderco.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SendPhotosTask extends AsyncTask<File, Void, Integer>{
	
	//codes
	public static final int FILE_DOES_NOT_EXIST = 100;
	public static final int SUCCESS = 300;
	String unique_id_string;
	
	public SendPhotosTask( String cookie) {
		unique_id_string = cookie;
	}
	
	@Override
	protected Integer doInBackground(File... params) {
		File photo = params[0];
		if (photo != null) {
			if (photo.isFile()) {
				try {

					HttpClient httpclient = new DefaultHttpClient();

					HttpPost httppost = new HttpPost(
							URLS.SUBMIT_PHOTO_URL_STRING);
					
					

					InputStreamEntity reqEntity = new InputStreamEntity(
							new FileInputStream(photo), -1);
					reqEntity.setContentType("multipart/form-data; boundary=*****");
					reqEntity.setChunked(false); // Send in multiple parts if
													// needed
					
					httppost.setEntity(reqEntity);
					httppost.addHeader("Cookie", unique_id_string);
					
					HttpResponse response = httpclient.execute(httppost);

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent(), "UTF-8"));
					StringBuilder builder = new StringBuilder();
					for (String line = null; (line = reader.readLine()) != null;) {
						builder.append(line).append("\n");
					}
					Log.d("CameraResponse", builder.toString());

				} catch (Exception e) {
					// show error
				}
			}
		} else {
			Log.d("PROBLEMS", "SHIT IS NULL");
		}
		return 0;
	}

	
}