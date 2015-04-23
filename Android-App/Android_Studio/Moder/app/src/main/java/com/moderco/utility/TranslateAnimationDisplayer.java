package com.moderco.utility;

import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

/**
 * Created by Ethan on 4/15/2015.
 */

public class TranslateAnimationDisplayer implements BitmapDisplayer {
    private int diplayLength;

    public TranslateAnimationDisplayer(int diplayLength){
        this.diplayLength = diplayLength;
    }

    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {

        animate(imageAware.getWrappedView(), diplayLength);

    }

    public static void animate(View imageView, int durationMillis) {
        if (imageView != null) {
            TranslateAnimation fadeImage = new TranslateAnimation(0, 0,-200,200);
            fadeImage.setDuration(durationMillis);
            fadeImage.setInterpolator(new DecelerateInterpolator());
            imageView.startAnimation(fadeImage);
        }
    }

}
