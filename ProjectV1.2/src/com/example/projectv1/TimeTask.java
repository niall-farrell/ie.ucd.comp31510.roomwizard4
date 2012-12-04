package com.example.projectv1;

import java.util.TimerTask;

import android.app.Activity;

public class TimeTask  extends TimerTask {

	private Activity activity;
	
	TimeTask(Activity activity)
	{
		this.activity = activity;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		activity.finish();
	}

}
