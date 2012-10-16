package com.jdenoc.convertit;
//TimeConversion.java
//GUI: 	time_host.xml
//		convert_menu.xml		(for Menu)
//		tab_icon_location.xml	(tab icon animation)
//		tab_icon_zones.xml		(tab icon animation)
//Author: Denis O'Connor
//Last Modified: 28/6/12

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.util.Log;		//	TESTING
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TimeConvertIt extends TabActivity {

	private SharedPreferences settings;
	private TimeFunctions t1;
	private TabHost host;
	private TabSpec location, zone;
//	private final String TAG = "Time";		//	TESTING
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		// removes title bar
		setContentView(R.layout.tab_host);
		
		settings = getSharedPreferences("settings", MODE_WORLD_READABLE);
		t1 = new TimeFunctions(settings.getBoolean("time", true));
		t1.setPublicTime(t1.getCurrentTime());
		t1.setPublicZone(t1.getCurrentTimeZone());
		
//		Setup Tab feature
		host = getTabHost();
		location = host.newTabSpec("Location");
		zone = host.newTabSpec("Time Zone");
//		Setup tab icons
		location.setIndicator("Location", getResources().getDrawable(R.drawable.tab_icon_location));
		zone.setIndicator("Time Zone", getResources().getDrawable(R.drawable.tab_icon_zone));
		
		location.setContent(new Intent(this, TimeByLocation.class));
		zone.setContent(new Intent(this, TimeByZone.class));
		host.addTab(location);
		host.addTab(zone);
	}// END onCreate()

//	Specifically for phones. When press MENU button on phones 
	public boolean onCreateOptionsMenu(Menu menu){		// Should be (Menu menu), but Menu is the name of the class, so there is a conflict error
		super.onCreateOptionsMenu(menu);
		MenuInflater awesome = getMenuInflater();
		awesome.inflate(R.menu.convert_menu, menu);
//		Log.d(TAG, "'MENU' button pressed");		// TESTING
		return true;
	}// END onCreateOptionsMenu()
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.menuAbout:
//			Displays About.java file
			startActivity(new Intent("com.jdenoc.convertit.ABOUT"));
			return true;
			
		case R.id.menuReset:
//			Resets the current activity
			finish();
			startActivity(getIntent());
//			Log.d(TAG, "Activity Reset");		//	TESTING
			return true;
			
		case R.id.menuSettings:
//			Displays Settings.java
			startActivity(new Intent("com.jdenoc.convertit.SETTINGS"));
			return true;
			
		case R.id.menuHelp:
//			Displays a help menu, depending on the type of conversion being made
			startActivity(new Intent("com.jdenoc.convertit.HELP"));
			return true;
		}
		return false;
	}// END onOptionsItemSelected()
//END Menu Button setup

}
