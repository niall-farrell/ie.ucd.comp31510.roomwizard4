package com.example.projectv1;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;


public class Menu extends Activity {	

	OneMinTimeout timer = new OneMinTimeout(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		timer.startTimer();
		
		Typeface qs1=Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.ttf");
		Typeface qs2=Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.ttf");
	
		Button back = new Button (this);
		back = (Button)findViewById(R.id.back_from_menu);
		back.setTypeface(qs1);
		
		TextView pref = new TextView(this);
		pref = (TextView) findViewById(R.id.preferences);
		pref.setTypeface(qs1);
		
		TextView book = new TextView(this);
		book = (TextView) findViewById(R.id.book_room);
		book.setTypeface(qs1);

	}



	public void buttonClick(View v)
	{
		switch(v.getId())
		{
		case R.id.back_from_menu:
			
		//	timer.killThread();
			finish();
			
			break;
			
		case R.id.book_room:
			
			//timer.killThread();
			Intent bookRoom = new Intent("com.project.ADDBOOKING");
			startActivity(bookRoom);
			
			break;
			
		case R.id.preferences:
			
			timer.killThread();
			Intent promptPass = new Intent("android.intent.action.PASS");
			startActivity(promptPass);
			
			break;
		}
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		timer.startTimer();
	}


}
