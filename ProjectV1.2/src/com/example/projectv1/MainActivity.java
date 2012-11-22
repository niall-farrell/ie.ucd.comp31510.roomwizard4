package com.example.projectv1;
// this is just a test comment  test again
import java.util.Calendar;
import java.util.Locale;

import com.example.projectv1.timeline.TimelineImageView;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
	Calendar c = Calendar.getInstance();
	String language;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.myLayout);
		TextView content = new TextView(this);
		content = (TextView) findViewById(R.id.content);
//		content.setGravity(Gravity.CENTER_HORIZONTAL);
		
		// get system date and time
		//
		// this probably isn't a very good system. needs more work
		//
		int seconds = c.get(Calendar.SECOND);
		int mins = c.get(Calendar.MINUTE);
		int hours = c.get(Calendar.HOUR_OF_DAY);
		int date = c.get(Calendar.DATE);
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);
		
		if (occupied)
		{
			content.setText("Organizer's name \n" +
					"Event name \n" +
					"Booking time (from - to) \n" +
					"Current date: " + date + "/" + month + "/" + year + ". Time: " + 
					hours+":"+mins+":"+seconds + " \n" +
					"Room number \n");
			// mainLayout.setBackgroundColor(0xCCCC0000);
		} else
		{
			content.setText("This room is currently free");
			// mainLayout.setBackgroundColor(Color.BLUE);
		}
		
		
		// Timeline Listener
		ImageView timelineView = (ImageView)findViewById(R.id.timeline);
		timelineView.setBackgroundColor(Color.GREEN);
		
		OnTouchListener timelineListener = new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				float positionX = event.getX();
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					// TODO These are just test values
					int[][] time_array = new int[][] {{50, 150},{200, 300},{600,700}};
					
					boolean occupied = false;
					for (int i = 0; i<3; i++) {
						if (time_array[i][0] < positionX && time_array[i][1] > positionX)
						{
							occupied = true;
						}
					}
					
					String message;
					if (occupied) {
						message = "The room is occupied";
					}
					else {
						message = "The room is available";
					}
					
					
					Toast.makeText(getApplicationContext(),
							"You clicked at: " + positionX + "\n" + message, Toast.LENGTH_SHORT).show();
					
					return true;
				}
				return false;
			}
		};
		
		timelineView.setOnTouchListener(timelineListener);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	
	// is called from XML when button is clicked
	public void buttonClick(View v)
	{
		switch(v.getId())
		{
		case R.id.menu:
			Intent openMenu = new Intent("com.project.MENU");
			startActivity(openMenu);
			break;
			
		case R.id.english:
			
			break;
			
		case R.id.gaeilge:
			/*
			language="ga";
			Locale loc2 = new Locale(language);
			Locale.setDefault(loc2);
			Configuration config = new Configuration();
			config.locale =loc2;
			getBaseContext().getResources().updateConfiguration(config,
					getBaseContext().getResources().getDisplayMetrics());
			this.setContentView(R.layout.activity_main);
			*/
			break;
		}

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
