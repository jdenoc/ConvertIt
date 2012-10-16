package com.jdenoc.convertit;
// SetZone.java
// GUI: set_zone.xml
// Author: Denis O'Connor
// Last Modified: 04/8/12
// Allows the user to set a different time zone that the one they are currently in

import java.util.Arrays;
import java.util.Hashtable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SetZone extends Activity implements OnClickListener{

	private Button confirm, cancel;
	private Spinner zone;
	private String gotZoneID, gotZone;
	private TimeFunctions t = new TimeFunctions();;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_zone);
		
		confirm = (Button) findViewById(R.id.setTimeButton);
		cancel = (Button) findViewById(R.id.cancelTimeButton);
		zone = (Spinner) findViewById(R.id.spinnerZone);
		
		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
		
//		Spinner selectors
		final Hashtable<String, String> table = t.getTimezones();
		String[] keys = table.keySet().toArray(new String[table.keySet().toArray().length]);
		Arrays.sort(keys);	// Sorts the keys array in Alphabetical order
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_item_text, keys);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		zone.setAdapter(adapter);
		
		zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		    	gotZone = parent.getItemAtPosition(pos).toString();
		    	gotZoneID = table.get(gotZone);
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
//		END Spinner selectors
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
			databackIntent.putExtra("zoneName", gotZone);
			databackIntent.putExtra("zoneID", gotZoneID);
			setResult(Activity.RESULT_OK, databackIntent);
			finish();
			break;
		
		case R.id.cancelTimeButton:
			finish();
			break;
		}
	}// END onClick()

}
