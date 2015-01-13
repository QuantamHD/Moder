package com.moderco.moder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.edmodo.cropper.CropImageView;
import com.moderco.network.CookieHandler;
import com.moderco.network.PostPhotosTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class PhotoEditActivity extends Activity {

    CropImageView cropImageView; //From Cropper
    String cookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_edit);

        Log.v("PhotoEditActivity", "Started!");

        //Retrieve intent with photo
        byte[] byteArray = getIntent().getByteArrayExtra("photo");
        Bitmap photo = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        cookie = getIntent().getStringExtra(CookieHandler.COOKIE);
        Log.v("PhotoEditActivity", cookie);

        //Configure the mcropview
        cropImageView = (CropImageView) findViewById(R.id.CropImageView);
        cropImageView.setAspectRatio(1, 1);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setImageBitmap(photo); //Set the photo
    }

    public void submit(View v) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        cropImageView.getCroppedImage().compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();


        File outputDir = getApplicationContext().getCacheDir();
        File file = null;
        try {
            file = File.createTempFile("photo", ".png", outputDir);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
        } catch (IOException e) {
            e.printStackTrace();
        }


        uploadPhoto(file);

        switchToMain(null);
    }


    //Borrowed this
    private void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    private void uploadPhoto(File file) {
        if (file == null) {
            Log.v("MainActivityNullCheck", "FILE IS NULL AGAIN DAMMIT");
        } else {
            PostPhotosTask task = new PostPhotosTask(file, cookie);
            task.post(); //That simple bitch.
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_edit, menu);
        return true;
    }

    public void switchToMain(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(CookieHandler.COOKIE, cookie);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
