package com.example.projectv1;
// this is just a test comment  test again
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import com.example.projectv1.timeline.TimelineImageView;
import com.example.projectv1.ClassBooking;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
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

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.util.Calendars;
import net.fortuna.ical4j.util.CompatibilityHints;

public class MainActivity extends Activity{
	// Create our linkedlist of class bookings
	ArrayList<ClassBooking> cb = new ArrayList<ClassBooking>();
	
	Boolean occupied = true;
	Calendar c = Calendar.getInstance();
	String language;
	String[] times = {"a","b","c","d", "e", "f", "g"};
	FiveMinRefresh fiveMin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			cb = getClassBooking();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.myLayout);
		TextView content = new TextView(this);
		content = (TextView) findViewById(R.id.content);
//		content.setGravity(Gravity.CENTER_HORIZONTAL);
		
		content.setText("start");
		fiveMin= new FiveMinRefresh(times, content);	
		fiveMin.execute();
/*
		int date = c.get(Calendar.DATE);
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);
		
		if (occupied)
		{
			content.setText("Organizer's name \n" +
					"Event name \n" +
					"Booking time (from - to) \n" +
					"Current date: " + date + "/" + month + "/" + year + ".\n" +
					"Room number \n");
			// mainLayout.setBackgroundColor(0xCCCC0000);
		} else
		{
			content.setText("This room is currently free");
			// mainLayout.setBackgroundColor(Color.BLUE);
		}
*/		
		
		
		// Timeline Listener
		final TimelineImageView timelineView = (TimelineImageView)findViewById(R.id.timeline);
		timelineView.setTimes(cb);
		timelineView.invalidate(); // Redraw timelineView
		timelineView.setBackgroundColor(0xFFb0d4e8);
		
		OnTouchListener timelineListener = new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				float positionX = event.getX();
				int width = timelineView.getWidth();
				
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
			    	Map<String, Integer[]> draw_map = TimelineImageView.createDrawMap(cb, width);
					
					boolean occupied = false;
					String class_id = "";
					for (Map.Entry<String, Integer[]> entry : draw_map.entrySet()) {
						if (entry.getValue()[0] < positionX && entry.getValue()[1] > positionX) {
							occupied = true;
							class_id = entry.getKey();
						}
					}
					
					String message;
					if (occupied) {
						message = "The room is occupied - class UID " + class_id;
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

	public void setLanguage(String lang){
		Resources standardResources = getBaseContext().getResources();
		AssetManager assets = standardResources.getAssets();
		DisplayMetrics metrics = standardResources.getDisplayMetrics();
		Configuration config = new Configuration(standardResources.getConfiguration());
		config.locale = new Locale(lang);
		Resources defaultResources = new Resources(assets, metrics, config);
		
		
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
			
			setLanguage("en");
			
			break;
			
		case R.id.gaeilge:
			
			setLanguage("ga");

			
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
	
	//Calendar calendar = Calendars.load(new URL("http://ical4j.cvs.sourceforge.net/viewvc/*checkout*/ical4j/iCal4j/etc/samples/valid/Australian32Holidays.ics"));
		public  java.util.ArrayList<ClassBooking> getClassBooking() throws IOException, ParserException {
			String uid = "";
			String summary = "";
			String st = "";
			String et = "";
			String url = "";
			URL add = null; 
			try {
				add = new URL("http://www.chartspms.com/android/calendar.ics");
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
			CalendarBuilder builder = new CalendarBuilder();
			//CalendarBuilder builder = new CalendarBuilder();
	    	
				calendar = builder.build(add.openStream());
		
	    	if(calendar!=null)
	    	{

			// Iterating over a Calendar
			for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
				Component component = (Component) i.next();

				System.out.println("Component [" + component.getName() + "]");

				for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
					Property property = (Property) j.next();

					if (property.getName().equals("UID")) {
						uid = property.getValue();
					}
					if (property.getName().equals("SUMMARY")) {
						summary = property.getValue();
					}
					if (property.getName().equals("DTSTART")) {
						st = property.getValue();
					}
					if (property.getName().equals("DTEND")) {
						et = property.getValue();
					}
					if (property.getName().equals("URL")) {
						url = property.getValue();
					}
				}
				cb.add(new ClassBooking(uid, summary, st, et, url));
			}
	    	}
	    	for (ClassBooking booking : cb) {
				/**String message = "UID:" + booking.getUID() + "ST: "
						+ booking.getStartTime() + "ET: " + booking.getEndTime();
				Log.i("pab", message);**/

				System.out.println("UID:" + booking.getUID() + "ST: "
						+ booking.getStartTime() + "ET: " + booking.getEndTime());

			}

			
			return cb;
		}
}
