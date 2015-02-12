package com.moderco.network;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.moderco.utility.PhotoProfileDataSet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

/**
 * Created by Flaque on 1/30/15.
 */
public class FindPhotoTask extends AsyncTask<Queue<PhotoProfileDataSet>, Void, Void>{

    private String cookie;
    private Context context;
    private int maxPhotos;
    static final public String COPA_RESULT = "com.controlj.copame.backend.COPAService.REQUEST_PROCESSED";
    static final public String COPA_MESSAGE = "com.moderco.network.Message";

    public FindPhotoTask(Context context, String cookie, int num) {
        this.cookie = cookie;
        this.context = context;
        this.maxPhotos = num;
    }

    protected Void doInBackground(Queue<PhotoProfileDataSet>... params) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        ArrayList<String> urls = new ArrayList<>(Arrays.asList(prefs.getString(GetUrlsTask.urlPrefCode, "ERROR")
                .split(GetUrlsTask.urlRegex)));


        for (int i = 0; (i < maxPhotos)&&(i < urls.size()); i++) {
            try {


                    //currently just test pics
                    Bitmap bitmap;
                    HttpGet httpRequest = new HttpGet(URI.create(URLS.MAIN_FEED_URL_STRING + "/" + urls.get(i)));
                    Log.v("FindPhotos", URLS.MAIN_FEED_URL_STRING + urls.get(i));
                    HttpClient httpclient = new DefaultHttpClient();
                    httpRequest.addHeader("Cookie", cookie);
                    HttpResponse resp = httpclient.execute(httpRequest);
                    HttpEntity entity = resp.getEntity();
                    BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
                    bitmap = BitmapFactory.decodeStream(bufHttpEntity.getContent());
                    httpRequest.abort();

                    PhotoProfileDataSet photo = new PhotoProfileDataSet(bitmap, urls.get(i));

                    if (bitmap == null) Log.v("FindPhotos", "BITMAP DOESN'T EXIST");
                    else params[0].add(photo);

                    //Remove the first one.
                    urls.remove(0);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String current = "";
        for (int i = 0; i < urls.size(); i++) {
            current += (urls.get(i) + GetUrlsTask.urlRegex);
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(GetUrlsTask.urlPrefCode, current);
        editor.commit();

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.v("Something", "Happening");
        LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(this.context);
        Intent intent = new Intent(COPA_RESULT);
        broadcaster.sendBroadcast(intent);
    }


}
