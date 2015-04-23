package com.moderco.utility;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.net.URI;

/**
 * Created by Ethan on 4/12/2015.
 */
public class ProfileCardInformation {
    private String id;
    private int rateUp =0;
    private int rateDown =0;
    private String description;
    private File localFile;
    private boolean submited = true;

    public ProfileCardInformation() {
        description = "";
        id = null;

    }

    public ProfileCardInformation(String id, int rateUp, int rateDown) {
        this.id = id;
        description = "";
        this.rateUp = rateUp;
        this.rateDown = rateDown;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRateUp() {
        return rateUp;
    }

    public void setRateUp(int rateUp) {
        this.rateUp = rateUp;
    }

    public int getRateDown() {
        return rateDown;
    }

    public void setRateDown(int rateDown) {
        this.rateDown = rateDown;
    }

    public String getURL(){
        return "http://www.moderapp.com/images/"+id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getLocalFile() {
        return localFile;
    }

    public void setLocalFile(File localFile) {
        this.localFile = localFile;
    }

    public Uri getLocalURI(){
        Uri contentUri = Uri.fromFile(localFile);
        return contentUri;
    }

    public int getPercent(){
        if(rateUp+rateDown == 0){
            return 100;
        }
        return (rateUp/(rateUp+rateDown))*100;
    }
    public boolean isSubmited() {
        return submited;
    }

    public void setSubmited(boolean submited) {
        this.submited = submited;
    }
}
