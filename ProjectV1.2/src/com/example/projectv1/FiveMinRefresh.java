package com.example.projectv1;

import android.os.AsyncTask;
import android.widget.TextView;


class FiveMinRefresh extends AsyncTask <String, Void, String>
{
	private static int count=0;
	private static int size;
	private static String[] times;
	private static TextView view;
	
	
	FiveMinRefresh(String[] data, TextView inView)
	{
		FiveMinRefresh.times= data;
		FiveMinRefresh.view = inView;
		FiveMinRefresh.size=times.length;
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
	
		String display = times[Integer.valueOf(result)];
		view.setText(display);
		count++;
		if (count<size){
			
			new FiveMinRefresh(times, view).execute();
		}
	}
	
	protected void onPreExecute(){

	}
	protected void onProgressUpdate(Void... values){
		
	}

}
