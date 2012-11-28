package com.example.projectv1;
// this is just a test comment  test again
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectv1.timeline.TimelineImageView;

public class MainActivity extends Activity{
	// Create our linkedlist of class bookings
	ArrayList<ClassBooking> cb = new ArrayList<ClassBooking>();
	
	private SharedPreferences pref;
	
	Boolean occupied = true;
	Calendar c = Calendar.getInstance();
	String language;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// initialise pref
		pref = getPreferences(MODE_PRIVATE);
		
		// create restart preference editor and update
        SharedPreferences.Editor editRestart = pref.edit();
        editRestart.putInt("nRestart", pref.getInt("nRestart", 0) + 1);

        // create network fail preference editor in the event of failure
        SharedPreferences.Editor editNetwork = pref.edit();
        
		try {
			cb = getClassBooking();
		} catch (IOException e) {
			// increment network preference failure
	        editNetwork.putInt("nNetwork", pref.getInt("nNetwork", 0) + 1); editNetwork.commit();
			e.printStackTrace();
		} catch (ParserException e) {
			// increment network preference failure
			editNetwork.putInt("nNetwork", pref.getInt("nNetwork", 0) + 1); editNetwork.commit();
			e.printStackTrace();
		}
		
//		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.myLayout);
		TextView content = new TextView(this);
		content = (TextView) findViewById(R.id.content);
//		content.setGravity(Gravity.CENTER_HORIZONTAL);
		

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
		
		
		
		// Timeline Listener
		final TimelineImageView timelineView = (TimelineImageView)findViewById(R.id.timeline);
		timelineView.setTimes(cb);
		timelineView.invalidate(); // Redraw timelineView
		timelineView.setBackgroundColor(Color.GREEN);
		
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
					
					// update interaction statistic
					SharedPreferences.Editor editInteract = pref.edit();
			        editInteract.putInt("nInteract", pref.getInt("nInteract", 0) + 1);
					
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
		
		// creates AlarmManager object to restore device from standby at specified time
		public void createStartAlarm(int hour, int minute)
		{
			// create calendar object for tomorrow using time parameters
	        Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(System.currentTimeMillis());
	        cal.add(Calendar.DATE, 1);
	        cal.set(Calendar.HOUR_OF_DAY, hour);
	        cal.set(Calendar.MINUTE, minute);
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND, 0);
			
			// initialise intent to start boot app service
	        Intent myIntent = new Intent(getBaseContext(), MyScheduledReceiver.class);

	        PendingIntent pendingIntent
	         = PendingIntent.getBroadcast(getBaseContext(),
	           0, myIntent, 0);
	      
	        // bind intent to alarm
	        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
	        
	        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
		}
		
		// method to send statistics report to server
		// this is called at the end of the day prior to standby
		public void sendStats()
		{
	        // Create HttpClient and header
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://www.chartspms.com/android/processStats.php"); // ## update to live server

	        try {
	            // Add your data
	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
	            nameValuePairs.add(new BasicNameValuePair("deviceid", "1"));
	            nameValuePairs.add(new BasicNameValuePair("restart", "" + pref.getInt("restart", 0)));
	            nameValuePairs.add(new BasicNameValuePair("interact", "" + pref.getInt("interact", 0)));
	            nameValuePairs.add(new BasicNameValuePair("network", "" + pref.getInt("network", 0)));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	            // Execute HTTP Post Request
	            HttpResponse response = httpclient.execute(httppost);
	            
	        } catch (Exception e)
	        {
	            // update nNetwork in pref
	            SharedPreferences pref = getPreferences(MODE_PRIVATE);
	            SharedPreferences.Editor editor = pref.edit();
	            editor.putInt("nInteract", pref.getInt("nInteract", 0) + 1);
	        }
		}
}
