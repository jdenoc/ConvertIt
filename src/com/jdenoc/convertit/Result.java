package com.jdenoc.convertit;
// Result.java
// GUI: output.xml
// Author: Denis O'Connor
// Last modified: 28/6/12
// Displays the final result of the conversion process

import android.app.Activity;
import android.os.Bundle;
//import android.util.Log;		//	TESTING
import android.widget.TextView;

public class Result extends Activity{

	private TextView output;
	private Object value;
	private String unit, decimalPoint;
//	private final String TAG = "Result";		// TESTING
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.output);
		
		output = (TextView) findViewById(R.id.outputValue);
		
		Bundle data = getIntent().getExtras();
		if(data.getBoolean("num")){		// a check to see if what is being output is a number
			unit = ' '+data.getString("unit");
			value = data.getDouble("value");
			decimalPoint = data.getString("decimal");
			
			output.setText(String.format(decimalPoint, (Double) value)+unit);
		}else{
			value = data.getString("value");
			output.setText((String) value);
		}
//		Log.d(TAG, "Result printed");		// TESTING
	}// END onCreate()

}
