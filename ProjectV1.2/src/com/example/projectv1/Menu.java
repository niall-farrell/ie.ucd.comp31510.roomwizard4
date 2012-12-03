package com.example.projectv1;


import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;


public class Menu extends Activity {	

	//OneMinTimeout timer = new OneMinTimeout(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	//	timer.startTimer();
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
			
		//	timer.killThread();
			Intent bookRoom = new Intent("com.project.ADDBOOKING");
			startActivity(bookRoom);
			
			break;
			
		case R.id.preferences:
			
			//timer.killThread();
			Intent promptPass = new Intent("android.intent.action.PASS");
			startActivity(promptPass);
			
			break;
		}
	}


}
