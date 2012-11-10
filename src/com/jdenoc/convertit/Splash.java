package com.jdenoc.convertit;
// Splash.java
// GUI: splash.xml
//		fadeout.xml		(for animation)
//		fadein.xml		(for animation)
// Author: Denis O'Connor
// Last modified: 31-OCT-2012
// Displays a splash screen for 1.5 seconds, fades out and then displays Main.java

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//import android.util.Log;		//	TESTING
import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity{

//	private final String TAG = "SPLASH";		//	TESTING
	private final int SPLASH_TIMER = 1500;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Display in full screen, i.e.: no bar at top of screen showing time, signal, etc.
		requestWindowFeature(Window.FEATURE_NO_TITLE);		// removes title bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.splash);	// displays XML GUI
//		Log.d(TAG, "Splash started");		//	TESTING
		
		
		
		new Handler().postDelayed(new Runnable(){
			
			@Override
			public void run() {
				// Makes the SDK Version available for comparison
				SDKVersion.setSDKVersion();			
				
				// Create an intent that will start the main activity. 
				startActivity(new Intent("com.jdenoc.convertit.MAIN"));
                    
	            // Finish splash activity so user can't go back to it. 
				finish();
	            
	            // Apply our splash exit (fade out) and main entry (fade in) animation transitions.
	            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
			}
		}, SPLASH_TIMER);
			
	}// END onCreate()
}
