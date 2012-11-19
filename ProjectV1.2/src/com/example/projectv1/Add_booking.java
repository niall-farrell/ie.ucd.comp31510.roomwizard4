package com.example.projectv1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Add_booking extends Activity {

	String url="dummy url"; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_booking);
		TextView content = new TextView(this);
		content = (TextView) findViewById(R.id.add_url);
		content.setText(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_booking, menu);
		return true;
	}
	public void buttonClick(View v)
	{
		switch(v.getId())
		{
		case R.id.back_from_add:						
			finish();			
			break;			
		}
	}
}
