// This class causes an activity to close and return to the main activity if there 
// has been no user interaction for 1 min.
// When you create a new activity please do the following:
//		create a new instance variable for you class : private OneMinTimeout timer = new OneMinTimeout(this);
// 		call timer.startTimer() in you onCreate method.
//		call timer.killThread() or timer.restartTimer() in your onClickListeners
//		depending on whether you want the timer to restart for this activity or
// 		you want to kill the thread because
//		you are opening a new activity (which will have it's own timer)
//
//
//	Is a bit buggy for now. may need onPause and onResume methods

package com.example.projectv1;


import android.app.Activity;

class OneMinTimeout 
{
	private Activity activity;
	private Thread t;
	
	OneMinTimeout(Activity activity){
		this.activity = activity;
	}
	
	
	public void startTimer(){
		t =  new Thread(new Runnable()  {
			public void run() {
				try{
					Thread.sleep(2000);			// must change this to 1 minute when the class has been fully implemented.
					activity.finish();			// finish the activity if the thread reaches this point
				}catch(InterruptedException e){
					e.printStackTrace();
				}		
			}
		});
		t.start();				
	}
	
	public void killThread() {		// stop the thread from executing before it reaches activity.finish()
		t.interrupt();
		t=null;
	}
	
	public void restartTimer() {	// kill the old thread and start a new one
		killThread();
		startTimer();
	}

}	
	
