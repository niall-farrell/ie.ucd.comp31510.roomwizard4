package com.example.projectv1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.TextView;

public class DisplayDetails extends Activity {

	Timeout timer = new Timeout(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_details);
		
		Typeface qs1=Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.ttf");
		Typeface qs2=Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.ttf");
		 
		timer.startTimer();
		
		TextView content = new TextView(this);
		content = (TextView) findViewById(R.id.detailsContent);

		content.setTypeface(qs2);
		
		DigitalClock clock = new DigitalClock(this);
		clock = (DigitalClock)findViewById(R.id.digitalClock1);		 

		clock.setTypeface(qs2);
		
		Button back = new Button (this);
		back = (Button)findViewById(R.id.back_from_details);

		back.setTypeface(qs1);
		
		Bundle submitted = getIntent().getExtras();
		String uid = submitted.getString("uid");
		String display;
		
		if(!uid.equals("")){		// display booking info unless uid == null
			
			String summary = submitted.getString("summary");
			String start   = submitted.getString("start");
			String end     = submitted.getString("end");
			String url     = submitted.getString("url");
			String org     = submitted.getString("organizer");
			
			Calendar start_cal = iCalToTimeToday(start);
			Calendar end_cal = iCalToTimeToday(end);
			
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);   
			String room = preferences.getString("room", "B0.0X");
			
			SimpleDateFormat timeFormat = new SimpleDateFormat("k:mm a");

			display = summary + " \n" +
					timeFormat.format(start_cal.getTime()) + " - " + timeFormat.format(end_cal.getTime()) + " \n" +							
					org + "\n" + "Room " + room;
			content.setText(display);
		}
		else{
			 
			content.setText(content.getResources().getString(R.string.roomFreeAtThatTime));
		}
		
	}

	public static Calendar iCalToTimeToday(String iCalText) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
		Date date = null;
		
		try {
			date = format.parse(iCalText);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block  
			e1.printStackTrace();  
		}
		
		// Set calendar to given time
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		// Set calendar to today's date
		Calendar now = Calendar.getInstance();
		cal.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
		
		return cal;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_display_details, menu);
		return true;
	}
	public void buttonClick(View v)
	{
		switch(v.getId())
		{
		case R.id.back_from_details:
			finish();
			break;
		}
	}
}
