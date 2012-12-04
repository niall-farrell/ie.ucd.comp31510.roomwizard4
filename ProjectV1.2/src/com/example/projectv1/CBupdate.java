package com.example.projectv1;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;


class CBupdate extends AsyncTask <String, Void, String>
{
	private static int count=0;
	private static int size;
	private static ArrayList<ClassBooking> cb;
	private static int refresh;
	private static Activity activity;
	
	CBupdate(ArrayList<ClassBooking> inCb,  int ref, Activity act)
	{
		CBupdate.cb= inCb;
		CBupdate.size=cb.size();
		CBupdate.refresh = ref;
		CBupdate.activity = act;
	}
	

	@Override
	protected String doInBackground(String... params) {		
	
			try{
				Thread.sleep(1000);	
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		
		return String.valueOf(count);
	}
	protected void onPostExecute(String result){		
	
		
		Log.v("count", count + "");
		Log.v("size", size + "");
		
		if (count<size) {
			setCB();
			new CBupdate(cb, refresh, activity).execute();
		}
		count++;
	}
	
	protected void onPreExecute(){		
	
	}
	
	protected void onProgressUpdate(Void... values){
		
	}
	
	public void setCB()
	{
		try {
			cb = getClassBooking();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Calendar calendar = Calendars.load(new
	// URL("http://ical4j.cvs.sourceforge.net/viewvc/*checkout*/ical4j/iCal4j/etc/samples/valid/Australian32Holidays.ics"));
	public java.util.ArrayList<ClassBooking> getClassBooking()
			throws IOException, ParserException {
		String uid = "";
		String summary = "";
		String st = "";
		String et = "";
		String url = "";
		String organizer = "";
		URL add = null;
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);   
		String calurl = preferences.getString("urliCalendar", "www.ucd.ie");
		
		try {
			add = new URL("http://www.chartspms.com/android/calendar.ics");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
		CalendarBuilder builder = new CalendarBuilder();
		// CalendarBuilder builder = new CalendarBuilder();

		calendar = builder.build(add.openStream());
		//calendar = builder.build(getResources().openRawResource(0) .openStream());
		//InputStream inputStream = getResources().openRawResource(R.raw.calendar);
		//calendar = builder.build(inputStream);
		if (calendar != null) {

			// Iterating over a Calendar
			for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
				Component component = (Component) i.next();

				System.out.println("Component [" + component.getName() + "]");

				for (Iterator j = component.getProperties().iterator(); j
						.hasNext();) {
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
					if (property.getName().equals("ORGANIZER")) {
						organizer= property.toString().substring(13);
						int end = organizer.indexOf(":");
						organizer = organizer.substring(0, end);
					}
				
				}
				cb.add(new ClassBooking(uid, summary, st, et, url,organizer));
			}
		}
		for (ClassBooking booking : cb) {
			/**
			 * String message = "UID:" + booking.getUID() + "ST: " +
			 * booking.getStartTime() + "ET: " + booking.getEndTime();
			 * Log.i("pab", message);
			 **/

			System.out.println("UID:" + booking.getUID() + "ST: "
					+ booking.getStartTime() + "ET: " + booking.getEndTime());

		}

		return cb;
	}

}