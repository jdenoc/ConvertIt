package com.jdenoc.convertit;
// DbHelper.java
// GUI: N/A
// Author: Denis O'Connor
// Last Modified: 29-OCT-2012
// SRC:	http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
// SQLite Database for Length, Mass, Speed, Volume, Time and Area Conversions

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;		// TESTING ONLY

	
public class DbHelper extends SQLiteOpenHelper{

	public static final String KEY_NAME = "name";
	public static final String[] KEY_SPEED = {"'m_s'", "'kph'", "'mph'", "'Knots'", "'ft_s'", "'Mach'"};
	public static final String[] KEY_LEN = {"'Inch'", "'Foot'", "'Yard'", "'Mile'", "'MM'", "'CM'", "'M'", "'KM'" , "'NauticalMile'"};
	public static final String[] KEY_VOL = {"'Fl_Oz(US)'", "'Fl_Oz(UK)'", "'Pint(US)'", "'Pint(UK)'", "'Quart(US)'", "'Quart(UK)'", "'Gallon(US)'", "'Gallon(UK)'", "'Millilitre'", "'Litre'"};
	public static final String[] KEY_MASS = {"'Milligram'", "'Gram'", "'Kilogram'", "'Pound'", "'Ounce'", "'Ton(US)'", "'Ton(UK)'", "'MetricTon'"};
	public static final String[] KEY_TIME = {"'Nanosecond'", "'Microsecond'", "'Millisecond'", "'Second'", "'Minute'", "'Hour'", "'Day'", "'Week'", "'Year'", "'Decade'", "'Century'"};
	public static final String[] KEY_AREA = {"'KM_2'", "'Hectare'", "'M_2'", "'Mile_2, '", "'Acre'", "'Yard_2'", "'Foot_2'", "'Inch_2'"};
	
	private static final String DB_NAME = "Conversion Values";
	private static final String[] TABLE_NAME = {"Speed", "Length", "Volume", "Mass", "Time", "Area"};
	private static final int DB_VER = 1;
	
