package com.jdenoc.convertit;
// TimeFunctions.java
// GUI: n/a
// Author: Denis O'Connor
// Last Modified: 02/8/12
// Contains functions to aid in finding out time in different time zones

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.TimeZone;

//import android.util.Log;	//	TESTING

public class TimeFunctions {

//	private final String TAG = "TimeFunctions";		//	TESTING
	private int type;
	private boolean format;
	
	private static String publicTime = null;
	private static String publicTimeZone = null;
	private static String publicZoneID = null;
	
	public TimeFunctions(){
//		Constructor
		type = TimeZone.LONG;
	}// END Constructor()
	
	public TimeFunctions(boolean timeFormat){
//		Constructor
		type = TimeZone.LONG;
		format = timeFormat;
	}// END Constructor()
	
	public String getCurrentTimeZone(){
//		Obtains the devices current time zone setting
	    Calendar now = Calendar.getInstance();	// get Calendar instance
	    TimeZone timeZone = now.getTimeZone();	// get current TimeZone using getTimeZone method of Calendar class

//	    display current TimeZone using getDisplayName() method of TimeZone class
	    return timeZone.getDisplayName(timeZone.inDaylightTime(new Date()), type);
	}// END getCurrentTimeZone()
	
	public String getCurrentTime(){
//		Obtains the current time on device. Displays in 12 hour clock format (i.e.: 3:15 p.m.)
		String dateANDtime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
//		Log.d(TAG, "Time is "+dateANDtime);		//	TESTING
		
		String[] timeElements = dateANDtime.split(" "); 
		String period;
		int hour = Integer.parseInt(timeElements[3].substring(0, timeElements[3].indexOf(':')));
		if(timeElements.length > 4){
			period = timeElements[4];
		}else{
			if(hour >= 12 && hour != 24){
				hour -= 12;
				period = "p.m.";
			}else{
				period = "a.m.";
			}
		}
		return formatTime(
				hour,
				Integer.parseInt(timeElements[3].substring(timeElements[3].indexOf(':')+1,timeElements[3].lastIndexOf(':'))),
				period
				);
	}// END getCurrentTime()
	
	public Hashtable<String, String> getTimezones(){
//		Retrieves the names of the time zones installed on the device 
		TimeZone t;
		Date now = new Date();
		String[] zones = TimeZone.getAvailableIDs();
		Hashtable<String, String> table = new Hashtable<String, String>();
		for(int i=0; i<zones.length; i++){
			if((zones[i].matches("[A-Za-z]*\\/[A-Za-z]*"))&&(!zones[i].substring(0, zones[i].indexOf('/')).equals("Etc"))){
				t = TimeZone.getTimeZone(zones[i]);
				String key = t.getDisplayName(t.inDaylightTime(now), type);
				if(!key.substring(0, 3).equals("GMT")){
					if(!table.contains(zones[i])){
						table.put(key, zones[i]);
					}
				}
			}
		}
		return table;
	}// END getTimezones()
	
	
	public Hashtable<String, ArrayList<String>> getLocations(Hashtable<String, ArrayList<String>> regions){
//		retrieves the locations (time zones) installed on the android device by default
		String[] zones = TimeZone.getAvailableIDs();

		for(int i=0; i<zones.length; i++){
			if(zones[i].matches("[A-Za-z]*\\/[A-Za-z]*")){
				ArrayList<String> cities;
				String[] breakup = zones[i].split("/");
				String key = breakup[0];		// Region
				String value = breakup[1];		// City
				if(!key.equals("Etc")){
					if(regions.containsKey(key)){
						cities = (ArrayList<String>) regions.get(key);	
						if(!cities.contains(value)){
							cities.add(value);
							regions.put(key, cities);
						}
					}else{
						cities = new ArrayList<String>();
						cities.add(value);
						regions.put(key, cities);
					}
				}
			}
		}
		return regions;
	}// END getLocations()
	
	
	public void setPublicTime(String newTime){
		publicTime = newTime;
	}// END setPublicTime()
	public void setPublicZone(String newZone){
		publicTimeZone = newZone;
	}// END setPublicZone()
	public void setPublicZoneID(String newID){
		publicZoneID = newID;
	}// END setPublicZoneID()
	public String getPublicTime(){
		return publicTime;
	}// END getPublicTime()
	public String getPublicZone(){
		return publicTimeZone;
	}// END getPublicZone()
	public String getPublicZoneID(){
		return publicZoneID;
	}// END getPublicZoneID()
	
