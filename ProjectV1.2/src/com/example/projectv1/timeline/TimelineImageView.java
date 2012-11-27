package com.example.projectv1.timeline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
//Nail's comment
public class TimelineImageView extends ImageView{
	Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
	private int[][] time_array;

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

		p.setColor(Color.RED);
		for (int i = 0; i<3; i++) {
			this.time_array = new int[][] {{50, 150},{200, 300},{600,700}};
			canvas.drawRect(time_array[i][0], 0, time_array[i][1], height, p);
		}
	}
	
	public void setTimes(int[][] time_array) {
		this.time_array = time_array;
	}
}
