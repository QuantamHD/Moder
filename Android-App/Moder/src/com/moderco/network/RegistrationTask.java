package com.moderco.network;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class RegistrationTask extends AsyncTask<Void, Void, HttpResponse>{

	String email, pwd1, pwd2;
	
	public RegistrationTask(String email, String pwd1, String pwd2) {
		this.email = email;
		this.pwd1 = pwd1;
		this.pwd2 = pwd2;
	}
	
	@Override
	/**
	 * Sends http post to server who then sends back a response (hopefully).
	 */
	protected HttpResponse doInBackground(Void... params) { //We ignore the params because it's an upload

		// Setup
		HttpResponse response = null; // Hopefully this shouldn't happen.
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(URLS.REGISTRATION_URL_STRING); // TODO: add url

		try {
			// fill in parameters email, pwd1, pwd2
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("email", email));
			nameValuePairs.add(new BasicNameValuePair("pwd1", pwd1));
			nameValuePairs.add(new BasicNameValuePair("pwd2", pwd2));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// send data
			response = httpClient.execute(post);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return response;
	}
	

	
}
