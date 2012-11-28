package com.example.projectv1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartAppAtBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	        Intent i = new Intent(context, MainActivity.class);  
	        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        context.startActivity(i);  
	}
}