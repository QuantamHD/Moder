package com.moderco.network;

import android.os.AsyncTask;

import com.moderco.utility.JsonReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Flaque on 2/10/15.
 */
public class GetProfileTask extends AsyncTask<Void, Void, JSONObject> {

    String cookie;

    public GetProfileTask(String cookie) {
        this.cookie = cookie;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        HttpResponse response;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet();
        try {
            request.setURI(new URI(URLS.GET_PROFILE_STRING));
            request.addHeader("Cookie", this.cookie);
            response = client.execute(request);
            JSONObject jObject = JsonReader.parse(response, true);

            return jObject;


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
