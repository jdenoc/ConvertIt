package com.jdenoc.convertit;
// Help.java
// GUI: help.xml
//		expanded_group_layout.xml	(for group list title)
//		expanded_child_layout.xml	(for expanded list display)
// Author: Denis O'Connor
// Last Modified: 04/8/12
// Displays an expandable list that will allow the user to search through the help sections for each conversion

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
//import android.util.Log;		//	TESTING
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

public class Help extends ExpandableListActivity {
	
	private final String G_KEY = "Help Topic";
	private final String C_KEY = "Topic Details";
//	private final String TAG = "HELP";		//	TESTING
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		// removes title bar
		setContentView(R.layout.help);
		
		SimpleExpandableListAdapter expListAdapter = new SimpleExpandableListAdapter(
		        this,								//																		context
		        createGroupList(),              	// Creating group List.													groupData
		        R.layout.expand_group_layout,      	// Group item layout XML.												groupLayout
		        new String[] {G_KEY},  				// the key of group item.												groupFrom
		        new int[] { R.id.group_name },   	// ID of each group item.-Data under the key goes into this TextView. 	groupTo
		        createChildList(),					// childData describes second-level entries.							childData
		        R.layout.expand_child_layout, 		// Layout for sub-level entries(second level).							childLayout
		        new String[] {C_KEY},     			// Keys in childData maps to display.									childFrom
		        new int[] { R.id.grp_child}     	// Data under the keys above go into these TextViews.					childTo
	        );
	        setListAdapter( expListAdapter );	// setting the adapter in the list.
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}// END onPause()
	
	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//		Log.d(TAG, "Inside onChildClick at groupPosition = " + groupPosition +" Child clicked at position " + childPosition);		//	TESTING
        if(childPosition == 2){
			switch(groupPosition){
	        case 0:		// Currency
	        	if(new CurrencyFunctions().checkInternetConnectivity(
						true,
						(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE),
						(WifiManager) this.getSystemService(Context.WIFI_SERVICE),
						this
					) == -1){
						startActivity(new Intent("com.jdenoc.convertit.ACCESSINTERNET"));	// start AccessInternet activity
					}
	        	break;
	        case 1:		// Temperature
	        	startActivity(new Intent(this, ChangeDecimal.class));
	        	break;
	        case 2:		// Lengths
	        	startActivity(new Intent(this, ChangeDecimal.class));
	        	break;
	        case 3:		// Volume
	        	startActivity(new Intent(this, ChangeDecimal.class));
	        	break;
	        case 4:		// Mass
	        	startActivity(new Intent(this, ChangeDecimal.class));
	        	break;
	        case 5:		// Time
	        	startActivity(new Intent(this, ChangeTimeDisplay.class));
	        	break;
	        case 6:		// Speed
	        	startActivity(new Intent(this, ChangeDecimal.class));
	        	break;
			}
        }else if(childPosition == 4){
        	if(groupPosition == 0){		// Currency
        		startActivity(new Intent(this, ChangeCurrencyDisplay.class));
        	}
        }
		
		return super.onChildClick(parent, v, groupPosition, childPosition, id);
	}// END onChildClick()

	@Override
	public void onGroupExpand(int groupPosition) {
		super.onGroupExpand(groupPosition);
		try{
//			Log.d(TAG, "Group exapanding Listener => groupPosition = " + groupPosition);		//	TESTING
		}catch(Exception e){
//			Log.e(TAG, " groupPosition Error:" + e.getMessage());		//	TESTING
		}
	}// END onGroupExpand()

	public List<HashMap<String, String>> createGroupList(){
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        
        result.add(HashMapMaker(G_KEY,"Currency"));
        result.add(HashMapMaker(G_KEY,"Temperature"));
        result.add(HashMapMaker(G_KEY,"Length"));
        result.add(HashMapMaker(G_KEY,"Volume"));
        result.add(HashMapMaker(G_KEY,"Mass"));
        result.add(HashMapMaker(G_KEY,"Time"));
        result.add(HashMapMaker(G_KEY,"Speed"));
        result.add(HashMapMaker(G_KEY,"Colour"));
        
        return (List<HashMap<String, String>>)result;
	}// END createGroupList()
  
	private HashMap<String, String> HashMapMaker(String key, String item){
		//creates and returns a HashMap
		HashMap<String, String> m = new HashMap<String, String>();
		m.put(key, item);
		return m;
	}// END HashMapMaker()
	
	private List<ArrayList<HashMap<String, String>>> createChildList() {
    	ArrayList<ArrayList<HashMap<String, String>>> result = new ArrayList<ArrayList<HashMap<String, String>>>();		// overall list of children
     	ArrayList<HashMap<String, String>> currency = new ArrayList<HashMap<String, String>>();				// list of currency help info
     	ArrayList<HashMap<String, String>> temp = new ArrayList<HashMap<String, String>>();					// list of temperature help info
     	ArrayList<HashMap<String, String>> length = new ArrayList<HashMap<String, String>>();				// list of length help info
     	ArrayList<HashMap<String, String>> volume = new ArrayList<HashMap<String, String>>();				// list of volume help info
     	ArrayList<HashMap<String, String>> mass = new ArrayList<HashMap<String, String>>();					// list of mass help info
     	ArrayList<HashMap<String, String>> time = new ArrayList<HashMap<String, String>>();					// list of time help info
     	ArrayList<HashMap<String, String>> speed = new ArrayList<HashMap<String, String>>();				// list of speed help info
     	ArrayList<HashMap<String, String>> colour = new ArrayList<HashMap<String, String>>();				// list of colour help info
     	
     	currency.add(HashMapMaker(C_KEY, getResources().getString(R.string.currencyHelp0)));
     	currency.add(HashMapMaker(C_KEY, getResources().getString(R.string.currencyHelp1)));
     	currency.add(HashMapMaker(C_KEY, getResources().getString(R.string.tap)));	// interactive (group 0, position 2)
     	currency.add(HashMapMaker(C_KEY, getResources().getString(R.string.currencyHelp2)));
     	currency.add(HashMapMaker(C_KEY, getResources().getString(R.string.tap)));	// interactive (group 0, position 4)
     	currency.add(HashMapMaker(C_KEY, getResources().getString(R.string.currencyHelp3)));
     	result.add(currency);	// add currency help to overall list
     	
     	temp.add(HashMapMaker(C_KEY, getResources().getString(R.string.tempHelp0)));
     	temp.add(HashMapMaker(C_KEY, getResources().getString(R.string.tempHelp1)));
     	temp.add(HashMapMaker(C_KEY, getResources().getString(R.string.tap)));		// interactive (group 1, position 2)
     	temp.add(HashMapMaker(C_KEY, getResources().getString(R.string.decDefault)));
     	result.add(temp);	// add temperature help to overall list 
     	
     	length.add(HashMapMaker(C_KEY, getResources().getString(R.string.lenHelp0)));
     	length.add(HashMapMaker(C_KEY, getResources().getString(R.string.lenHelp1)));
        length.add(HashMapMaker(C_KEY, getResources().getString(R.string.tap)));	// interactive (group 2, position 2)
        length.add(HashMapMaker(C_KEY, getResources().getString(R.string.decDefault)));
        length.add(HashMapMaker(C_KEY, getResources().getString(R.string.lenHelp2)));
        result.add(length);		// add length help to overall list
        
        volume.add(HashMapMaker(C_KEY, getResources().getString(R.string.volHelp0)));
        volume.add(HashMapMaker(C_KEY, getResources().getString(R.string.volHelp1)));
        volume.add(HashMapMaker(C_KEY, getResources().getString(R.string.tap)));	// interactive (group 3, position 2)
        volume.add(HashMapMaker(C_KEY, getResources().getString(R.string.decDefault)));
        volume.add(HashMapMaker(C_KEY, getResources().getString(R.string.volHelp2)));
        result.add(volume);	// add volume help to overall list
        
        mass.add(HashMapMaker(C_KEY, getResources().getString(R.string.massHelp0)));
        mass.add(HashMapMaker(C_KEY, getResources().getString(R.string.massHelp1)));
        mass.add(HashMapMaker(C_KEY, getResources().getString(R.string.tap)));		// interactive (group 4, position 2)
        mass.add(HashMapMaker(C_KEY, getResources().getString(R.string.decDefault)));
        result.add(mass);	// add mass help to overall list
        
        time.add(HashMapMaker(C_KEY, getResources().getString(R.string.timeHelp0)));
        time.add(HashMapMaker(C_KEY, getResources().getString(R.string.timeHelp1)));
        time.add(HashMapMaker(C_KEY, getResources().getString(R.string.tap)));		// interactive (group 5, position 2) 
        result.add(time);	// add time help to overall list
        
        speed.add(HashMapMaker(C_KEY, getResources().getString(R.string.speedHelp0)));
        speed.add(HashMapMaker(C_KEY, getResources().getString(R.string.speedHelp1)));
        speed.add(HashMapMaker(C_KEY, getResources().getString(R.string.tap)));		// interactive (group 6, position 2)
        speed.add(HashMapMaker(C_KEY, getResources().getString(R.string.decDefault)));
        speed.add(HashMapMaker(C_KEY, getResources().getString(R.string.speedHelp2)));
        result.add(speed);	// add speed help to overall list
        
        colour.add(HashMapMaker(C_KEY, getResources().getString(R.string.colorHelp0)));
        colour.add(HashMapMaker(C_KEY, getResources().getString(R.string.colorHelp1)));
        result.add(colour);	// add colour help to overall list
        
        return result;
    }//	END createChildList()
}