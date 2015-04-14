package com.moderco.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;

/**
 * Created by Flaque on 2/12/15.
 */
public class GetSinglePhotoTask extends AsyncTask<String, Void, Void> {

    String cookie;

    public GetSinglePhotoTask(String cookie) {
        this.cookie = cookie;
    }

    @Override
    protected Void doInBackground(String... params) {
        Bitmap bitmap;
        HttpGet httpRequest = new HttpGet(URI.create(URLS.MAIN_FEED_URL_STRING + "/" + params[0]));
        Log.v("getSinglePhoto", URLS.MAIN_FEED_URL_STRING + params[0]);
        HttpClient httpclient = new DefaultHttpClient();
        httpRequest.addHeader("Cookie", cookie);
        HttpResponse resp = null;
        try {
            resp = httpclient.execute(httpRequest);
            HttpEntity entity = resp.getEntity();
            BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
            bitmap = BitmapFactory.decodeStream(bufHttpEntity.getContent());
            httpRequest.abort();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
