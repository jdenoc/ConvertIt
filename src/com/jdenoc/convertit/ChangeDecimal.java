package com.jdenoc.convertit;
// ChangeDecimal.java
// GUI: change_decimal.xml
// author: Denis O'Connor
// Last Modified: 29/6/12
// Allows the user to set how many decimal places their conversions will be displayed to 
// Must be between 2 and 8 decimal places, Default is 3
// Doesn't apply to Currency Conversions

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.util.Log;		//	TESTING
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeDecimal extends Activity implements OnClickListener{

	private EditText decimalInput;
	private TextView currentDecimal;
	private Button confirm, cancel, reset;
	private SharedPreferences settings;
	
//	private final String TAG = "ChangeDecimal";		//	TESTING
	private final String KEY = "decimal";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_decimal);
		
//		XML setup
		decimalInput = (EditText) findViewById(R.id.decimalEdit);
		currentDecimal = (TextView) findViewById(R.id.decimalDisplay);
		confirm = (Button) findViewById(R.id.decimalButton);
		cancel = (Button) findViewById(R.id.decimalCancel);
		reset = (Button) findViewById(R.id.decimalReset);
		
//		Make items interactive
		settings = this.getSharedPreferences("settings", MODE_WORLD_READABLE);
		currentDecimal.setText("Current amount of decimal places: "+settings.getInt(KEY, 3));
		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
		reset.setOnClickListener(this);
	}// END onCreate()

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}// END onPause()

	@Override
	public void onClick(View v) {
		SharedPreferences.Editor settingsEditor = settings.edit();
		
		switch(v.getId()){
		case R.id.decimalButton:
			String input = decimalInput.getText().toString();
			if(input.length() == 0){
				Toast.makeText(this, "Please enter a value between 2 and 8", Toast.LENGTH_SHORT).show();
			}else{
//				Log.d(TAG, "input is:"+input);		//	TESTING
				int decimal = Integer.parseInt(input);
				if(decimal < 2 || decimal > 8){
					Toast.makeText(this, "Please enter a value between 2 and 8", Toast.LENGTH_SHORT).show();
//					Log.d(TAG, "Got int error:"+decimal);		//	TESTING
				}else if(decimal >= 2 && decimal <= 8){
					settingsEditor.putInt(KEY, decimal);
					settingsEditor.commit();
					Toast.makeText(this, "Value changed to: "+settings.getInt(KEY, 3), Toast.LENGTH_SHORT).show();
//					Log.d(TAG, "value change to:"+settings.getInt(KEY, 3));		//	TESTING
					finish();
				}else{
					Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
//					Log.d(TAG, "Got int error:"+decimal);		//	TESTING
				}
			}
			break;
			
		case R.id.decimalReset:
//			Log.d(TAG, "Reset decimal");		//	TESTING
			settingsEditor.putInt(KEY, 3);
			settingsEditor.commit();
			Toast.makeText(this, "Value reset to: "+3, Toast.LENGTH_SHORT).show();
			finish();
			break;
	
		case R.id.decimalCancel:
//			Log.d(TAG, "Cancel Decimal Change");		//	TESTING
			finish();
			break;
		}	
	}// END onClick()

}
