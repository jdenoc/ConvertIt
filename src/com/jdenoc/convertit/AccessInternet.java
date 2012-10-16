package com.jdenoc.convertit;
// AccessInternet.java
// GUI: settings_change.xml
// Author: Denis O'Connor
// Last modified: 28/6/12
// Gives the user the option to connect to the internet by Wi-Fi, Mobile Internet or not at all
// Internet access is required to use the Currency Conversion facility

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
//import android.util.Log;		//	TESTING
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AccessInternet extends Activity implements OnClickListener {

	private Button confirm, cancel;
	private RadioButton wifi, mobile;
	private TextView attention, main;
//	private final String TAG = "AccessInternet";		//	TESTING
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_change);
		
//		setup XML items to be interactive
		attention = (TextView) findViewById(R.id.changeHeading);
		main = (TextView) findViewById(R.id.changeMainText);
		confirm = (Button) findViewById(R.id.changeConfirm);
		cancel = (Button) findViewById(R.id.changeCancel);
		wifi = (RadioButton) findViewById(R.id.changeOp1);
		mobile = (RadioButton) findViewById(R.id.changeOp2);
		
//		set on click listeners
		attention.setVisibility(View.VISIBLE);
		attention.setText("ATTENTION!");
		main.setText("You need to be connected to the internet to access this facility.\nDo you wish to connect to in the invernet via:");
		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
		wifi.setText("Wi-Fi Connection");
		wifi.setOnClickListener(this);
		mobile.setText("Mobile Connection");
		mobile.setOnClickListener(this);
		
	}//	END onCreate()

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.changeOp1:
			wifi.setChecked(true);
			mobile.setChecked(false);
//			Log.d(TAG, "Wi-Fi selected");		//	TESTING
			break;
			
		case R.id.changeOp2:
			wifi.setChecked(false);
			mobile.setChecked(true);
//			Log.d(TAG, "Mobile Selected");		//	TESTING
			break;
			
		case R.id.changeConfirm:
			if (wifi.isChecked()){
				WifiManager wifiM = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				wifiM.setWifiEnabled(true);
//				Log.d(TAG, "Wi-Fi enabled");		//	TESTING
				finish();
				
			}else if (mobile.isChecked()){Intent mobileIntent=new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
				ComponentName cName = new ComponentName("com.android.phone","com.android.phone.Settings");
				mobileIntent.setComponent(cName);
				startActivity(mobileIntent);
				Toast.makeText(this, "Click on \"Data enabled\" and then return", Toast.LENGTH_LONG).show();
//				Log.d(TAG, "Mobile internet activation screen opened");		//	TESTING
				finish();
				
			}else{
				Toast.makeText(this, "Please make a selection or cancel", Toast.LENGTH_SHORT).show();
//				Log.d(TAG, "Nothing selected to confirm");		//	TESTING
			}
			break;
			
		case R.id.changeCancel:
//			Log.d(TAG, "Task canceled");		//	TESTING
			finish();
			break;
		}
	}//	END onClick()
}
