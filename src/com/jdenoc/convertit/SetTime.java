package com.jdenoc.convertit;
// SetTime.java
// GUI: set_time.xml
// Author: Denis O'Connor
// Last Modified: 28/6/12
// Allows the user to set a custom time

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.util.Log;		//	TESTING
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;

public class SetTime extends Activity implements OnClickListener{

	private Button set, cancel;
	private TimePicker time;
//	private final String TAG = "Set Time";		//	TESTING
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_time);
		
		set = (Button) findViewById(R.id.setTimeButton);
		cancel = (Button) findViewById(R.id.cancelTimeButton);
		time = (TimePicker) findViewById(R.id.selectTime);
		
		SharedPreferences settings = getSharedPreferences("settings", MODE_WORLD_READABLE);
		
		time.setIs24HourView(settings.getBoolean("time", true));
		set.setOnClickListener(this);
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
		case R.id.setTimeButton:
			Intent databackIntent = new Intent();
			databackIntent.putExtra("hour", time.getCurrentHour());
			databackIntent.putExtra("min", time.getCurrentMinute());
			setResult(Activity.RESULT_OK, databackIntent);
			finish();
			break;
			
		case R.id.cancelTimeButton:
//			Log.d(TAG, "canceled");		//	TESTING
			setResult(RESULT_CANCELED);
			finish();
			break;
		}
	}// END onClick()

}
