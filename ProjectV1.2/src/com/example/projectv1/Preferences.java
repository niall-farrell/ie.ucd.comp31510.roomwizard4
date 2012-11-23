package com.example.projectv1;

import android.os.Bundle;
import android.util.Log;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Preferences extends PreferenceActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {       
        super.onCreate(savedInstanceState);       
        addPreferencesFromResource(R.xml.preferences);       


 //       Preference button = (Preference)findPreference("backButtons");
        Preference button = (Preference)getPreferenceManager().findPreference("backButtons");      
        
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference arg0) { 
                            //Intent intent = new Intent();
                            //intent.setClass(this,Menu.class);
                            //startActivity(intent);
                        	Log.v("back","button");
                			Intent openMenu = new Intent("com.project.MENU");
                			startActivity(openMenu);

                            finish();
         	                            return true;
                        }
                    });
    
    }
     
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, 0, "Show current settings");
        return super.onCreateOptionsMenu(menu);
    }
 
    @Override
public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
//                startActivity(new Intent(this, ShowSettingsActivity.class));
                return true;
        }
        return false;
    }
     
}
/*
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
*/