package com.jdenoc.convertit;
// TimeByZone.java
// GUI: time_tab.xml
// Author: Denis O'Connor
// Last Modified: 04/8/12
// Second tab for time zone conversion
// Allows user to convert time zone by time zone selection

import java.util.Arrays;
import java.util.Hashtable;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.util.Log;		//	TESTING
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class TimeByZone extends Activity implements OnClickListener{
	
	private Button setTime, setZone;
	private ImageButton convert; 
	private Spinner zone, hiddenSpinner;
	private TextView lable, hiddenLable, currentTime, currentZone;
	private String gotZoneID;
	private TimeFunctions t;
	private SharedPreferences settings;
//	private final String TAG = "TimeByZone";		//	TESTING
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_tab);
		
		setTime = (Button) findViewById(R.id.setTime);
		setZone = (Button) findViewById(R.id.setZone);
		currentTime = (TextView) findViewById(R.id.currentTime);
		currentZone = (TextView) findViewById(R.id.currentTimeZone);
		lable = (TextView) findViewById(R.id.timeLable1);
		hiddenLable = (TextView) findViewById(R.id.timeLable2);
		zone = (Spinner) findViewById(R.id.spinnerTime1);
		hiddenSpinner = (Spinner) findViewById(R.id.spinnerTime2);
		convert = (ImageButton) findViewById(R.id.doConversion);
		
		String spinnerText = getResources().getString(R.string.tZone); 
		settings = getSharedPreferences("settings", MODE_WORLD_READABLE);
		t = new TimeFunctions(settings.getBoolean("time", true));
		
		setTime.setOnClickListener(this);
		setZone.setOnClickListener(this);
		currentTime.setText(t.getPublicTime());
		currentZone.setText(t.getPublicZone());
		lable.setText(spinnerText);
		zone.setPrompt(spinnerText);
		hiddenLable.setVisibility(View.GONE);
		hiddenSpinner.setVisibility(View.GONE);
		convert.setOnClickListener(this);

//		Spinner selectors
		final Hashtable<String, String> table = t.getTimezones();
		String[] keys = table.keySet().toArray(new String[table.keySet().toArray().length]);
		Arrays.sort(keys);	// Sorts the keys array in Alphabetical order
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_item_text, keys);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		zone.setAdapter(adapter);
		
		zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		    	gotZoneID = table.get(parent.getItemAtPosition(pos).toString());
//		    	Log.d(TAG, gotZoneID);		//	TESTING
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
//		END Spinner selectors		
	}// END onCreate()

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.setZone:
//			sets a custom time zone
			startActivityForResult(new Intent("com.jdenoc.convertit.SETZONE"), 12345);
			break;
			
		case R.id.setTime:
//			sets a custom time
			startActivityForResult(new Intent("com.jdenoc.convertit.SETTIME"), 55555);
			break;
		
		case R.id.doConversion:
//			performs conversion
			Bundle data = new Bundle();
			Intent file = new Intent("com.jdenoc.convertit.RESULT");
			data.putBoolean("num", false);
			data.putString("value", t.convertTime(gotZoneID));
			file.putExtras(data);
			startActivity(file);
			break;
		}
	}// END onClick()
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		 // See which sub activity has finished   
		switch (requestCode) {
		case 55555: 		// SetTime.java finished
			if (resultCode == RESULT_OK) {// Check resultCode to see if it finished correctly
				if (data != null) {
					int hour = data.getIntExtra("hour", 0);
					int min = data.getIntExtra("min", 0);

					currentTime.setText(t.formatTime(hour, min, ""));
					t.setPublicTime(t.formatTime(hour, min, ""));
				}
				
			}
			break;
			
		case 12345:			// SetZone.java finished
			if (resultCode == RESULT_OK) {// Check resultCode to see if it finished correctly
				if (data != null) {
					currentZone.setText(data.getStringExtra("zoneName"));
					t.setPublicZone(data.getStringExtra("zoneName"));
					t.setPublicZoneID(data.getStringExtra("zoneID"));
				}
			}
			break;
			
		}
	}// END onActivityResult()

	@Override
	protected void onResume() {
		super.onResume();
		currentTime.setText(t.getPublicTime());
		currentZone.setText(t.getPublicZone());
	}// END onResume()
}
