package com.jdenoc.convertit;
// NoConnection.java
// GUI: output.xml
// Author: Denis O'Connor
// Last modified: 26/6/12
// Displays a message indicating that the App is not able to make a connection to retrieve currency rates

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NoConnection extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.output);
		
		TextView output = (TextView) findViewById(R.id.outputValue);
		output.setText("No Connection available.\nPlease check your internet connection settings.");
	}// END onCreate()

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}// END onPause()
}
