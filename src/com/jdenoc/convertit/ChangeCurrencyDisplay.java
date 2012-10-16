package com.jdenoc.convertit;
// ChangeCurrencyDisplay.java
// GUI: settings_change.xml
// Author: Denis O'Connor
// Last Modified: 28/6/12
// Changes how Currencies are displayed. i.e.: USD or United States Dollar

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

public class ChangeCurrencyDisplay extends Activity implements OnClickListener{

	private TextView main, head;
	private Button confirm, cancel;
	private RadioButton full, three;
//	private final String TAG = "ChangeCurrencyDisplay";		//	TESTING
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_change);
		
		head = (TextView) findViewById(R.id.changeHeading);
		main = (TextView) findViewById(R.id.changeMainText);
		three = (RadioButton) findViewById(R.id.changeOp1);
		full = (RadioButton) findViewById(R.id.changeOp2);
		confirm = (Button) findViewById(R.id.changeConfirm);
		cancel = (Button) findViewById(R.id.changeCancel);
		
		head.setVisibility(View.GONE);
		main.setText("For Currency selection, you can choose the format that currencies are displayed to you.\nPlease select one of the following formats:");
		three.setText(" Short\n (i.e.: USD)");
		three.setOnClickListener(this);
		full.setText(" Long\n (i.e.: United States Dollar)");
		full.setOnClickListener(this);
		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
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
//			Log.d(TAG, "Short selected");		//	TESTING
			three.setChecked(true);
			full.setChecked(false);
			break;
			
		case R.id.changeOp2:
//			Log.d(TAG, "Long selected");		//	TESTING
			three.setChecked(false);
			full.setChecked(true);
			break;
			
		case R.id.changeConfirm:
			SharedPreferences settings = getSharedPreferences("settings", MODE_WORLD_READABLE);
			SharedPreferences.Editor settingsEditor = settings.edit();
			boolean set = false;
			if (three.isChecked()){
				settingsEditor.putBoolean("currency", false);
				settingsEditor.commit();
				set = true;
			}else if(full.isChecked()){
				settingsEditor.putBoolean("currency", true);
				settingsEditor.commit();
				set = true;
			}else{
				Toast.makeText(this, "Please make a selection or cancel", Toast.LENGTH_SHORT).show();
			}
			
			if(set){
//				Log.d(TAG, "Full currency name:"+settings.getBoolean("currency", false));		//	TESTING
				finish();
				Toast.makeText(this, R.string.newSetting, Toast.LENGTH_SHORT).show();
			}
			break;
			
		case R.id.changeCancel:
//			Log.d(TAG, "Change cancelled");
			finish();
			break;
		}
	}// END onClick()

}
