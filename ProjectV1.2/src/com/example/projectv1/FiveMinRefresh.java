// This class is for refreshing the main activity every 5 minutes to check that the display is up to date.
//	If modifying this class, please keep the variables static as new class instances are created every 5 minutes
//	but most of them share the same data. 
//	The class works like this:
//	When first called, the constructor is given an array of strings to iterate through and display in sequence.
//	It is also given a TextView to display the results in.
//	A new thread is created when FiveMinRefresh.execute() is called. (build in method of AsynckTask class.)
//	It first runs onPreExecute, which currently sets initial values the first time it is run.
//	Then it calls doInBackground, which makes it wait.
//  Finally, the onPostExecute method is called, which updates the display and then creates a new instance of the FiveMinRefresh
//  class to wait 5 more minutes and do the next update.

// When adding content to be displayed, add it in the onPostExecute method.

package com.example.projectv1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


class FiveMinRefresh extends AsyncTask <String, Void, String>
{
	private static int count=0;
	private static int size;
	private static ArrayList<ClassBooking> cb;
	private static TextView view;
	private static Boolean occupied = false;
	private static String display;
	private static Activity activity;
	
	FiveMinRefresh(ArrayList<ClassBooking> inCb, TextView inView, Activity act)
	{
		FiveMinRefresh.cb= inCb;
		FiveMinRefresh.view = inView;
		FiveMinRefresh.size=cb.size();
		FiveMinRefresh.activity = act;
	}
	

	@Override
	protected String doInBackground(String... params) {		// wait 5 minutes and then calls onPostExecute
	
			try{
				Thread.sleep(5 * 60 * 1000);	// 5 minutes * 60 seconds * 1000 milliseconds
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		
		return String.valueOf(count);
	}
	protected void onPostExecute(String result){		// update the display and then start the next thread to wait 5 more minutes 
	
		int index = Integer.valueOf(result);
		
		Log.v("count", count + "");
		Log.v("size", size + "");
		
		if (count<size) {
			setDisplay(index);
			new FiveMinRefresh(cb, view, activity).execute();
		}
		count++;
	}
	
	protected void onPreExecute(){		// executed before waiting. if this is the first time it is called then set the display now
		if(count==0) {
			setDisplay(0);
		}
		count=0;
	}
	protected void onProgressUpdate(Void... values){
		
	}
	private void setDisplay (int index)
	{
		String start = cb.get(index).getStartTime();
		String uid = cb.get(index).getUID();
		String summary = cb.get(index).getSummary();
		String end = cb.get(index).getEndTime();
		String url = cb.get(index).getURL();
		String organizer = cb.get(index).getOrganizer();
		
		Calendar start_cal = iCalToTimeToday(start);
		Calendar end_cal = iCalToTimeToday(end);
		Calendar now_cal = Calendar.getInstance(); 
			
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);   
		
		String room = preferences.getString("room", "Room X");
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("k:mm a");
		
		// Check if room is currently occupied
		if (now_cal.after(start_cal) && now_cal.before(end_cal)) {
			occupied = true;
			display = summary + " \n" +
					  timeFormat.format(start_cal.getTime()) + " - " + timeFormat.format(end_cal.getTime()) + " \n" +
				      organizer + "\n" + "Room " + room;
		}
		

		
		if (occupied) {			
			
			view.setText(display);
		} else
		{
			view.setText(view.getResources().getString(R.string.The_room_is_currently_free));
			
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
}
