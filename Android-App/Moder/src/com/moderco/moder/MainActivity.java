package com.moderco.moder;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


/*
 * A drag and drop requires a "drag shadow" that represents
 * the object the user is dragging. After that's done and over with
 * The users object will be moved to wherever it's needed.
 */

public class MainActivity extends ActionBarActivity {
	
	//Create a label
	private static final String IMAGEVIEW_TAG = "icon bitmap";
	ImageView imageView; //Needed for the drag shadow

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = new ImageView(this); 
        imageView.setTag(IMAGEVIEW_TAG);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
        
			
			@Override
			public boolean onLongClick(View v) {
				ClipData.Item item = new ClipData.Item( (CharSequence) v.getTag());
				String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
				ClipData dragData = new ClipData((CharSequence)v.getTag(), mimeTypes, item);
				View.DragShadowBuilder myShadow = new MyDragShadowBuilder(imageView); //uses private class
				v.startDrag(dragData, myShadow, null, 0); //Actually creates the drag
				return false; 
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    /* Private class that describes the drag shaodw for the drag and drop
     * Honestly, I just ripped this from the official anndroid tutorials. I'll
     * probably edit it in a bit.
     */
    private static class MyDragShadowBuilder extends View.DragShadowBuilder {

        // The drag shadow image, defined as a drawable thing
        private static Drawable shadow;

            // Defines the constructor for myDragShadowBuilder
            public MyDragShadowBuilder(View v) {

                // Stores the View parameter passed to myDragShadowBuilder.
                super(v);

                // Creates a draggable image that will fill the Canvas provided by the system.
                shadow = new ColorDrawable(Color.LTGRAY);
            }

            // Defines a callback that sends the drag shadow dimensions and touch point back to the
            // system.
            @Override
            public void onProvideShadowMetrics (Point size, Point touch) {
                // Defines local variables
                int width;
				int height;

                // Sets the width of the shadow to half the width of the original View
                width = getView().getWidth() / 2;

                // Sets the height of the shadow to half the height of the original View
                height = getView().getHeight() / 2;

                // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
                // Canvas that the system will provide. As a result, the drag shadow will fill the
                // Canvas.
                shadow.setBounds(0, 0, width, height);

                // Sets the size parameter's width and height values. These get back to the system
                // through the size parameter.
                size.set(width, height);

                // Sets the touch point's position to be in the middle of the drag shadow
                touch.set(width / 2, height / 2);
            }

            // Defines a callback that draws the drag shadow in a Canvas that the system constructs
            // from the dimensions passed in onProvideShadowMetrics().
            @Override
            public void onDrawShadow(Canvas canvas) {

                // Draws the ColorDrawable in the Canvas passed in from the system.
                shadow.draw(canvas);
            }
        }
}
