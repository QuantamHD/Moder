package com.moderco.network;


import android.os.AsyncTask;

import com.moderco.utility.JsonReader;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationTask extends AsyncTask<Void, Void, Integer>{

	String email, pwd1, pwd2;
	
	public static final int SUCCESS_CODE = 300;
	
	
	public RegistrationTask(String email, String pwd1, String pwd2) {
		this.email = email;
		this.pwd1 = pwd1;
		this.pwd2 = pwd2;
	}
	
	@Override
	/**
	 * Sends http post to server who then sends back a response (hopefully).
	 */
	protected Integer doInBackground(Void... params) { //We ignore the params because it's an upload
		int code = -1;

		// Setup
		HttpResponse response = null; // Hopefully this shouldn't happen.
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(URLS.REGISTRATION_URL_STRING); // TODO: add url
		

		try {
			// fill in parameters email, pwd1, pwd2
			List<NameValuePair> nameValuePairs = new ArrayList<>(2);
			nameValuePairs.add(new BasicNameValuePair("email", email));
			nameValuePairs.add(new BasicNameValuePair("pwd1", pwd1));
			nameValuePairs.add(new BasicNameValuePair("pwd2", pwd2));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// send data
			response = httpClient.execute(post);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		//Log response
		try {
			return JsonReader.getCode(response, true);
		} catch (IllegalStateException | NullPointerException e) {
			e.printStackTrace();
		}

        return code; //Return the response
	}
	
}
