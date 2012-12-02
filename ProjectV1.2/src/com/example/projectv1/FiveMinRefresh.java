// This class is for refreshing the main activity every 5 minutes to check that the display is up to date.
//	If modifying this class, please keep the variables static as new class instances are created every 5 minutes
//	but most of them share the same data. 
//	The class works like this:
//	When first called, the constructor is given an array of strings to iterate through and display in sequence.
//	It is also given a TextView to display the results in.
//	A new thread is created when FiveMinRefresh.execute() is called. (build in method of AsynckTask class.)
//	It first runs onPreExecute, which currently sets initial values the first time it is run.
//	Then it calls doInBackground, which makes it wait. This is set for 1 second for debugging, will eventually be set to 5 minutes.
//  Finally, the onPostExecute method is called, which updates the display and then creates a new instance of the FiveMinRefresh
//  class to wait 5 more minutes and do the next update.

// When adding content to be displayed, add it in the onPostExecute method.

package com.example.projectv1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;


class FiveMinRefresh extends AsyncTask <String, Void, String>
{
	private static int count=0;
	private static int size;
	private static ArrayList<ClassBooking> cb;
	private static TextView view;
	private static Calendar c = Calendar.getInstance();
	private static int date = c.get(Calendar.DATE);
	private static int month = c.get(Calendar.MONTH);
	private static int year = c.get(Calendar.YEAR);	
	private static Boolean occupied = false;
	private static String display;
	
	FiveMinRefresh(ArrayList<ClassBooking> inCb, TextView inView)
	{
		FiveMinRefresh.cb= inCb;
		FiveMinRefresh.view = inView;
		FiveMinRefresh.size=cb.size();
	}
	

	@Override
	protected String doInBackground(String... params) {
	
			try{
				Thread.sleep(2000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		
		return String.valueOf(count);
	}
	protected void onPostExecute(String result){
	
		int index = Integer.valueOf(result);
		
		if (count<size) {
			setDisplay(index);
			new FiveMinRefresh(cb, view).execute();
		}
		count++;
	}
	
	protected void onPreExecute(){
		
		if(count==0)
		{
			setDisplay(0);
		}
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
		
		Calendar start_cal = iCalToTimeToday(start);
		Calendar end_cal = iCalToTimeToday(end);
		Calendar now_cal = Calendar.getInstance(); 
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("K:mm a");
		
		// Check if room is currently occupied
		if (now_cal.after(start_cal) && now_cal.before(end_cal)) {
			occupied = true;
			display = "Summary: " + summary + " \n" +
					"Time: " + timeFormat.format(start_cal.getTime()) + " - " + timeFormat.format(end_cal.getTime()) + " \n" +
							"URL: " + url + "\n";
		}
		
		if (occupied) {
			view.setText(display);
			
			/*view.setText("Organizer's name \n" +
					"Event name \n" +
					"Booking time (from - to) \n" +
					"Current date: " + date + "/" + month + "/" + year + ".\n" +
					"Room number \n"+ display);
			// mainLayout.setBackgroundColor(0xCCCC0000);*/
		} else
		{
			view.setText("This room is currently free");
			// mainLayout.setBackgroundColor(Color.BLUE);
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
