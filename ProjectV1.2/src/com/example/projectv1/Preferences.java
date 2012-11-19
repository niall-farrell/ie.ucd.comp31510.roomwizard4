package com.example.projectv1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Preferences extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_preferences, menu);
		return true;
	}
	public void buttonClick(View v)
	{
		switch(v.getId())
		{
		case R.id.back_from_prefs:
						
			finish();
			
			break;
			
		case R.id.update:
			
			// need to implement this
			//
			//
			finish();
			
			break;
			
		}
	}
}
