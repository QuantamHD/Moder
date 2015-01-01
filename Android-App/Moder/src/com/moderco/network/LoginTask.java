package com.moderco.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class LoginTask extends AsyncTask<Void, Void, Integer>{
	
	String email, pwd;
	public static final int SUCCESS_CODE = 300;
	public static final int UNKNOWN_ERROR_CODE = 200;
	public static final int INFO_MISSING_CODE = 100;
	public static final int CONNECTION_FAILED_CODE = 140;
	public Cookie cookie = null;

	public LoginTask(String email, String pwd) {
		this.email = email;
		this.pwd = pwd;
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		int code = -1;
		// Setup
		HttpResponse response = null; // Hopefully this shouldn't happen.
		HttpClient httpClient = new DefaultHttpClient();
		//Cookie time
		
		HttpPost post = new HttpPost(URLS.LOGIN_URL_STRING); // TODO: add url

		try {
			// fill in parameters email, pwd1
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("email", email));
			nameValuePairs.add(new BasicNameValuePair("pwd", pwd));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// send data
			response = httpClient.execute(post);
			
			
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent(), "UTF-8"));
				StringBuilder builder = new StringBuilder();
				for (String line = null; (line = reader.readLine()) != null;) {
					builder.append(line).append("\n");
				}
				
				Log.d("LoginResponse", builder.toString());
				//Now let's parse some JSON! 
				try {
					JSONObject jObject = new JSONObject(builder.toString());
					code = jObject.getInt("ResponseCode"); //Assigns code
					
					//Only get a cookie if all goes well (other wise it causes a nasty crash) 
					if (code == SUCCESS_CODE) {
						//Cookie time
						CookieStore store = ((DefaultHttpClient) httpClient).getCookieStore();
						List<Cookie> cookies = store.getCookies();
						cookie = cookies.get(0); //There should only be one.
						Log.v("Cookie Time", "Received cookie: " + cookie.getName());
					}
					
					return code; //Codes defined above
					
				/* Catch all the things! */
				} catch (JSONException e) { 
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return code; // Code = -1 here. That's kinda bad. it should crash b4 it gets here
	}

	
}
