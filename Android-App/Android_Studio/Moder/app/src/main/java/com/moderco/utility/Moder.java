package com.moderco.utility;

import android.app.Activity;

import com.loopj.android.http.AsyncHttpClient;
import com.moderco.network.NutraBaseImageDecoder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by Ethan on 3/15/2015.
 */
public class Moder {
    public static AsyncHttpClient client;
    private static ImageLoader imageLoader;
    private static boolean imageLoaderinit =false;

    public static void setupImageLoader(Activity activity){
        if(!imageLoaderinit){
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(true).displayer(new FadeInBitmapDisplayer(380)).build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(activity).defaultDisplayImageOptions(defaultOptions).build();
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);
            imageLoaderinit =true;
        }
    }

    public static ImageLoader getImageLoader(){
        return imageLoader;
    }


    public static AsyncHttpClient getClient() {
        return client;
    }

    public static void setClient(AsyncHttpClient client) {
        Moder.client = client;
    }
}
