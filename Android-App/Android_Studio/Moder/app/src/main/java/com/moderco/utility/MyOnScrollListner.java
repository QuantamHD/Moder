package com.moderco.utility;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.AbsListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

/**
 * Created by Ethan on 4/15/2015.
 */
public class MyOnScrollListner extends RecyclerView.OnScrollListener {

    private ImageLoader imageLoader;
    private boolean pauseOnScroll;
    private boolean pauseOnFling;

    public MyOnScrollListner(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {
        super();
        this.imageLoader = imageLoader;
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if(newState == 0){
            imageLoader.resume();

        }
        if(newState == 2){
            imageLoader.pause();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(Math.abs(dy)<15){
            imageLoader.resume();
        }
    }
}
