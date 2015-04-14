package com.moderco.network;

import android.net.http.AndroidHttpClient;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ethan on 2/13/2015.
 */
public class ThreadedLogin implements Runnable{

    public boolean isDone = false;
    public int responseCode = -1;
    private String email;
    private String pwd;

    public ThreadedLogin(String email, String pwd){
        this.email = email;
        this.pwd = pwd;
    }

    @Override
    public void run() {

        long time = System.nanoTime();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(URLS.LOGIN_URL_STRING);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-length", "0");
            urlConnection.setUseCaches(false);
            urlConnection.setAllowUserInteraction(false);
            urlConnection.setRequestProperty("email",email);
            urlConnection.setRequestProperty("pwd",pwd);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            Log.w("ThreadedLogin", urlConnection.getHeaderFields().toString());

        } catch (Exception e) {
            Log.w("ThreadedLogin", e.toString());
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }

        Log.w("ThreadedLogin","Completed at " + (System.nanoTime() - time));
        isDone = true;
    }



}













