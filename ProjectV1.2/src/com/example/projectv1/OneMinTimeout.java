// This class was used as a timer. It has now been replaced with teh Timeout class.

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
				try{							// 1 minute * 60 seconds * 1000 milliseconds
					Thread.sleep(3000);			// must change this to 1 minute when the class has been fully implemented.
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
	
