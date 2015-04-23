package com.moderco.model;

import android.os.AsyncTask;
import android.util.Log;

import com.moderco.utility.RateCardInformation;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Ethan on 4/17/2015.
 */
public class LoadingPhotosAsync extends AsyncTask<BlockingQueue<RateCardInformation>,Void,Void>{


    @Override
    protected Void doInBackground(BlockingQueue<RateCardInformation>... params) {
        BlockingQueue queue = params[0];
        while(queue.size()< 5){

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
