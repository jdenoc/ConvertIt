package com.jdenoc.convertit;
// ColorConvertIt.java
// GUI: convert_menu.xml	(for menu)
// Author: Denis O'Connor
// Last Modified: 08-NOV-2012
// SRC: http://android-er.blogspot.com/2012/06/create-actionbar-in-tab-navigation-mode.html
// Allows the user to convert colors from HEX to RGB and back
// This activity contains allows the use of Fragments instead of Tabs (which are now depricated).  

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;		//  TESTING
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.jdenoc.convertit.fragments.*;

@TargetApi(11)
public class ColorConvertIt extends Activity{

	private final String TAG = "Color";		//	TESTING
	private ActionBar.Tab rgb, hex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getResources().getString(R.string.app_name)+" | Colour");
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		rgb = actionBar.newTab();
		rgb.setText("RGB");
		rgb.setTabListener(new ActionBarTabListener<RGBFragment>(this, "RGB", RGBFragment.class));
		actionBar.addTab(rgb);
		
		hex = actionBar.newTab();
		hex.setText("HEX");
		hex.setTabListener(new ActionBarTabListener<HEXFragment>(this, "HEX", HEXFragment.class));
		actionBar.addTab(hex);

		if(savedInstanceState != null){
			int savedIndex = savedInstanceState.getInt("SAVED_INDEX");
			getActionBar().setSelectedNavigationItem(savedIndex);
			Log.d(TAG, "Set action bar to Saved State");		// TESTING ONLY
		}
	}// END onCreate()
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("SAVED_INDEX", getActionBar().getSelectedNavigationIndex());
		Log.d(TAG, "Display Saved Instance");
	} // END onSaveInstanceState()
	
	@TargetApi(13)
	public static class ActionBarTabListener<T extends Fragment> implements TabListener {
		private Fragment fragment;
		private final Activity fActivity;
		private final String fTag;
		private final Class<T> fClass;
	
		/**
		* Constructor used each time a new tab is created.
		* 
		* @param activity
		*            The host Activity, used to instantiate the fragment
		* @param tag
		*            The identifier tag for the fragment
		* @param fragmentClass
		*            The fragment's Class, used to instantiate the fragment
		*/
		
		public ActionBarTabListener(Activity activity, String tag, Class<T> fragmentClass) {
			fActivity = activity;
			fTag = tag;
			fClass = fragmentClass;
		}// END Constructor()
		
		/* The following are each of the ActionBar.TabListener call backs */
	
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// Check if the fragment is already initialized
			if (fragment == null) {
				// If not, instantiate and add it to the activity
				fragment = Fragment.instantiate(fActivity, fClass.getName());
				ft.add(android.R.id.content, fragment, fTag);
			} else {
				// If it exists, simply attach it in order to show it
				ft.attach(fragment);
			}
		}// END onTabSelected()
		
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			Fragment currentFragment = fActivity.getFragmentManager().findFragmentByTag(fTag);
			
			if (currentFragment != null) {
				ft.detach(currentFragment);
				Log.d(fTag, "Fragment unselected: "+fTag);
			}
		}// END onTabUnselected()
		
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			Toast.makeText(fActivity, "You're already here", Toast.LENGTH_SHORT).show();
		}// END onTabReselected()
	}// END ActionBarTabListener<T>()
	
	
//	Specifically for phones. When press MENU button on phones 
	public boolean onCreateOptionsMenu(Menu menu){		// Should be (Menu menu), but Menu is the name of the class, so there is a conflict error
		super.onCreateOptionsMenu(menu);
		MenuInflater awesome = getMenuInflater();
		awesome.inflate(R.menu.convert_menu, menu);
		Log.d(TAG, "'MENU' button pressed");		// TESTING
		return true;
	}// END onCreateOptionsMenu()
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case android.R.id.home:
//			Returns User back to the Main.java Activity file
			finish();
			Intent intent = new Intent("com.jdenoc.convertit.MAIN");
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		
		case R.id.menuAbout:
//			Displays About.java file
			startActivity(new Intent("com.jdenoc.convertit.ABOUT"));
			return true;
			
		case R.id.menuReset:
//			Resets the current activity
			finish();
			startActivity(getIntent());
			Log.d(TAG, "Activity Reset");		//	TESTING
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
