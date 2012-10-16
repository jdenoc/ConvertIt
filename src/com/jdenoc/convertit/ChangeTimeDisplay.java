package com.jdenoc.convertit;
// ChangeTimeDisplay.java
// GUI: settings_change.xml
// Author: Denis O'Connor
// Last Modified: 28/6/12
// Allows the user to set how Time will be displayed. i.e: "13:00" or "1:00 p.m." 

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.util.Log;		//	TESTING
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeTimeDisplay extends Activity implements OnClickListener{

	private Button confirm, cancel;
	private RadioButton twentyFour, twelve;
	private TextView main, head;
//	private final String TAG = "ChangeTimeDisplay";		//	TESTING
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_change);
		
		head = (TextView) findViewById(R.id.changeHeading);
		main = (TextView) findViewById(R.id.changeMainText);
		confirm = (Button) findViewById(R.id.changeConfirm);
		cancel = (Button) findViewById(R.id.changeCancel);
		twentyFour = (RadioButton) findViewById(R.id.changeOp1);
		twelve = (RadioButton) findViewById(R.id.changeOp2);
		
		head.setVisibility(View.GONE);
		main.setText("Here you can choose the format that time is displayed to you.\nPlease select one of the following formats:");
		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
		twentyFour.setText(" Twenty Four Hour Clock\n (i.e.: 13:00)");
		twentyFour.setOnClickListener(this);
		twelve.setText(" Twelve Hour Clock\n (i.e.: 1:00 p.m.)");
		twelve.setOnClickListener(this);
	}// END onCreate()

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}// END onPause()

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.changeOp1:
			twentyFour.setChecked(true);
			twelve.setChecked(false);
//			Log.d(TAG, "24 checked");		//	TESTING
			break;
			
		case R.id.changeOp2:
			twelve.setChecked(true);
			twentyFour.setChecked(false);
//			Log.d(TAG, "12 checked");		//	TESTING
			break;
			
		case R.id.changeCancel:
			finish();
			break;
			
		case R.id.changeConfirm:
			SharedPreferences settings = getSharedPreferences("settings", MODE_WORLD_READABLE);
			SharedPreferences.Editor settingsEditor = settings.edit();
			boolean set = false;
			if (twentyFour.isChecked()){
				settingsEditor.putBoolean("time", true);
				settingsEditor.commit();
				set = true;
			}else if(twelve.isChecked()){
				settingsEditor.putBoolean("time", false);
				settingsEditor.commit();
				set = true;
			}else{
				Toast.makeText(this, "Please make a selection or cancel", Toast.LENGTH_SHORT).show();
			}
			
			if(set){
				finish();
				Toast.makeText(this, R.string.newSetting, Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}// END onClick()
}
