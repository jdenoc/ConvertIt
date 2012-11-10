package com.jdenoc.convertit;
// ColorConvertIt_old.java
// GUI: tab_host.xml
//		convert_menu.xml	(for menu)
//		tab_icon_rgb.xml	(for tab animation)
//		tab_icon_hex.xml	(for tab animation)
// Author: Denis O'Connor
// Last Modified: 08-NOV-2012
// Allows the user to convert colours from HEX to RGB and back

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;		// TESTING
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class ColorConvertIt_old extends TabActivity{

	private TabHost host;
	private TabSpec rgb, hex;
//	private final String TAG = "Color";		//	TESTING
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		// removes title bar
		setContentView(R.layout.tab_host);

//		Setup Tab feature
		host = getTabHost();
		rgb = host.newTabSpec("RGB -> HEX");
		hex = host.newTabSpec("HEX -> RGB");
//		Setup tab icons
		rgb.setIndicator("RGB", getResources().getDrawable(R.drawable.tab_icon_rgb));
		hex.setIndicator("HEX", getResources().getDrawable(R.drawable.tab_icon_hex));
		
		rgb.setContent(new Intent(this, RGBtoHEX.class));
		hex.setContent(new Intent(this, HEXtoRGB.class));
		host.addTab(rgb);
		host.addTab(hex);
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
