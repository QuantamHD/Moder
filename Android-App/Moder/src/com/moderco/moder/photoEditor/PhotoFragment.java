package com.moderco.moder.photoEditor;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.moderco.moder.R;

public class PhotoFragment extends Fragment{
	
	 Bitmap photo; // The photo to show
	 ImageView photoView;
	 
	 public PhotoFragment() {
		 
	 }
	 
	 // ----- All methods in this section are in chronological order ----- //
	 
	 @Override
	 public void onAttach(Activity activity) {
		 super.onAttach(activity);
		 Log.v("PhotoFragment", "Attached."); //Log it
		 
		//Decode the photo
	    byte[] byteArray = activity.getIntent().getByteArrayExtra("photo");
	    photo = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
	 }

	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			 Bundle savedInstanceState) {
		View fragmentContentView = inflater.inflate(R.layout.fragment_photo, container, false);
		return fragmentContentView;
	 }

	 @Override
	 public void onStart() {
	     super.onStart();
	     photoView = (ImageView) getView().findViewById(R.id.imageToCrop);
	     photoView.setImageBitmap(photo); //Set the photo
	     
	     /* Handle scrolling/Cropping
	     photoView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				 float curX, curY;
		         float mx = 0, my = 0;
		         
					switch (event.getAction()) {

		                case MotionEvent.ACTION_DOWN:
		                    mx = event.getX();
		                    my = event.getY();
		                    break;
		                case MotionEvent.ACTION_MOVE:
		                    curX = event.getX();
		                    curY = event.getY();
		                    photoView.scrollBy((int) (mx - curX), (int) (my - curY));
		                    mx = curX;
		                    my = curY;
		                    break;
		                case MotionEvent.ACTION_UP:
		                    curX = event.getX();
		                    curY = event.getY();
		                    photoView.scrollBy((int) (mx - curX), (int) (my - curY));
		                    break;
		            }

		            return true;
			}
		}); */
	 }
	 
	 
	 // ----- Chronological order section end ----- //
	 
}
