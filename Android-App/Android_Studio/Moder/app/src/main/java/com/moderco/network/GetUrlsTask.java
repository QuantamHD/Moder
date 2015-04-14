package com.moderco.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.moderco.utility.JsonReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Flaque on 1/27/15.
 */
public class GetUrlsTask extends AsyncTask <Void, Void, Integer> {


    private final int URL_CACHE_NUMBER = 3;
    public static final int SUCCESS_CODE = 300;
    public static final int NO_MORE_PHOTOS_CODE = 281;
    private final String photoID = "PhotoID";
    private Context context;
    private String cookie;
    public static final String urlPrefCode = "com.moderco.moder.urls";
    public static final String urlRegex = "~!";

    //Constructor for access to multiple inputs
    public GetUrlsTask(Context context, String cookie) {
        this.context = context;
        this.cookie = cookie;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        int code = -1;
        HttpResponse response;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet();

        //Get multiple Urls
        for (int i = 0; i < URL_CACHE_NUMBER; i++) {
            try {
                request.setURI(new URI(URLS.GET_PHOTO_RATE_STRING));
                request.addHeader("Cookie", this.cookie);
                response = client.execute(request);
                JSONObject jObject = JsonReader.parse(response, true);
                code = jObject.getInt("ResponseCode"); //Assigns code

                //Check for success!
                if (code == SUCCESS_CODE) storeInfo(jObject);
                else if (code == NO_MORE_PHOTOS_CODE) return code;
                else return code;

            } catch (URISyntaxException | JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return code;
    }


    //Helper method stores all the urls
    private void storeInfo(JSONObject jObject) {
        try {
            //Add string to response list
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String current = prefs.getString(urlPrefCode, ""); //Default to nothing
            current += ( jObject.getString(photoID) + urlRegex ); //Add the new one
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(urlPrefCode, current);
            editor.commit();

            String[] strings = prefs.getString(urlPrefCode, "ERROR")
                    .split(urlRegex);
            for (String string : strings) {
                Log.v("GetURlsTask", "PhotoIDs:" + string);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
