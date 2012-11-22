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

	public TimelineImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public TimelineImageView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO These are just test values
		int height = canvas.getHeight();
		int[][] time_array = new int[][] {{50, 150},{200, 300},{600,700}};

		p.setColor(Color.RED);
		for (int i = 0; i<3; i++) {
			canvas.drawRect(time_array[i][0], 0, time_array[i][1], height, p);
		}
	}
}
