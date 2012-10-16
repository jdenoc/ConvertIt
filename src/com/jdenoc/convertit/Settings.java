package com.jdenoc.convertit;
// Settings.java
// GUI: list_layout_controller.xml	(for animation)
//		slide_in.xml				(for animation)
// Author: Denis O'Connor
// Last Modified: 05/7/12
// Provides the user with a list of "Settings" that they can alter 

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
//import android.util.Log;		//	TESTING
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Settings extends ListActivity {

	private String[] classNames = {"AccessInternet", "ChangeDecimal", "ChangeCurrencyDisplay", "ChangeTimeDisplay", "About", "Help"};					// classes/activities the menu can access
	private String[] options = {"Activate Internet", "No. of Decimal places", "Change Currency Designation", "Change Time Display Format", "About", "Help"};	// what is displayed to user
//	private final String TAG = "Settings";		//	TESTING
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		Log.d(TAG, "Settings activated");		//	TESTING
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options));

//		Causes the ListActivity to run an animation that makes the text to enter from the right sliding to there normal position 
		LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.list_layout_controller);
		getListView().setLayoutAnimation(controller);	// Setting activity names slide in from right side 
	}// END onCreate()

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String openClass = classNames[position];
		
		try{
			Class<?> selected = Class.forName("com.jdenoc.convertit."+openClass);
			Intent selectedIntent = new Intent(this, selected);
			
//			Log.d(TAG, "Selected item: "+options[position]+" at position: "+position);		//	TESTING
			
			switch(position){
			case 0:
				if(new CurrencyFunctions().checkInternetConnectivity(
					true,
					(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE),
					(WifiManager) this.getSystemService(Context.WIFI_SERVICE),
					this
				) == -1){
					startActivity(new Intent("com.jdenoc.convertit.ACCESSINTERNET"));	// start AccessInternet activity
				}
				break;
				
			default:
				startActivity(selectedIntent);
				break;
			}
						
		}catch(Exception e){
//			Log.e(TAG, "Class not found: "+e.toString());		//	TESTING
			Toast.makeText(this, R.string.featureNA, Toast.LENGTH_SHORT).show();
		}
	}// END onListItemClick()

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}// END onPause()
}
