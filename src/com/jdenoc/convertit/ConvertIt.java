package com.jdenoc.convertit;
// ConvertIt.java
// GUI:	convert.xml
//		convert_menu.xml (for menu)
// Author: Denis O'Connor
// Last modified: 28-OCT-2012
// Main Conversion file
// Allows user to convert Mass, Volume, Length, Speed and Temperature

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.util.Log;		//	TESTING
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
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("WorldReadableFiles")
public class ConvertIt extends Activity implements OnClickListener{

	private ImageButton convert;	// interactive
	private Spinner from, to;		// interactive
	private TextView title;			// interactive
	private EditText input;			// interactive
	private ProgressDialog loading;
	private String fromResponce, toResponce;	// contains the value associated with the spinners
	private String head;			// to contain the title of the conversion
	private int spinnerArray;		// to contain the id of the array for the spinners 
//	private String TAG;				// TESTING 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Bundle data = getIntent().getExtras();
		head = data.getString("title");			// retrieve title from previous Main.java
		spinnerArray = data.getInt("spinner");	// retrieve resource ID for the spinner arrays 
//		TAG = "ConvertIt:"+head;				// TESTING
		
		super.onCreate(savedInstanceState);
		if(SDKVersion.useActionBar()){		
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}else{
			requestWindowFeature(Window.FEATURE_NO_TITLE);		// removes title bar
		}
		setContentView(R.layout.convert);
//		Log.d(TAG, head+" started");		// TESTING
		
		// Setup interactive XML references
		title = (TextView) findViewById(R.id.convertTitle);
		input = (EditText) findViewById(R.id.convertInput);
		from = (Spinner) findViewById(R.id.spinnerFrom);
		to = (Spinner) findViewById(R.id.spinnerTo);
		convert = (ImageButton) findViewById(R.id.doConversion);
		
		// Setup click/touch listeners
		convert.setOnClickListener(this);
		title.setText(head);		// Sets the Convert title to information received from previous activity
		
//		Spinner selectors
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, spinnerArray, R.layout.simple_item_text);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    to.setAdapter(adapter);
		from.setAdapter(adapter);
		to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        toResponce = parent.getItemAtPosition(pos).toString();
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        fromResponce = parent.getItemAtPosition(pos).toString();
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
//		END Spinner selectors	
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
				SharedPreferences settings = getSharedPreferences("settings", MODE_WORLD_READABLE);
				double finalResult=0;
				String decimalPoint = "%."+settings.getInt("decimal", 3)+"f";;
				
//				Starts the "Loading" Progress Dialog
				loading = ProgressDialog.show(this, "", "Converting...");
				new Thread(){
					public void run(){
						try{
							sleep(2250);		// runs for 2.25 seconds
						}catch(Exception e){
//							Log.e(TAG, e.getMessage());		//	TESTING
						}finally{
							loading.dismiss();	// Finishes the Loading Dialog
//							Log.d(TAG, "Loading complete!");		//	TESTING
						}
					}
				}.start();
//				END of Thread
				
				ConversionFunctions rates = new ConversionFunctions(fromResponce, toResponce, Double.parseDouble(info));
				if(fromResponce.equals(toResponce)){
					finalResult = Double.parseDouble(info);
				}else if(head.equals("Temperature")){
// ***** TEMPERATURE *****
//					Log.d(TAG, "Begin Temperature Conversion");		//	TESTING
					finalResult = rates.getTemperature();
// ***** END TEMPERATURE *****
				}else{
// ***** LENGTH, MASS, VOLUME *****
					finalResult = rates.convert(rates.getConversion(this, head));
// ***** END LENGTH, MASS, VOLUME *****
				}
				
				Bundle data = new Bundle();
				data.putBoolean("num", true);
				data.putString("decimal", decimalPoint);
				data.putString("unit", toResponce);
				data.putDouble("value", finalResult);
				Intent file = new Intent("com.jdenoc.convertit.RESULT");
				file.putExtras(data);	// bundle is attached to an intent
				startActivity(file);	// start ConvertIt activity with parameter sent
			}
			break;
		}
	}// END onClick()
	
//	Specifically for phones. When press MENU button on phones 
	public boolean onCreateOptionsMenu(android.view.Menu menu){		// Should be (Menu menu), but Menu is the name of the class, so there is a conflict error
		super.onCreateOptionsMenu(menu);
		MenuInflater awesome = getMenuInflater();
		awesome.inflate(R.menu.convert_menu, menu);
//		Log.d(TAG, "'MENU' button pressed");		// TESTING
		return true;
	}// END onCreateOptionsMenu()

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.menuAbout:
//			Displays About.java file
			startActivity(new Intent("com.jdenoc.convertit.ABOUT"));
			return true;
			
		case R.id.menuReset:
//			Resets the current activity
			Bundle data = new Bundle();
			Intent file = getIntent();
			data.putString("title", head);
			data.putInt("spinner", spinnerArray);
			file.putExtras(data);
			finish();
			startActivity(file);
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
	}// END onOptionsItemSelected()
//END Menu Button setup
}