	private static final String TAG = "SQLite";
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VER);
		// Nothing to do here ...
	}// END Constructor

	@Override
	public void onCreate(SQLiteDatabase db) {
//		Used when creating database
		for(int i=0; i < TABLE_NAME.length; i++){
//			Log.d(TAG, "Creating "+TABLE_NAME[i]+" Table");		// TESTING
			Object[] keys = {};
			switch(i){
			case 0:	// Speed
				keys = KEY_SPEED;
				break;
			case 1:	// Length
				keys = KEY_LEN;
				break;
			case 2:	// Volume
				keys = KEY_VOL;
				break;
			case 3:	// Mass
				keys = KEY_MASS;
				break;
			case 4:	// Time
				keys = KEY_TIME;
				break;
			case 5:	// Area
				keys = KEY_AREA;
				break;
			}
			
			String createTable = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME[i]+" ("+KEY_NAME+" TEXT NOT NULL";
			for(int j=0; j<keys.length; j++){
				createTable += ", "+(String) keys[j]+" REAL NOT NULL";
			}
			createTable += ");";
						
//			Log.d(TAG, createTable);	// TESTING
			db.execSQL(createTable);	// Create Tables
		}
		
		// Populate Speed Table
		Log.d(TAG, "Populating Speed Table");		// TESTING
		db.execSQL("INSERT INTO "+TABLE_NAME[0]+" VALUES ("+KEY_SPEED[0]+", 1, 3.6, 2.236926, 1.943844, 3.280840, 1224.7752);");
		db.execSQL("INSERT INTO "+TABLE_NAME[0]+" VALUES ("+KEY_SPEED[1]+", 0.277778, 1, 2.236926, 0.0539957, 0.911344, 1224.7752);");
		db.execSQL("INSERT INTO "+TABLE_NAME[0]+" VALUES ("+KEY_SPEED[2]+", 0.44704, 1.609344, 1, 0.868976, 1.466667, 0.0013);");
		db.execSQL("INSERT INTO "+TABLE_NAME[0]+" VALUES ("+KEY_SPEED[3]+", 0.514444, 1.852, 1.150779, 1, 1.687810, 0.0015);");
		db.execSQL("INSERT INTO "+TABLE_NAME[0]+" VALUES ("+KEY_SPEED[4]+", 0.3048, 1.09728, 0.681818, 0.592484, 1, 0.0009);");
		db.execSQL("INSERT INTO "+TABLE_NAME[0]+" VALUES ("+KEY_SPEED[5]+", 1224.7752, 1224.7752, 761.1797, 1224.7752, 1116.192, 1);");

		// Populate Length Table
		Log.d(TAG, "Populating Length Table");		// TESTING
		db.execSQL("INSERT INTO "+TABLE_NAME[1]+" VALUES ("+KEY_LEN[0]+", 1, 0.0833333333, 0.0277777778, 0.0000157828283, 25.4, 2.54, 0.0254, 0.0000254, 0.0000137149028);");
		db.execSQL("INSERT INTO "+TABLE_NAME[1]+" VALUES ("+KEY_LEN[1]+", 12, 1, 0.333333333, 0.000189393939, 304.8, 30.48, 0.3048, 0.0003048, 0.000164578834);");
		db.execSQL("INSERT INTO "+TABLE_NAME[1]+" VALUES ("+KEY_LEN[2]+", 36, 3, 1, 0.000568181818, 914.4, 91.44, 0.9144, 0.0009144, 0.000493736501);");
		db.execSQL("INSERT INTO "+TABLE_NAME[1]+" VALUES ("+KEY_LEN[3]+", 63360, 5280, 1760, 1, 1609344, 160934.4, 1609.344, 1.609344, 0.868976242);");
		db.execSQL("INSERT INTO "+TABLE_NAME[1]+" VALUES ("+KEY_LEN[4]+", 0.0393700787, 0.0032808399, 0.0010936133, 0.000000621371192, 1, 0.1, 0.001, 0.000001, 0.000000539956803);");
		db.execSQL("INSERT INTO "+TABLE_NAME[1]+" VALUES ("+KEY_LEN[5]+", 0.393700787, 0.032808399, 0.010936133, 0.00000621371192, 10, 1, 0.01, 0.00001, 0.00000539956803);");
		db.execSQL("INSERT INTO "+TABLE_NAME[1]+" VALUES ("+KEY_LEN[6]+", 39.3700787, 3.2808399, 1.0936133, 0.000621371192, 1000, 100, 1, 0.001, 0.000539956803);");
		db.execSQL("INSERT INTO "+TABLE_NAME[1]+" VALUES ("+KEY_LEN[7]+", 39370.0787, 3280.8399, 1093.6133, 0.621371192, 1000000, 100000, 1000, 1, 0.539956803);");
		db.execSQL("INSERT INTO "+TABLE_NAME[1]+" VALUES ("+KEY_LEN[8]+", 72913.3858, 6076.11549, 2025.37183, 1.15077945, 1852000, 185200, 1852, 1.85200, 1);");

		// Populate Volume Table
		Log.d(TAG, "Populating Volume Table");		// TESTING
		db.execSQL("INSERT INTO "+TABLE_NAME[2]+" VALUES ("+KEY_VOL[0]+", 1, 1.04084273, 0.0625, 0.05204214, 0.03125, 0.02602134, 0.0078125, 0.00650527, 29.57352956, 0.02957353);");
		db.execSQL("INSERT INTO "+TABLE_NAME[2]+" VALUES ("+KEY_VOL[1]+", 0.96075994, 1, 0.06004750, 0.05, 0.03002375, 0.025, 0.00750594, 0.00625, 28.4130625, 0.028413063);");
		db.execSQL("INSERT INTO "+TABLE_NAME[2]+" VALUES ("+KEY_VOL[2]+", 16, 16.65348370, 1, 0.83267418, 0.5, 0.41633709,  0.125, 0.15011874, 473.176473, 0.47317647);");
		db.execSQL("INSERT INTO "+TABLE_NAME[2]+" VALUES ("+KEY_VOL[3]+", 19.2151988, 20, 1.20094993, 1, 0.60047496, 0.5, 0.15011874, 0.125, 568.26125, 0.56826125);");
		db.execSQL("INSERT INTO "+TABLE_NAME[2]+" VALUES ("+KEY_VOL[4]+", 32, 33.30696739, 2, 1.66534837, 1, 0.83267418, 0.25, 0.20816855, 946.352946, 0.94635295);");
		db.execSQL("INSERT INTO "+TABLE_NAME[2]+" VALUES ("+KEY_VOL[5]+", 38.430, 40, 2.40189985, 2, 1.20094993, 1, 0.30023748, 0.25, 1136.5225, 1.1365225);");
		db.execSQL("INSERT INTO "+TABLE_NAME[2]+" VALUES ("+KEY_VOL[6]+", 128, 133.22786957, 8, 6.66139348, 4, 3.33069674, 1, 0.83267418, 3785.41178, 3.78541178);");
		db.execSQL("INSERT INTO "+TABLE_NAME[2]+" VALUES ("+KEY_VOL[7]+", 153.7215904, 160, 6.66139351, 8, 4.80379970, 4, 1.200949926, 1, 4546.09, 4.54609);");
		db.execSQL("INSERT INTO "+TABLE_NAME[2]+" VALUES ("+KEY_VOL[8]+", 0.03381402, 0.03519508, 0.02113376, 0.00175975, 0.01056688, 0.00087988, 0.00264172, 0.00219969, 1, 0.001);");
		db.execSQL("INSERT INTO "+TABLE_NAME[2]+" VALUES ("+KEY_VOL[9]+", 33.8140227, 35.19507973, 2.11337642, 1.75975399, 1.05668821, 0.87987699, 0.26417205, 0.21996925, 1000.0, 1);");
		
		// Populate Mass Table
		Log.d(TAG, "populating Mass Table");		// TESTING
		db.execSQL("INSERT INTO "+TABLE_NAME[3]+" VALUES ("+KEY_MASS[0]+", 1, 0.001, 0.000001, 0.00000220462262, 0.0000352739619, 0.00000000110231131, 0.000000000984206528, 0.000000001);");
		db.execSQL("INSERT INTO "+TABLE_NAME[3]+" VALUES ("+KEY_MASS[1]+", 1000, 1, 0.001, 0.00220462262, 0.0352739619, 0.000001, 0.000000984206528, 0.000001);");
		db.execSQL("INSERT INTO "+TABLE_NAME[3]+" VALUES ("+KEY_MASS[2]+", 1000000, 1000, 1, 2.20462262, 35.2739619, 0.00110231131, 0.000984206528, 0.001);");
		db.execSQL("INSERT INTO "+TABLE_NAME[3]+" VALUES ("+KEY_MASS[3]+", 453592.37, 453.59237, 0.45359237, 1, 16, 0.0005, 0.000446428571, 0.00045359237);");
		db.execSQL("INSERT INTO "+TABLE_NAME[3]+" VALUES ("+KEY_MASS[4]+", 28349.5231, 28.3495231, 0.0283495231, 0.0625, 1, 0.0000312500, 0.0000279017857, 0.0000283495231);");
		db.execSQL("INSERT INTO "+TABLE_NAME[3]+" VALUES ("+KEY_MASS[5]+", 907184740, 907184.74, 907.18474, 2000, 32000, 1, 0.892857143, 0.90718474);");
		db.execSQL("INSERT INTO "+TABLE_NAME[3]+" VALUES ("+KEY_MASS[6]+", 1016046910, 1016046.91, 1016.04691, 2240, 35840, 1.12, 1, 1.01604691);");
		db.execSQL("INSERT INTO "+TABLE_NAME[3]+" VALUES ("+KEY_MASS[7]+", 1000000000, 1000000, 1000, 2204.62262, 35273.9619, 1.10231131, 0.984206528, 1);");

		// Populate Time Table
		Log.d(TAG, "populating Time Table");		// TESTING
		db.execSQL("INSERT INTO "+TABLE_NAME[4]+" VALUES ("+KEY_TIME[0]+", 1, 0.001, 0.000001, 0.000000001, 0.000000000016667, 0.00000000000027778, 0.000000000000011574, 0.0000000000000016534, 0.000000000000000031689, 0.0000000000000000031689, 0.00000000000000000031689);");
		db.execSQL("INSERT INTO "+TABLE_NAME[4]+" VALUES ("+KEY_TIME[1]+", 1000, 1, 0.001, 0.000001, 0.000000016667, 0.00000000027778, 0.000000000011574, 0.0000000000016534, 0.000000000000031689, 0.0000000000000031689, 0.0000000000000031689);");
		db.execSQL("INSERT INTO "+TABLE_NAME[4]+" VALUES ("+KEY_TIME[2]+", 1000000, 1000, 1, 0.001, 0.000016667, 0.00000027778, 0.000000011574, 0.0000000016534, 0.000000000031689, 0.0000000000031689, 0.00000000000031689);");
		db.execSQL("INSERT INTO "+TABLE_NAME[4]+" VALUES ("+KEY_TIME[3]+", 1000000000, 1000000, 1000, 1, 0.016667, 			0.00027778, 0.000011574, 0.00000016534, 0.000000031689, 0.0000000031689, 0.00000000031689);");
		db.execSQL("INSERT INTO "+TABLE_NAME[4]+" VALUES ("+KEY_TIME[4]+", 60000000000, 60000000, 60000, 	60, 			1, 0.016667, 0.000694444, 0.000099206, 0.000001902, 0.0000001902, 0.00000001902);");
		db.execSQL("INSERT INTO "+TABLE_NAME[4]+" VALUES ("+KEY_TIME[5]+", 3600000000000, 3600000000, 3600000, 3600, 60, 1, 0.0416667, 0.00595238, 0.00011408, 0.000011408, 0.0000011408);");
		db.execSQL("INSERT INTO "+TABLE_NAME[4]+" VALUES ("+KEY_TIME[6]+", 86400000000000, 86400000000, 86400000, 86400, 1440, 24, 1, 0.142857, 0.0027378, 0.00027378, 0.000027378);");
		db.execSQL("INSERT INTO "+TABLE_NAME[4]+" VALUES ("+KEY_TIME[7]+", 604800000000000, 604800000000, 604800000, 604800, 10080, 168, 7, 1, 0.0191654, 0.00191654, 0.000191654);");
		db.execSQL("INSERT INTO "+TABLE_NAME[4]+" VALUES ("+KEY_TIME[8]+", 31560000000000000, 31560000000000, 31560000000, 31560000, 525949, 8765.81, 365.25, 52, 1, 0.1, 0.01);");
		db.execSQL("INSERT INTO "+TABLE_NAME[4]+" VALUES ("+KEY_TIME[9]+", 315600000000000000, 315600000000000, 315600000000, 315600000, 5259490, 87658.1, 3652.5, 520, 10, 1, 0.1);");
		db.execSQL("INSERT INTO "+TABLE_NAME[4]+" VALUES ("+KEY_TIME[10]+",3156000000000000000, 3156000000000000, 3156000000000, 3156000000, 52594900, 876581, 36525, 5200, 100, 10, 1);");
		
		// Populate Area Table
		Log.d(TAG, "populating Area Table");		// TESTING
		db.execSQL("INSERT INTO "+TABLE_NAME[5]+" VALUES ("+KEY_AREA[0]+", 1, 100, 1000000, 0.386102, 247.105, 1195990, 10763900, 1550000000);");
		db.execSQL("INSERT INTO "+TABLE_NAME[5]+" VALUES ("+KEY_AREA[1]+", 0.01, 1, 10000, 0.00386102, 2.47105, 11959.9, 107639, 15500000);");
		db.execSQL("INSERT INTO "+TABLE_NAME[5]+" VALUES ("+KEY_AREA[2]+", 1000000, 0.0001, 1, 0.0000003861, 	0.000247105, 1.19599, 10.7639, 	1550);");
		db.execSQL("INSERT INTO "+TABLE_NAME[5]+" VALUES ("+KEY_AREA[3]+", 2.58999, 258.999, 2590000, 1, 640, 3098000, 27880000, 4014000000);");
		db.execSQL("INSERT INTO "+TABLE_NAME[5]+" VALUES ("+KEY_AREA[4]+", 0.00404686, 0.40686, 4046.86, 0.0015625, 1, 4840, 43560, 6273000);");
		db.execSQL("INSERT INTO "+TABLE_NAME[5]+" VALUES ("+KEY_AREA[5]+", 0.00000083613, 0.000083613, 0.836127, 0.00000032283, 0.000206612, 1, 9, 1296);");
		db.execSQL("INSERT INTO "+TABLE_NAME[5]+" VALUES ("+KEY_AREA[6]+", 0.000000092903, 0.0000092903, 0.092903, 0.00000003587, 0.000022957, 0.111111, 1, 144);");
		db.execSQL("INSERT INTO "+TABLE_NAME[5]+" VALUES ("+KEY_AREA[7]+", 0.00000000064516, 0.000000064516, 0.00064516, 0.0000000002491, 0.00000015942, 0.000771605, 0.00694444, 1);");

	}// END onCreate()
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Used when updating database
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME[0]);
		onCreate(db);
	}//	END onUpgrade
	
//	CRUD methods	(Create, Read, Update, Delete) 		{only read has been implemented}
	
	public double getConversionRate(String table, String from, String to){
//		Obtain conversion rate from SQLite database
		to = to.replace('/', '_');
		from = from.replace('/', '_');
		to = to.replace('.', '_');
		from = from.replace('.', '_');
		if(to.indexOf(' ') != -1) 
			to = to.substring(0, to.indexOf(' '))+"_2";
		if(from.indexOf(' ') != -1) 
			from = from.substring(0, from.indexOf(' '))+"_2";
		
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM "+table+" WHERE name=?", new String[]{from});
        if (cursor != null){
            if(cursor.moveToFirst()) return cursor.getDouble(cursor.getColumnIndex(to));
            else return 1.0;
        }else return 1.0;
        
	}//	END getConversionRate()	
}

