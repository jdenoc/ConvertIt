package com.jdenoc.convertit;
// Currency.java
// GUI: n/a
// Author: Denis O'Connor
// Last modified: 05/7/12
// Contains functions for Currency conversions

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
//import android.util.Log;		//	TESTING
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class CurrencyFunctions extends Activity{

	private Hashtable<String, String> ratesTable = new Hashtable<String, String>();	// to contain the rates of conversion 
	private Hashtable<String, String> keyStore;	// used to gather both long & short currency names
	private String convertFrom, convertTo;
	private double value;
	private boolean fullCurrency;
//	private final String TAG = "CurrencyModule";		//	TESTING
	
	public CurrencyFunctions(){
//		Constructor - DOES NOTHING
	}// END Constructor
	
	
	public CurrencyFunctions(boolean format){
//		Constructor
		fullCurrency = format;
	}// END Constructor
	
	public CurrencyFunctions(String from, String to, double input, boolean format, Hashtable<String, String> currencyNameTable){
//		Constructor
		value = input;
		convertFrom = from;
		convertTo = to;
		fullCurrency = format;
		if(fullCurrency){
			keyStore = currencyNameTable;
		}
	}// END Constructor
	
	public boolean rateRetrival(String serverURL){
//		Retrieve the currency conversion rate 
		final String NODE = "item";
		final String RATE = "description";
		final String NAME = "title";
		
		if(fullCurrency){
			serverURL += keyStore.get(convertFrom).substring(0, 3)+"/rss.xml";
		}else{
			serverURL += convertFrom+"/rss.xml";
		}
		
		String xml = xmlHTTPconnect(serverURL);
		if(xml == null){
//			if there is a connection failure
			return false;
		}
		Document doc = getDomElements(xml);
		if(doc==null){
//			if there is a reading failure
			return false;
		}
			
		NodeList items = doc.getElementsByTagName(NODE);
		for (int i=0; i<items.getLength(); i++){
			Element e = (Element) items.item(i);
//			The key = <title>, i.e.: EUR/USD, value = <description>, i.e.: String containing exchange rate info
			ratesTable.put(getValue(e, NAME), getValue(e, RATE));
//			Log.d(TAG, "KEY="+getValue(e, NAME)+", VALUE="+getValue(e, RATE));		// TESTING (slows down app when active)
		}
		return true;	
	}// END rateRetrival()
	
	public Hashtable<String, ArrayList<String>> getCurrencyArray(){
//		retrieves the values for an array containing many currency names
		final String NODE = "item";			// for the node the info is stored
		final String KEY = "category";		// for the region of the currency
		final String NAME = "title";		// for the 3 letter representation of the currency
		final String INFO = "description";	// for the full name of the currency
		Hashtable<String, ArrayList<String>> arrays = new Hashtable<String, ArrayList<String>>();	// Hashtable to return
		
		String xml = xmlHTTPconnect("http://themoneyconverter.com/rss-feed/USD/rss.xml");
		if(xml == null){//	if there is a connection failure
			return null;
		}
		
		Document doc = getDomElements(xml);
		if(doc==null){//	if there is a reading failure
			return null;
		}
				
		NodeList items = doc.getElementsByTagName(NODE);		// obtain list of nodes
		if(fullCurrency){
			keyStore = new Hashtable<String, String>();
		}
		for (int i=0; i<items.getLength(); i++){
			ArrayList<String> currencies;
			Element e = (Element) items.item(i);				// cast a node from the list of nodes as an Element 
			String key = getValue(e, KEY);		// Obtain region name
			String money;
			if(fullCurrency){
				money = cleanInput(getValue(e, INFO)); 	// Obtain the full currency name
				keyStore.put(money, getValue(e, NAME));
//				Log.d(TAG, "into keyStore:"+getValue(e, NAME)+,"+money);	// slows app down when active
			}else{
				money = cleanInput(getValue(e, NAME));	// Obtain the 3 letter currency name
			}
					
			if(arrays.containsKey(key)){//	Check to see if currency value is already in the Hashtable
				currencies = (ArrayList<String>) arrays.get(key);	
				if(!currencies.contains(money)){
					if((money.equals("EUR")) ||(money.equals("Euro"))){		// Adds Euro to top of European list
						currencies.add(0, money);
					}else{
						currencies.add(money);
					}
					arrays.put(key, currencies);
				}
			}else{
				currencies = new ArrayList<String>();
				currencies.add(money);
				arrays.put(key, currencies);
			}
//			Log.d(TAG, key+", "+money);		// TESTING (slows down app when active)
		}
		ArrayList<String> extra = (ArrayList<String>) arrays.get("North America");
		if(!fullCurrency){
			extra.add(0, "USD");
		}else{
			extra.add(0, "United States Dollar");
			keyStore.put("United States Dollar", "USD/");
		}
		arrays.put("North America", extra);
		
		return arrays;
	}//	END getCurrencyArray()
	
	public String cleanInput(String value){
//		Log.d(TAG, "Process "+value+" to array");	// slows down app when active
		if (!fullCurrency){
			return value.substring(0, value.indexOf('/'));
			
		}else{
			String[] tokens = value.split(" ");
			String name=null;
			boolean checked=false;
			for(int i=0; i<tokens.length; i++){
				if(checked){
					if(name == null){
						name = tokens[i];
					}else{
						name = name+" "+tokens[i];
					}
				}
				if(tokens[i].equals("=")){
					checked = true;
					i++;
				}
			}
			return name;
		}
	}// END cleanInput()
	
	public String xmlHTTPconnect(String url){
//		Connect to the internet, retrieve an XML rss feed
//		Log.d(TAG, "Should get something like this: http://themoneyconverter.com/rss-feed/USD/rss.xml");	//TESTING
//		Log.d(TAG, "Actually got: "+url);				// TESTING
		String xml = null;
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httppost = new HttpGet(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			xml = EntityUtils.toString(entity);
//			Log.d(TAG, "Site Accessed");		//	TESTING
		}catch(Exception e){
//			Log.e(TAG, "Error in http connection: "+e.toString());		//	TESTING
		}
		return xml;
	}// END xmlHTTPconnect()
			
	public Document getDomElements(String xml){
//		Convert XML to searchable format
		Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
 
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is); 
 
        } catch (Exception e) {
//        	Log.e("Error: ", e.toString());		//	TESTING
        }
        	// return DOM
            return doc;
	}//	END getDomElements()
	
	public String getValue(Element item, String str) {
//		Gets a value 
	    NodeList n = item.getElementsByTagName(str);
	    return this.getElementValue(n.item(0));
	}// END getValue()
	 
	public final String getElementValue( Node elem ) {
//		Gets the value of an element in the converted XML
	         Node child;
	         if( elem != null){
	             if (elem.hasChildNodes()){
	                 for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
	                     if( child.getNodeType() == Node.TEXT_NODE  ){
	                         return child.getNodeValue();
	                     }
	                 }
	             }
	         }
	         return "";
	  }//	END getElementValue()

	public Hashtable<String, String> getStoredKeys(){
		return keyStore;
	}//	END getStoredKeys()
	
	public double processRateAndConvert(){
//		Converts the currency
		String info;
		if(fullCurrency){
			info = ratesTable.get(keyStore.get(convertTo).substring(0, 3)+"/"+keyStore.get(convertFrom).substring(0, 3));
		}else{
			info = ratesTable.get(convertTo+"/"+convertFrom);
		}
		
		String[] tokens = info.split(" ");
		String rateString = null;
		for(int i=0; i<tokens.length; i++){
			if(tokens[i].equals("=")){
				rateString = tokens[i+1];
				break;
			}
		}
		
		return value*Double.valueOf(rateString);
	}//	END processRateAndConvert()
	
	public int checkInternetConnectivity(boolean fromSettings, ConnectivityManager connManager, WifiManager wifiManager, Context goesTo){
		int[] returns = {-1,0,1};
		
	    NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    
		if(wifiManager.isWifiEnabled()){
//			Log.d(TAG, "Wi-Fi enabled");		//	TESTING
			//if wi-fi connected
			if (mWifi.isConnected()) {
//				Log.d(TAG, "Wi-Fi connected");		//	TESTING
				if(!fromSettings){
					return returns[2];		// Runs CurrencyConvertIt.java
				}else{
					Toast.makeText(goesTo, "Wi-Fi access is already available", Toast.LENGTH_SHORT).show();
					return returns[1];		// Does nothing
				}
			}else{
				Toast.makeText(goesTo, "Wi-Fi enabled.\nPlease wait a moment for it to start.", Toast.LENGTH_SHORT).show();
				return returns[1];		// Does nothing
			}
			
	    }else if (mMobile.isConnected()) {
//	        //if mobile internet is connected
//	    	Log.d(TAG, "Mobile enabled");		//	TESTING
	    	if(fromSettings){
	    		Toast.makeText(goesTo, "Mobile internet access is already available", Toast.LENGTH_SHORT).show();
	    		return returns[1];		// Does nothing
	    	}else{
	    		return returns[2];		// Runs CurrencyConvertIt.java
	    	}
	    }else{
//	    	Log.d(TAG, "Wi-Fi & Mobile NOT enabled");		//	TESTING
	    	return returns[0];		// Runs AccessInternet.java
	    }
	}// END checkInternetConnectivity()
}
