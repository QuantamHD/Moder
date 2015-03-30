package com.moderco.network;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.moderco.utility.JsonReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoginTask extends AsyncTask<Void, Void, Integer>{
	
	private String email, pwd;
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
        HttpParams httpParams = new BasicHttpParams();
        httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpConnectionParams.setTcpNoDelay(httpParams, true);
		HttpResponse response; // Hopefully this shouldn't happen.
		HttpClient httpClient = new DefaultHttpClient(httpParams);

        long time = System.nanoTime();
        HttpPost post = new HttpPost(URLS.LOGIN_URL_STRING);

		try {
			// fill in parameters email, pwd1
			List<NameValuePair> nameValuePairs = new ArrayList<>(2);
			nameValuePairs.add(new BasicNameValuePair("email", email));
			nameValuePairs.add(new BasicNameValuePair("pwd", pwd));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// send data
			response = httpClient.execute(post);
			

			code = JsonReader.getCode(response, true);
					
			//Only get a cookie if all goes well (other wise it causes a nasty crash)
			if (code == SUCCESS_CODE) {
						//Cookie time
						CookieStore store = ((DefaultHttpClient) httpClient).getCookieStore();
						List<Cookie> cookies = store.getCookies();
						cookie = cookies.get(1); //There should only be one.
						Log.v("Cookie Time", "Received cookie: " + cookie.getValue());
			}
            Log.w("LoginTask", "Completed at " + (System.nanoTime() - time));

            return code; //Codes defined above
					

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        Log.w("LoginTask", "Completed at bottom " + System.currentTimeMillis());

        return code; // Code = -1 here. That's kinda bad. it should crash b4 it gets here
	}

	
}
