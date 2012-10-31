package com.jdenoc.convertit;
// ConversionFunctions.java
// GUI: n/a
// Author: Denis O'Connor
// Last modified: 28-OCT-2012
// Contains functions to allow for conversions
// Not for Time or Currency Conversions

import android.content.Context;
//import android.util.Log;		//	TESTING

public class ConversionFunctions {
	
	private String convertFrom, convertTo;
	private double value;
	
//	private final String TAG = "ConversionFunctions";		//	TESTING
	
	public ConversionFunctions(String from, String to, double input){
//		Constructor
		convertFrom = from;
		convertTo = to;
		value = input;
	}// END Constructor
	
	public double getTemperature(){
//		Calculates the temperature
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
	
	public double getConversion(Context c, String type){
		DbHelper db = new DbHelper(c);
		double rate = db.getConversionRate(type, convertFrom, convertTo);
		db.close();
		return rate;
	}//	END getConversion()
	
	public double convert(double rate){
//		Run the final conversion
		return rate*value;
	}// END convert()
}
