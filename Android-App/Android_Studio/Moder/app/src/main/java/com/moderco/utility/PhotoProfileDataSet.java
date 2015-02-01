package com.moderco.utility;

import android.graphics.Bitmap;

/**
 * Created by Flaque on 1/30/15.
 */
public class PhotoProfileDataSet {

    public Bitmap bitmap;
    public String ID;
    public String description;

    public PhotoProfileDataSet(Bitmap bit, String id) {
        this.bitmap = bit;
        this.ID = id;
    }

    PhotoProfileDataSet(Bitmap bit, String id, String description) {
        this.bitmap = bit;
        this.ID = id;
        this.description = description;
    }

}