	public String formatTime(int hour, int min, String period){
//		Log.d(TAG, hour+":"+min+" ("+period+")");		//	TESTING
		if(format){		// to be set to 24hour format
			if(period.equals("p.m.")){
				hour += 12;
			}else if((period.equals("a.m.")) && (hour == 12)){
				hour -= 12;
			}
			if(hour == 24){
				hour = 0;
			}
			period = "";
			
		}else{			// to be set to 12hour format
			if(hour == 24 || hour == 0){
				hour = 12;
				period = "a.m.";
			}else if(hour > 12){
				hour -= 12;
				period = "p.m.";
			}else{
				if(!period.equals("p.m.")){
					period = "a.m.";
				}
			}
		}
		
		if(min<10){
			return hour+":0"+min+" "+period;
		}else{
			return hour+":"+min+" "+period;
		}
	}// END formatTime()
	
	public String convertTime(String newTimeZone){
		Long time = new Date().getTime();
		Calendar c;
		
		if(getPublicZoneID() == null){
			Calendar now = Calendar.getInstance();
			c = new GregorianCalendar(now.getTimeZone());
		}else{
			c = new GregorianCalendar(TimeZone.getTimeZone(getPublicZoneID()));
		}
		
		c.setTimeInMillis(time);
		int originalHour = c.get(Calendar.HOUR_OF_DAY);	// gets hour based on 24 hour clock
		int originalMin = c.get(Calendar.MINUTE);		// gets minute
		int originalDay = c.get(Calendar.DAY_OF_MONTH);
//		Log.d(TAG, "original="+originalHour+":"+originalMin+" day="+originalDay);		//	TESTING
		
		c = new GregorianCalendar(TimeZone.getTimeZone(newTimeZone));
		c.setTimeInMillis(time);
		int newHour = c.get(Calendar.HOUR_OF_DAY);		// gets hour based on 24 hour clock
		int newMin = c.get(Calendar.MINUTE);			// gets minute
		int newDay = c.get(Calendar.DAY_OF_MONTH);
//		Log.d(TAG, "new="+newHour+":"+newMin+" day="+newDay);		//	TESTING
		
		int hourDiff = newHour - originalHour;
		int minDiff = newMin - originalMin;
		int dayDiff = newDay - originalDay;
//		Log.d(TAG, "diff="+hourDiff+":"+minDiff+" day="+dayDiff);		//	TESTING
		
		if(dayDiff !=0){
			hourDiff = hourDiff + 24*dayDiff;
		}

		int convertHour = hourDiff + Integer.parseInt(getPublicTime().substring(0, getPublicTime().indexOf(':')));
		int convertMin;
		String period=null;
		convertMin = minDiff + Integer.parseInt(getPublicTime().substring(getPublicTime().indexOf(':')+1, getPublicTime().indexOf(' ')));
		period = getPublicTime().substring(getPublicTime().indexOf(' ')+1);
//		Log.d(TAG, "initConvert="+convertHour+":"+convertMin+" "+period);		//	TESTING
		
		if(convertMin >= 60){
			convertHour++;
			convertMin -= 60;
		}else if(convertMin < 0){
			convertHour--;
			convertMin += 60;
		}
		
		int day = 0;
		if(convertHour > 24){
			convertHour -= 24;
			day++;
		}else if(convertHour <=0){
			convertHour += 24;
			day--;
		}
		
		String end = "";
		if(day<0){
			end = " -"+day+"day(s)";
		}else if(day>0){
			end = " +"+day+"day(s)";
		}
		
		return formatTime(convertHour, convertMin, period)+end;
	}// END convertTime()
}