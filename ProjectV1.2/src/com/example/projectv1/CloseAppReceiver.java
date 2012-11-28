package com.example.projectv1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

// called by AlarmManager object and finishes main activity (end of day)
public class CloseAppReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	
	 Intent scheduledIntent = new Intent(context, MainActivity.class);
	 scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	 context.startActivity(scheduledIntent);
	 
	 PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE); 
	 WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotDimScreen");
	 } 
	 
}

