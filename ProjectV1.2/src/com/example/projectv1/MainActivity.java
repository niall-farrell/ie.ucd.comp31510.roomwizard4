package com.example.projectv1;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{

	Boolean occupied = true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.myLayout);
		TextView content = new TextView(this);
		content = (TextView) findViewById(R.id.content);
//		content.setGravity(Gravity.CENTER_HORIZONTAL);
		
		if (occupied)
		{
			content.setText("Organizer's name \n" +
					"Event name \n" +
					"Booking time (from - to) \n" +
					"Current date and time \n" +
					"Room number \n");
			mainLayout.setBackgroundColor(0xCCCC0000);
		} else
		{
			content.setText("This room is currently free");
			mainLayout.setBackgroundColor(Color.BLUE);
		}
		
		// Ruler Listener
		ImageView rulerView = (ImageView)findViewById(R.id.ruler);
		
		OnTouchListener rulerListener = new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				float positionX = event.getX();
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					float rulerPosition = (float) (((positionX - 15) / 770) * 10.5);
					Toast.makeText(getApplicationContext(),
							"You clicked at: " + rulerPosition, Toast.LENGTH_SHORT).show();
					
					return true;
				}
				return false;
			}
		};
		
		rulerView.setOnTouchListener(rulerListener);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	
	// is called from xml when button is clicked
	public void openMenu(View v)
	{
		Intent openMenu = new Intent("com.project.MENU");
		startActivity(openMenu);
	}
	
	public static String eventActionToString(int eventAction) {
	    switch (eventAction) {
	        case MotionEvent.ACTION_CANCEL: return "Cancel";
	        case MotionEvent.ACTION_DOWN: return "Down";
	        case MotionEvent.ACTION_MOVE: return "Move";
	        case MotionEvent.ACTION_OUTSIDE: return "Outside";
	        case MotionEvent.ACTION_UP: return "Up";
	        case MotionEvent.ACTION_POINTER_DOWN: return "Pointer Down";
	        case MotionEvent.ACTION_POINTER_UP: return "Pointer Up";
	    }
	    return "";
	}
}
