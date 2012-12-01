package com.example.projectv1;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Add_booking extends Activity {
    
	//String url="dummy url";
	//private OneMinTimeout timer = new OneMinTimeout(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	//	timer.startTimer();
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);   
		String url = preferences.getString("url", "www.ucd.ie");
		
		setContentView(R.layout.activity_add_booking);
		TextView content = new TextView(this);
		content = (TextView) findViewById(R.id.add_url);	
		content.setText(url);
		
		
		  
		ImageView qrcodeImage = (ImageView) findViewById(R.id.qrcode);
	    qrcodeImage.setImageBitmap(generateQrcode(url));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_booking, menu);
		return true;
	}
	
	private Bitmap generateQrcode(String input) {
        URL aURL;

        try {
            aURL = new URL("https://chart.googleapis.com/chart?cht=qr&chs=300x300&chld=L&choe=UTF-8&chl=" + 
            URLEncoder.encode(input, "UTF-8"));
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();


            return bm;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	//	timer.killThread();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	//	timer.startTimer();
	}

	public void buttonClick(View v)
	{
		switch(v.getId())
		{
		case R.id.back_from_add:
		//	timer.killThread();
			finish();			
			break;			
		}
	}
}
