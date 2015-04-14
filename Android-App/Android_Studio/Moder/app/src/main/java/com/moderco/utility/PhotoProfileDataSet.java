package com.moderco.utility;

import android.graphics.Bitmap;

/**
 * Created by Flaque on 1/30/15.
 */
public class PhotoProfileDataSet {

    private Bitmap bitmap;
    private String ID;
    private String description;

    public PhotoProfileDataSet(Bitmap bit, String id) {
        this.setBitmap(bit);
        this.setID(id);
    }

    PhotoProfileDataSet(Bitmap bit, String id, String description) {
        this.setBitmap(bit);
        this.setID(id);
        this.setDescription(description);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
