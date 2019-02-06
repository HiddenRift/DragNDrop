package com.delaroystudios.dragndrop;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.skholingua.android.dragndrop_relativelayout.R;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

	private ImageView img;
	private ViewGroup rootLayout;
	private int _xDelta;
	private int _yDelta;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rootLayout = (ViewGroup) findViewById(R.id.view_root);
		img = (ImageView) rootLayout.findViewById(R.id.imageView);

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
		img.setLayoutParams(layoutParams);
		img.setOnTouchListener(new ChoiceTouchListener());
	}

	
	
	private final class ChoiceTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent event) {
			//get raw x and y of event
			final int X = (int) event.getRawX();
			final int Y = (int) event.getRawY();

			// get action type and use bitwise to mask it (remove unwanted crap)
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
				//action down is when touch first recognised
			case MotionEvent.ACTION_DOWN:
				RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

				//calculate position of x and y relative to top margin and left margin of layout
				_xDelta = X - lParams.leftMargin;
				_yDelta = Y - lParams.topMargin;
                Log.d("ACTIONDOWN", "X: " + X);
                Log.d("ACTIONDOWN", "Y: " + Y);
                Log.d("ACTIONDOWN", "leftMgn: " + lParams.leftMargin);
                Log.d("ACTIONDOWN", "topMgn: " + lParams.topMargin);
                Log.d("ACTIONDOWN", "X_Delt: " + _xDelta);
                Log.d("ACTIONDOWN", "Y_Delt: " + _yDelta);
				break;
			case MotionEvent.ACTION_UP:
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				break;
			case MotionEvent.ACTION_POINTER_UP:
				break;
			case MotionEvent.ACTION_MOVE:
				// retrieve layout params from view

                Log.d("ACTIONMove", "begin out...");
                Log.d("ACTIONMove", "X: " + (X-_xDelta));
                Log.d("ACTIONMove", "Y: " + (Y-_yDelta));
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

				//set layoutparams left and top margins to new values relative to x

                //                    Previous value of...
                // so overall its X - (X-leftMargin)
                // from experimenting this may have something to do with maintaining a grab of the
                // object by adjusting the end position relative to part touched???
				layoutParams.leftMargin = X - _xDelta;
				layoutParams.topMargin = Y - _yDelta;

				// not sure why 250 every time, perhaps size of image? (though looks too small)
                //having this uncommented will prevent the icon from becoming smaller as it nears edge to prevent it falling off
				layoutParams.rightMargin = -250;
				layoutParams.bottomMargin = -250;


				// write new layout params back to the file
				view.setLayoutParams(layoutParams);
				break;
			}
			// invalidate layout at end to cause redraw
			rootLayout.invalidate();

			// return true here, just because i guess???
			return true;
		}
	}

}



