package com.jdenoc.convertit;
// CurrencyConvertIt.java
// GUI:	currency.xml
//		convert_menu.xml (for menu)
// Author: Denis O'Connor
// Last modified: 04/8/12
// Allows the user to convert between two currencies

import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;		//	TESTING
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CurrencyConvertIt extends Activity implements OnClickListener{

	private ImageButton convert;							// interactive
	private Spinner from, to, regionFrom, regionTo;	// interactive
	private EditText input;							// interactive
	private ProgressDialog loading;
	private SharedPreferences settings;
	private String fromResponce, toResponce;			// contains the value associated with the spinners
	private Hashtable<String, String> currencyNameTable;
	private Hashtable<String, ArrayList<String>> spinners;
	private final String TAG = "ConvertIt:Currency";	// TESTING 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		// removes title bar
		setContentView(R.layout.currency);
		Log.d(TAG, "Currency started");		// TESTING
		
		// Setup interactive XML references
		input = (EditText) findViewById(R.id.convertInput);
		regionFrom = (Spinner) findViewById(R.id.regionFrom);
		regionTo = (Spinner) findViewById(R.id.regionTo);
		from = (Spinner) findViewById(R.id.spinnerFrom);
		to = (Spinner) findViewById(R.id.spinnerTo);
		convert = (ImageButton) findViewById(R.id.doConversion);
		
		settings = getSharedPreferences("settings", MODE_WORLD_READABLE);
		
		// Setup click/touch listeners
		convert.setOnClickListener(this);

		if(!setup()){
			setResult(666, new Intent());
			finish();
			
		}else{
//			Spinner selectors
			String[] regionSpinner = spinners.keySet().toArray(new String[spinners.keySet().toArray().length]);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_item_text, regionSpinner);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//			
			regionFrom.setAdapter(adapter);
			regionFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			        String region = parent.getItemAtPosition(pos).toString();
			        String[] values = spinners.get(region).toArray(new String[spinners.get(region).toArray().length]);
			        changeFrom(values);
//			        Log.d(TAG, region+" selected");		//	TESTING
			    }
			    public void onNothingSelected(AdapterView<?> parent) {
			    }
			});
//			
			String[] valuesFrom = spinners.get(regionSpinner[0]).toArray(new String[spinners.get(regionSpinner[0]).toArray().length]);
			changeFrom(valuesFrom);
			
			regionTo.setAdapter(adapter);
			regionTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			    	String region = parent.getItemAtPosition(pos).toString();
			        String[] values = spinners.get(region).toArray(new String[spinners.get(region).toArray().length]);
			        changeTo(values);
//			        Log.d(TAG, region+" selected");		//	TESTING
			    }
			    public void onNothingSelected(AdapterView<?> parent) {
			    }
			});
			
			String[] valuesTo = spinners.get(regionSpinner[0]).toArray(new String[spinners.get(regionSpinner[0]).toArray().length]);
			changeTo(valuesTo);
