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

import java.util.ArrayList;
import java.util.Calendar;

import android.os.AsyncTask;
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
	private static Boolean occupied = true;
	
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
		
		setDisplay(index);
		
		count++;
		if (count<size){
			
			new FiveMinRefresh(cb, view).execute();
		}
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
		
		String display = "Summary: " + summary + " \n" +
				"Time: " + start + " - " + end + " \n" +
						"URL: " + url + "\n";
		
		if (occupied)
		{
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
}
