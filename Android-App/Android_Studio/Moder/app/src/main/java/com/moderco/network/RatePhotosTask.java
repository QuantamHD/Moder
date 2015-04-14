package com.moderco.network;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 1/26/15.
 */
class RatePhotosTask extends AsyncTask<Boolean, Void, Integer> {

    private final int SUCCESS_CODE = 300;
    private String unique_id_string = "ERROR FROM EVAN";
    private String photoID;

    public RatePhotosTask(String cookie, String photoID) {
        unique_id_string = cookie;
        this.photoID = photoID;
    }

    @Override
    protected Integer doInBackground(Boolean... params) {
        int code = -1;

        HttpResponse response = null; // Hopefully this shouldn't happen.
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(URLS.LOGIN_URL_STRING);

        try {

            //Attach parameters
            List<NameValuePair> nameValuePairs = new ArrayList<>(2);
            nameValuePairs.add(new BasicNameValuePair("photoID", photoID));
            if (params[0])
                nameValuePairs.add(new BasicNameValuePair("choice", "yes"));
            else
                nameValuePairs.add(new BasicNameValuePair("choice", "no"));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            post.addHeader("Cookie", unique_id_string); //Don't forget the cookie!

            //Send post.
            response = httpClient.execute(post);

            //Get response
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String line = null; (line = reader.readLine()) != null;) {
                builder.append(line).append("\n");
            }

            //Log response
            Log.d("RatePhotosResponse", builder.toString());

            //Now let's parse some JSON!
            JSONObject jObject = new JSONObject(builder.toString());
            code = jObject.getInt("ResponseCode"); //Assigns code

            return code;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return code;
    }
}
