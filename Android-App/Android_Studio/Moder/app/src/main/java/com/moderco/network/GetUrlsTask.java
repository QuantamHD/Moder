package com.moderco.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Flaque on 1/27/15.
 */
public class GetUrlsTask extends AsyncTask <Void, Void, Integer> {


    private final int URL_CACHE_NUMBER = 10;
    public static final int SUCCESS_CODE = 300;
    public static final int NO_MORE_PHOTOS_CODE = 281;
    private final String photoID = "PhotoID";
    private Context context;
    private String cookie;
    public static final String urlPrefCode = "com.moderco.moder.urls";
    public static final String urlRegex = "~!";

    public GetUrlsTask(Context context, String cookie) {
        this.context = context;
        this.cookie = cookie;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        int code = -1;
        HttpResponse response = null;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        //Get multiple Urls
        for (int i = 0; i < URL_CACHE_NUMBER; i++) {
            try {
                request.setURI(new URI(URLS.GET_PHOTO_RATE_STRING));
                request.addHeader("Cookie", this.cookie);
                response = client.execute(request);

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

                //Check for success!
                if (code == SUCCESS_CODE) {
                    //Add string to response list
                    String current = prefs.getString(urlPrefCode, ""); //Default to nothing
                    current += ( jObject.getString(photoID) + urlRegex ); //Add the new one
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(urlPrefCode, current);
                    editor.commit();

                    String[] strings = prefs.getString(urlPrefCode, "ERROR")
                            .split(urlRegex);
                    for (int c = 0; c < strings.length; c++ ) {
                        Log.v("GetURlsTask", "PhotoIDs:" + strings[c]);
                    }

                } else if (code == NO_MORE_PHOTOS_CODE) {
                    return code;
                }

            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return code;
    }
}
