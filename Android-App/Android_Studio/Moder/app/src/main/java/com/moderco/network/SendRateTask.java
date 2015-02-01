package com.moderco.network;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flaque on 1/31/15.
 */
public class SendRateTask extends AsyncTask<Boolean, Void, Integer> {

    public static final int SUCCESS = 300;

    String photoID, cookie;

    public SendRateTask(String photoID, String cookie) {
        this.photoID = photoID;
        this.cookie = cookie;
    }

    @Override
    protected Integer doInBackground(Boolean... params) {
        int code = -1;

        HttpResponse response = null;
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URLS.SEND_RATE_PHOTO_STRING);
        post.addHeader("Cookie", cookie);

        List<NameValuePair> nameValuePairs = new ArrayList<>(2);
        nameValuePairs.add(new BasicNameValuePair("photoID", photoID));
        if (params[0]) nameValuePairs.add(new BasicNameValuePair("choice", "yes"));
        else nameValuePairs.add(new BasicNameValuePair("choice", "no"));

        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = client.execute(post);

            //Parse the JSON
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String line = null; (line = reader.readLine()) != null; ) {
                builder.append(line).append("\n");
            }
            Log.v("GetUrlsTask", "Response: " + builder.toString()); //Log the response

            JSONObject jObject = new JSONObject(builder.toString());
            code = jObject.getInt("ResponseCode"); //Assigns code

            //Check for success
            return code;

        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }
}
