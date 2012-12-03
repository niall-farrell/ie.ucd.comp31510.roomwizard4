package com.example.projectv1.timeline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import com.example.projectv1.ClassBooking;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class TimelineImageView extends ImageView{
	Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
	private ArrayList<ClassBooking> class_array = new ArrayList<ClassBooking>();

	public TimelineImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public TimelineImageView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int height = canvas.getHeight();
		int width = canvas.getWidth();
		
		Paint shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setTypeface(Typeface.SANS_SERIF);
        shadowPaint.setColor(Color.BLACK);
        shadowPaint.setTextSize(15.0f);
        
		
		
        p.setColor(0xFF00427b);
        Map<String, Integer[]> draw_map = createDrawMap(class_array, width);
		for (Map.Entry<String, Integer[]> entry : draw_map.entrySet()) {
			canvas.drawRect(entry.getValue()[0], 0, entry.getValue()[1], height, p);
		}
		
		// TODO - drawing temp hour marks
		p.setColor(Color.BLACK);
		int num_hours = 12;
		for (int i=0; i<num_hours+1; i++) {
			canvas.drawLine((i*60) * (width / (num_hours*60)), 0, (i*60) * (width / (num_hours*60)), height, p);
			canvas.drawText(8+i+":00", (i*60) * (width / (num_hours*60))+12, 15, shadowPaint);
		}
	}
	
	public void setTimes(ArrayList<ClassBooking> cb) {
		this.class_array = cb;
	}
	
	public static int iCalToMins(String iCalText) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
		Date date = null;
		
		try {
			date = format.parse(iCalText);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block  
			e1.printStackTrace();  
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int mins = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
		
		return mins;
	}
	
	public static Date minsToTime(int mins) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		try {
			cal.setTime(format.parse("00:00:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.add(Calendar.MINUTE, mins);
		
		Date result = cal.getTime();
		
		return result;
	}
	
	public static Map<String, Integer[]> createDrawMap(ArrayList<ClassBooking> cb, int width) {
		Map<String, Integer[]> map = new HashMap<String, Integer[]>();

    	for (ClassBooking booking : cb) {
    		int start_mins = iCalToMins(booking.getStartTime());
    		int end_mins = iCalToMins(booking.getEndTime());
 
    		// TODO - using test values for start and end of day
    		int start_day_mins = iCalToMins("20121127T080000Z");
    		int end_day_mins = iCalToMins("20121127T200000Z");
    		
    		int start_draw = (start_mins - start_day_mins) * (width / (end_day_mins - start_day_mins));
    		int end_draw = (end_mins - start_day_mins) * (width / (end_day_mins - start_day_mins));
    		Integer draw_times[] = {start_draw, end_draw};
    		
    		map.put(booking.getUID(), draw_times);
		}
    	
    	return map;
	}
}