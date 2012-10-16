package com.jdenoc.convertit;
// TimeByLocation.java
// GUI: time_tab.xml
// Author: Denis O'Connor
// Last Modified: 04/8/12
// First tab used in Time Conversion
// Allows user to Convert Time based on location

import java.util.ArrayList;
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

public class TimeByLocation extends Activity implements OnClickListener{

	private Spinner city, region;
	private TextView cityLable, regionLable, currentTime, currentZone;
	private Button setTime, setZone;
	private ImageButton convert;
	private String gotCity, gotRegion;
	private Hashtable<String, ArrayList<String>> locations;
	private TimeFunctions t; 
	private SharedPreferences settings;
//	private final String TAG = "TimeByLocation"; 		//	TESTING
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_tab);
		
		setTime = (Button) findViewById(R.id.setTime);
		setZone = (Button) findViewById(R.id.setZone);
		currentTime = (TextView) findViewById(R.id.currentTime);
		currentZone = (TextView) findViewById(R.id.currentTimeZone);
		regionLable = (TextView) findViewById(R.id.timeLable1);
		region = (Spinner) findViewById(R.id.spinnerTime1);
		cityLable = (TextView) findViewById(R.id.timeLable2);
		city = (Spinner) findViewById(R.id.spinnerTime2);
		convert = (ImageButton) findViewById(R.id.doConversion);
		
		settings = getSharedPreferences("settings", MODE_WORLD_READABLE);
		t = new TimeFunctions(settings.getBoolean("time", true));
		String prompt1 = getResources().getString(R.string.tRegion);
		String prompt2 = getResources().getString(R.string.tCity);
		
		setTime.setOnClickListener(this);
		setZone.setOnClickListener(this);
		currentTime.setText(t.getPublicTime());
		currentZone.setText(t.getPublicZone());
		regionLable.setText(prompt1);
		region.setPrompt(prompt1);
		cityLable.setText(prompt2);
		city.setPrompt(prompt2);
		convert.setOnClickListener(this);
		
//		Spinner selectors
		locations = t.getLocations(new Hashtable<String, ArrayList<String>>()); 
		
		String[] keys =  locations.keySet().toArray(new String[locations.keySet().toArray().length]);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.simple_item_text,  keys);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		region.setAdapter(adapter1);
		region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        gotRegion = parent.getItemAtPosition(pos).toString();
		        String[] values = locations.get(gotRegion).toArray(new String[locations.get(gotRegion).toArray().length]);
		        changeCityList(values);
//		        Log.d(TAG, gotRegion+" selected");		//	TESTING
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		
		String[] values = locations.get(keys[0]).toArray(new String[locations.get(keys[0]).toArray().length]);
		changeCityList(values);
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
			data.putString("value", t.convertTime(gotRegion+"/"+gotCity));
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
	
	public void changeCityList(String[] values){
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.simple_item_text, values);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adapter2);
		city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        gotCity = parent.getItemAtPosition(pos).toString();
//		        Log.d(TAG, gotCity+" selected");		//	TESTING
		    }
		    public void onNothingSelected(AdapterView<?> parent){
		    }
		});
	}// END changeCityList()
	
	@Override
	protected void onResume() {
		super.onResume();
		currentTime.setText(t.getPublicTime());
		currentZone.setText(t.getPublicZone());
	}// END onResume()
	
}
