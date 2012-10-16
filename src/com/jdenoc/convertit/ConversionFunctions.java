package com.jdenoc.convertit;
// ConversionFunctions.java
// GUI: n/a
// Author: Denis O'Connor
// Last modified: 28/6/12
// Contains functions to allow for conversions
// Not for Time or Currency Conversions

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

//import android.util.Log;		//	TESTING

public class ConversionFunctions {
	
	private String convertFrom, convertTo;
	private double value;
	private Hashtable<String, Double> ratesTable;	// to contain the rates of conversion
	
//	private final String TAG = "ConversionFunctions";		//	TESTING
	
	public ConversionFunctions(String from, String to, double input){
//		Constructor
		convertFrom = from;
		convertTo = to;
		value = input;
		ratesTable = new Hashtable<String, Double>();
	}// END Constructor
	
	public double rateRetrival(){
//		depending on the convertFrom and convertTo values, searches Hashtable for rate
		String hashKey = convertFrom+"-"+convertTo;
		return ratesTable.get(hashKey);
	}// END rateRetrival()
	
	public double getTemperature(){
//		Calcultes the temperature
		final String k = "Kelvin (\u00B0K)";
		final String c = "Celsius (\u00B0C)";
		final String f = "Fahrenheit (\u00B0F)";
		
		if(convertFrom.equals(c)){
			if(convertTo.equals(f)){
				return (value*1.8)+32;		// F = C × 1.8 + 32
			}else if(convertTo.equals(k)){
				return value+273.15;		// K = C + 273.15
			}
		}else if(convertFrom.equals(f)){
			if(convertTo.equals(c)){
				return (value-32)/(1.8);	// C = (F - 32) / 1.8
			}else if(convertTo.equals(k)){
				return (value+459.67)/1.8;	// K = (F + 459.67) / 1.8
			}
		}else if(convertFrom.equals(k)){
			if(convertTo.equals(c)){
				return value-273.15;		// C = K - 273.15
			}else if(convertTo.equals(f)){
				return value*1.8-459.67;	// F = K * 1.8 - 459.67
			}
		}
		
		return value;
	}// END getTemperature()
	
	public void fillTable(InputStream inStream){
//		Fill hashtable with values from "length_rates", "mass_rates" & "vol_rates" files in raw folder
		BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
		String line, hashKey;
		double hashValue; 
		try {
			line = reader.readLine();
			while (line != null) {
//				Log.d(TAG, line);		// TESTING (slows down app when active)
				hashKey = line.substring(0, line.indexOf(' '));
				hashValue = Double.valueOf(line.substring(line.indexOf(' ')+1));
				ratesTable.put(hashKey, hashValue);
				line = reader.readLine();	// advance to the next line
			}
		} catch (IOException e) {
//			Log.e(TAG, "fillTable ERROR: "+e.toString()+" - (Table not filled)");		//	TESTING
		}
	}// END fillTable()
	
	public double convert(double rate){
//		Run the final conversion
		return rate*value;
	}// END convert()
}
