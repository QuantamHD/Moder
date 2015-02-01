package com.moderco.network;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;

public class PostPhotosTask {
	
	private RequestParams params;
	private AsyncHttpClient client;
	
	public PostPhotosTask(File file, String cookie) {
		
		//File
		params = new RequestParams();
		client = new AsyncHttpClient();
		client.addHeader("Cookie", cookie);
		client.setTimeout(5000000);
		try{
			params.put("file", file);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void post() {
		client.post(URLS.SUBMIT_PHOTO_URL_STRING, params,  new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int code, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				Log.e("PostPhotosTask", "Post Failure code: " + Integer.toString(code));
			}

			@Override
			public void onSuccess(int code, Header[] arg1, byte[] message) {
				Log.v("PostPhotosTask", "Post success! message: " + new String(message));
			}
			
		});
	}
}