//			END Spinner selectors
		}
	}// END onCreate()
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.doConversion:
			String info = input.getText().toString();	// outputs whatever was entered into the input text box
			
			if (info.length() == 0){
				Toast.makeText(this, "Please input a value for conversion", Toast.LENGTH_SHORT).show();
//				Log.d(TAG, "Value not present");		//	TESTING
			}else{
//				Individual conversions begin
				
				String decimalPoint = "%.2f";
		        String intentString = "com.jdenoc.convertit.RESULT";
		        Bundle data = new Bundle();
				
//				Starts the "Loading" Progress Dialog
				loading = ProgressDialog.show(this, "", "Converting...");
				new Thread(){
					public void run(){
						try{
							sleep(2500);		// runs for 2.5 secs
						}catch(Exception e){
//							Log.e(TAG, e.getMessage());		//	TESTING
						}finally{
							loading.dismiss();	// Finishes the Loading Dialog
//							Log.d(TAG, "Loading complete!");		//	TESTING
						}
					}
				}.start();
//				END of Thread
				
				if(fromResponce.equals(toResponce)){
					data.putBoolean("num", true);
					data.putString("decimal", decimalPoint);
					data.putString("unit", toResponce);
					data.putDouble("value", Double.parseDouble(info));
				}else{
//					Log.d(TAG, "Begin Currency Conversion");		//	TESTING
					CurrencyFunctions rates = new CurrencyFunctions(fromResponce, toResponce, Double.parseDouble(info), 
																	settings.getBoolean("currency", false), currencyNameTable);
					boolean goodToGo = rates.rateRetrival(getResources().getString(R.string.currencySite));
					if(goodToGo){
						data.putBoolean("num", true);
						data.putString("decimal", decimalPoint);
						data.putString("unit", toResponce);
						data.putDouble("value", rates.processRateAndConvert());
					}else{
						intentString = "com.jdenoc.convertit.NOCONNECTION";
					}
									
				}
				
				Intent file = new Intent(intentString);
				file.putExtras(data);	// bundle is attached to an intent
				startActivity(file);	// start ConvertIt activity with parameter sent
			}
			break;
		}
	}// END onClick()
	
//	Specifically for phones. When press MENU button on phones 
	public boolean onCreateOptionsMenu(Menu menu){		// Should be (Menu menu), but Menu is the name of the class, so there is a conflict error
		super.onCreateOptionsMenu(menu);
		MenuInflater awesome = getMenuInflater();
		awesome.inflate(R.menu.convert_menu, menu);
//		Log.d(TAG, "'MENU' button pressed");		// TESTING
		return true;
	}//	END onCreateOptionsMenu()

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.menuAbout:
//			Displays About.java file
			startActivity(new Intent("com.jdenoc.convertit.ABOUT"));
			return true;
			
		case R.id.menuReset:
//			Resets the current activity
			finish();
			startActivity(getIntent());
//			Log.d(TAG, "Activity Reset");		//	TESTING
			return true;
			
		case R.id.menuSettings:
//			Displays Settings.java file
			startActivity(new Intent("com.jdenoc.convertit.SETTINGS"));
			return true;
			
		case R.id.menuHelp:
//			Displays a help menu, depending on the type of conversion being made
			startActivity(new Intent("com.jdenoc.convertit.HELP"));
			return true;
		}
		return false;
	}//	END onOptionsItemSelected()
//END Menu Button setup
	
	public boolean setup(){
		Log.d(TAG, "Initialising setup...");		//	TESTING
		CurrencyFunctions types = new CurrencyFunctions(settings.getBoolean("currency", false));
		spinners = types.getCurrencyArray();		// Either "title" for short or "description" for long
		if(spinners == null ){
			Log.d(TAG, "Connection Error ;_;");		//	TESTING
			return false;
		}else{
			currencyNameTable = types.getStoredKeys();
			Log.d(TAG, "Good to go ^_^");		//	TESTING
			return true;
		}
	}//	END setup()
	
//	Extra Spinner functions for updating
	public void changeFrom(String[] fromValues){
		ArrayAdapter<String> adapterFrom = new ArrayAdapter<String>(this, R.layout.simple_item_text, fromValues);
		adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		from.setAdapter(adapterFrom);
		from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        fromResponce = parent.getItemAtPosition(pos).toString();
//		        Log.d(TAG, fromResponce+" selected");		//	TESTING
		    }
		    public void onNothingSelected(AdapterView<?> parent){
		    }
		});
	}//	END changeFrom()
	
	public void changeTo(String[] toValues){
		ArrayAdapter<String> adapterTo = new ArrayAdapter<String>(this, R.layout.simple_item_text, toValues);
		adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		to.setAdapter(adapterTo);
		to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		    	toResponce = parent.getItemAtPosition(pos).toString();
//		        Log.d(TAG, toResponce+" selected");		//	TESTING
		    }
		    public void onNothingSelected(AdapterView<?> parent){
		    }
		});
	}//	END changeTo()
//	END Extra Spinner functions
}
