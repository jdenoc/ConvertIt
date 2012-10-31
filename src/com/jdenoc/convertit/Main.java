package com.jdenoc.convertit;
// Main.java
// GUI: main.xml
//		main_menu.xml	(for menu)
// Author: Denis O'Connor
// Last modified: 28-OCT-2012
// Main menu/Activity
// User comes to this after Splash has completed.
// User can make a selection as to which Conversion to perform 

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;		//	TESTING
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener{
	
	private ImageButton currency, length, volume, mass, timezone, speed, color, temp, time, area;
	private static final String TAG="Main";		// TESTING
	
	@TargetApi(11)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "Main Start");		//	TESTING
		super.onCreate(savedInstanceState);
		if(SDKVersion.useActionBar()){		
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}else{
			requestWindowFeature(Window.FEATURE_NO_TITLE);		// removes title bar
		}
		setContentView(R.layout.main);
		
		// set XML buttons to be interactive
		currency = (ImageButton) findViewById(R.id.menuCur);
		length = (ImageButton) findViewById(R.id.menuLen);
		volume = (ImageButton) findViewById(R.id.menuVol);
		mass = (ImageButton) findViewById(R.id.menuMass);
		timezone = (ImageButton) findViewById(R.id.menuTimeZone);
		temp = (ImageButton) findViewById(R.id.menuTemp);
		speed = (ImageButton) findViewById(R.id.menuSpeed);
		color = (ImageButton) findViewById(R.id.menuColor);
		area = (ImageButton) findViewById(R.id.menuArea);
		time = (ImageButton) findViewById(R.id.menuTime);
		
		// set click listeners for buttons
		currency.setOnClickListener(this);
		length.setOnClickListener(this);
		volume.setOnClickListener(this);
		mass.setOnClickListener(this);
		timezone.setOnClickListener(this);
		speed.setOnClickListener(this);
		color.setOnClickListener(this);
		temp.setOnClickListener(this);
		time.setOnClickListener(this);
		area.setOnClickListener(this);
	}// END onCreate()
	
	@Override
	public void onClick(View v) {
		Bundle data = new Bundle();
		Intent file = new Intent("com.jdenoc.convertit.CONVERT");
		
		switch(v.getId()){
		case R.id.menuCur:
			// When the user touches the Currency Button
			Log.d(TAG, "Currency Selected");		// TESTING
			
//			Thread FOR lOADING... Dialog
			final ProgressDialog loading = ProgressDialog.show(this, "", "Loading...");
			Thread ready = new Thread(){
				public void run(){
					try{
						sleep(3000);		// runs for 3 secs
					}catch(Exception e){
//						Log.e(TAG, e.getMessage());		//	TESTING
					}finally{
						loading.dismiss();	// Finishes the Loading Dialog
//						Log.d(TAG, "Loading complete!");		//	TESTING
					}
				}
			};
//			END of Thread
			
//			Check if Internet is available
			ready.start();
			switch(new CurrencyFunctions().checkInternetConnectivity(
				false,
				(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE),
				(WifiManager) this.getSystemService(Context.WIFI_SERVICE),
				this
			)){
				case -1:
					startActivity(new Intent("com.jdenoc.convertit.ACCESSINTERNET"));	// start AccessInternet activity
					break;
				case 1:
					startActivityForResult(new Intent("com.jdenoc.convertit.CURRENCY"), 98765);	// start Currency activity
					break;
			}
//		    END Internet check
			break;
			
		case R.id.menuLen:
			// when user touches the Length button
			Log.d(TAG, "Length Selected");			// TESTING
			runActivity("Length", R.array.lengthArray, data, file, true);
			break;
			
		case R.id.menuMass:
			// when user touches the Mass button
			Log.d(TAG, "Mass Selected");		// TESTING
			runActivity("Mass", R.array.massArray, data, file, true);
			break;
			
		case R.id.menuVol:
			// when user touches the Volume button
			Log.d(TAG, "Volume Selected");		// TESTING
			runActivity("Volume", R.array.volumeArray, data, file, true);
			break;
			
		case R.id.menuTimeZone:
			// when user touches the Time Zone button
			Log.d(TAG, "Time Zone Selected");		// TESTING
			startActivity(new Intent("com.jdenoc.convertit.TIME"));
			break;
			
		case R.id.menuArea:
			// when user touches the Time button
			Log.d(TAG, "Area Selected");		// TESTING
			runActivity("Volume", R.array.volumeArray, data, file, true);
			break;
			
		case R.id.menuTime:
			// when user touches the Time button
			Log.d(TAG, "Time Selected");		// TESTING
			runActivity("Volume", R.array.volumeArray, data, file, true);
			break;
			
		case R.id.menuTemp:
			// when user touches the Temperature button
			Log.d(TAG, "Temperature Selected");	// TESTING
			runActivity("Temperature", R.array.tempArray, data, file, true);
			break;
			
		case R.id.menuSpeed:
			// when user touches the Temperature button
			Log.d(TAG, "Speed Selected");	// TESTING
			runActivity("Speed", R.array.speedArray, data, file, true);
			break;
			
		case R.id.menuColor:
			// when user touches the Time button
			Log.d(TAG, "Color Selected");		// TESTING
			startActivity(new Intent("com.jdenoc.convertit.COLOR"));
			break;
		}
	}// END onClick()
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 98765){
			if(resultCode == 666){
				startActivity(new Intent("com.jdenoc.convertit.NOCONNECTION"));
			}
		}
	}// END onActivityResult()

	public void runActivity(String title, int resID, Bundle data, Intent file, boolean set){
		if (set){
			data.putString("title", title);	// attach the title to a bundle
			data.putInt("spinner", resID);	// attach the spinner id to the bundle 
			file.putExtras(data);			// bundle is attached to an intent
			startActivity(file);			// start ConvertIt activity with parameter sent
		}else{
			Toast.makeText(this, R.string.featureNA, Toast.LENGTH_SHORT).show();
		}	
	}// END runActivity()
	
//	Specifically for phones. When press MENU button on phones 
	public boolean onCreateOptionsMenu(Menu menu){		// Should be (Menu menu), but Menu is the name of the class, so there is a conflict error
		super.onCreateOptionsMenu(menu);
		MenuInflater awesome = getMenuInflater();
		awesome.inflate(R.menu.main_menu, menu);
//		Log.d(TAG, "'MENU' button pressed"); 	// TESTING
		return true;
	}// END onCreateOptionsMenu()

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.menuAbout:
//			Display About.java activity
			startActivity(new Intent("com.jdenoc.convertit.ABOUT"));
			return true;
		case R.id.menuSettings:
//			Display Settins.java activity
			startActivity(new Intent("com.jdenoc.convertit.SETTINGS"));
			return true;
		case R.id.menuHelp:
//			Displays Help.java activity 
			startActivity(new Intent("com.jdenoc.convertit.HELP"));
			return true;
		}
		return false;
	}// END onOptionsItemSelected()
// END Menu Button setup
}
