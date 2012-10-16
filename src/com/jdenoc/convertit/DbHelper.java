package com.jdenoc.convertit;
// DbHelper.java
// GUI: N/A
// Author: Denis O'Connor
// Last Modified: 07/8/12
// SRC:	http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
// SQLite Database for Length, Mass, Speed & Volume Conversions

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;		// TESTING ONLY

	
public class DbHelper extends SQLiteOpenHelper{

	public static final String KEY_NAME = "name";
	public static final String[] KEY_SPEED = {"'m/s'", "'k/m'", "'mph'", "'Knots'", "'ft/s'", "'Mach'"};
	public static final String[] KEY_LEN = {"'Inch'", "'Foot'", "'Yard'", "'Mile'", "'MM'", "'CM'", "'M'", "'KM'" , "'NauticalMile'"};
	public static final String[] KEY_VOL = {"'Fl.Oz(US)'", "'Fl.Oz(UK)'", "'Pint(US)'", "'Pint(UK)'", "'Quart(US)'", "'Quart(UK)'", "'Gallon(US)'", "'Gallon(UK)'", "'Millilitre'", "'Litre'"};
	public static final String[] KEY_MASS = {"'Milligram'", "'Gram'", "'Kilogram'", "'Pound'", "'Ounce'", "'Ton(US)'", "'Ton(UK)'", "'MetricTon'"};
		
	private static final String DB_NAME = "Conversion Values";
	private static final String[] TABLE_NAME = {"Speed", "Length", "Volume", "Mass"};
	private static final int DB_VER = 1;
	
//	private static final String TAG = "SQLite";
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VER);
		// Nothing to do here ...
	}// END Constructor

	@Override
	public void onCreate(SQLiteDatabase db) {
//		Used when creating database
				
		// Create Speed Table
//		Log.d(TAG, "Creating Speed Table");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME[0]+" ("+ 	//	Speed
			KEY_NAME+" TEXT NOT NULL, "+
			KEY_SPEED[0]+" REAL NOT NULL, "+	//	m/s
			KEY_SPEED[1]+" REAL NOT NULL, "+	//	km/h
			KEY_SPEED[2]+" REAL NOT NULL, "+	//	mph
			KEY_SPEED[3]+" REAL NOT NULL, "+	//	Knots
			KEY_SPEED[4]+" REAL NOT NULL, "+	//	ft/s
			KEY_SPEED[5]+" REAL NOT NULL);"		//	Mach
		);
		
		// Populate Speed Table
//		Log.d(TAG, "Populating Speed Table");
		db.execSQL("INSERT INTO "+TABLE_NAME[0]+" ("+
			KEY_NAME+", "+KEY_SPEED[0]+", "+KEY_SPEED[1]+", "+KEY_SPEED[2]+", "+KEY_SPEED[3]+", "+KEY_SPEED[4]+", "+KEY_SPEED[5]+") VALUES " +
			"("+KEY_SPEED[0]+", 1, 3.6, 2.236926, 1.943844, 3.280840, 1224.7752)," +
			"("+KEY_SPEED[1]+", 0.277778, 1, 0.621371, 0.0539957, 0.911344, 1224.7752)," +
			"("+KEY_SPEED[2]+", 0.44704, 1.609344, 1, 0.868976, 1.466667, 0.0013)," +
			"("+KEY_SPEED[3]+", 0.514444, 1.852, 1.150779, 1, 1.687810, 0.0015)," +
			"("+KEY_SPEED[4]+", 0.3048, 1.09728, 0.681818, 0.592484, 1, 0.0009)," +
			"("+KEY_SPEED[5]+", 1224.7752, 1224.7752, 761.1797, 1224.7752, 1116.192, 1);"	
		);
		
		// Create Length Table
//		Log.d(TAG, "Creating Length Table");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME[1]+" ("+ 	//	Length
			KEY_NAME+" TEXT NOT NULL, "+
			KEY_LEN[0]+" REAL NOT NULL, "+	//	Inch
			KEY_LEN[1]+" REAL NOT NULL, "+	//	Foot
			KEY_LEN[2]+" REAL NOT NULL, "+	//	Yard
			KEY_LEN[3]+" REAL NOT NULL, "+	//	Mile
			KEY_LEN[4]+" REAL NOT NULL, "+	//	MM
			KEY_LEN[5]+" REAL NOT NULL, "+	//	CM
			KEY_LEN[6]+" REAL NOT NULL, "+	//	M
			KEY_LEN[7]+" REAL NOT NULL, "+	//	KM
			KEY_LEN[8]+" REAL NOT NULL);"	//	NauticalMile
		);
			
		// Populate Length Table
//		Log.d(TAG, "Populating Length Table");
		db.execSQL("INSERT INTO "+TABLE_NAME[1]+" ("+
			KEY_NAME+", "+KEY_LEN[0]+", "+KEY_LEN[1]+", "+KEY_LEN[2]+", "+KEY_LEN[3]+", "+KEY_LEN[4]+", "+KEY_LEN[5]+", "+KEY_LEN[6]+", "+KEY_LEN[7]+", "+KEY_LEN[8]+") VALUES " +
			"("+KEY_LEN[0]+", 1, 0.0833333333, 0.0277777778, 0.0000157828283, 25.4, 2.54, 0.0254, 0.0000254, 0.0000137149028)," +
			"("+KEY_LEN[1]+", 12, 1, 0.333333333, 0.000189393939, 304.8, 30.48, 0.3048, 0.0003048, 0.000164578834)," +
			"("+KEY_LEN[2]+", 36, 3, 1, 0.000568181818, 914.4, 91.44, 0.9144, 0.0009144, 0.000493736501)," +
			"("+KEY_LEN[3]+", 63360, 5280, 1760, 1, 1609344, 160934.4, 1609.344, 1.609344, 0.868976242)," +
			"("+KEY_LEN[4]+", 0.0393700787, 0.0032808399, 0.0010936133, 0.000000621371192, 1, 0.1, 0.001, 0.000001, 0.000000539956803)," +
			"("+KEY_LEN[5]+", 0.393700787, 0.032808399, 0.010936133, 0.00000621371192, 10, 1, 0.01, 0.00001, 0.00000539956803),"+
			"("+KEY_LEN[6]+", 39.3700787, 3.2808399, 1.0936133, 0.000621371192, 1000, 100, 1, 0.001, 0.000539956803),"+
			"("+KEY_LEN[7]+", 39370.0787, 3280.8399, 1093.6133, 0.621371192, 1000000, 100000, 1000, 1, 0.539956803),"+
			"("+KEY_LEN[8]+", 72913.3858, 6076.11549, 2025.37183, 1.15077945, 1852000, 185200, 1852, 1.85200, 1);"
		);
		
		// Create Volume Table
//		Log.d(TAG, "Creating Volume Table");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME[2]+" ("+ 	//	Volume
			KEY_NAME+" TEXT NOT NULL, "+
			KEY_VOL[0]+" REAL NOT NULL, "+	//	Fl.Oz(US)
			KEY_VOL[1]+" REAL NOT NULL, "+	//	Fl.Oz(UK)
			KEY_VOL[2]+" REAL NOT NULL, "+	//	Pint(US)
			KEY_VOL[3]+" REAL NOT NULL, "+	//	Pint(UK)
			KEY_VOL[4]+" REAL NOT NULL, "+	//	Quart(US)
			KEY_VOL[5]+" REAL NOT NULL, "+	//	Quart(UK)
			KEY_VOL[6]+" REAL NOT NULL, "+	//	Gallon(US)
			KEY_VOL[7]+" REAL NOT NULL, "+	//	Gallon(UK)
			KEY_VOL[8]+" REAL NOT NULL, "+	//	Millilitre
			KEY_VOL[9]+" REAL NOT NULL);"	//	Litre
		);

		// Populate Volume Table
//		Log.d(TAG, "Populating Volume Table");
		db.execSQL("INSERT INTO "+TABLE_NAME[2]+" ("+
			KEY_NAME+", "+KEY_VOL[0]+", "+KEY_VOL[1]+", "+KEY_VOL[2]+", "+KEY_VOL[3]+", "+KEY_VOL[4]+", "+KEY_VOL[5]+", "+KEY_VOL[6]+", "+KEY_VOL[7]+", "+KEY_VOL[8]+", "+KEY_VOL[9]+") VALUES " +
			"("+KEY_VOL[0]+", 1, 1.04084273, 0.0625, 0.05204214, 0.03125, 0.02602134, 0.0078125, 0.00650527, 29.57352956, 0.02957353)," +
			"("+KEY_VOL[1]+", 0.96075994, 1, 0.06004750, 0.05, 0.03002375, 0.025, 0.00750594, 0.00625, 28.4130625, 0.028413063)," +
			"("+KEY_VOL[2]+", 16, 16.65348370, 1, 0.83267418, 0.5, 0.41633709,  0.125, 0.15011874, 473.176473, 0.47317647)," +
			"("+KEY_VOL[3]+", 19.2151988, 20, 1.20094993, 1, 0.60047496, 0.5, 0.15011874, 0.125, 568.26125, 0.56826125)," +
			"("+KEY_VOL[4]+", 32, 33.30696739, 2, 1.66534837, 1, 0.83267418, 0.25, 0.20816855, 946.352946, 0.94635295)," +
			"("+KEY_VOL[5]+", 38.430, 40, 2.40189985, 2, 1.20094993, 1, 0.30023748, 0.25, 1136.5225, 1.1365225),"+
			"("+KEY_VOL[6]+", 128, 133.22786957, 8, 6.66139348, 4, 3.33069674, 1, 0.83267418, 3785.41178, 3.78541178),"+
			"("+KEY_VOL[7]+", 153.7215904, 160, 6.66139351, 8, 4.80379970, 4, 1.200949926, 1, 4546.09, 4.54609),"+
			"("+KEY_VOL[8]+", 0.03381402, 0.03519508, 0.02113376, 0.00175975, 0.01056688, 0.00087988, 0.00264172, 0.00219969, 1, 0.001),"+
			"("+KEY_VOL[9]+", 33.8140227, 35.19507973, 2.11337642, 1.75975399, 1.05668821, 0.87987699, 0.26417205, 0.21996925, 1000.0, 1);"
		);
		
		// Create Mass Table
//		Log.d(TAG, "Creating Mass Table");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME[3]+" ("+ 	//	Mass
			KEY_NAME+" TEXT NOT NULL, "+
			KEY_MASS[0]+" REAL NOT NULL, "+	//	Milligram
			KEY_MASS[1]+" REAL NOT NULL, "+	//	Gram
			KEY_MASS[2]+" REAL NOT NULL, "+	//	Kilogram
			KEY_MASS[3]+" REAL NOT NULL, "+	//	Pound
			KEY_MASS[4]+" REAL NOT NULL, "+	//	Ounce
			KEY_MASS[5]+" REAL NOT NULL, "+	//	Ton(US)
			KEY_MASS[6]+" REAL NOT NULL, "+	//	Ton(UK)
			KEY_MASS[7]+" REAL NOT NULL);"	//	MetricTon	
		);

		// Populate Mass Table
//		Log.d(TAG, "populating Mass Table");
		db.execSQL("INSERT INTO "+TABLE_NAME[3]+" ("+
			KEY_NAME+", "+KEY_MASS[0]+", "+KEY_MASS[1]+", "+KEY_MASS[2]+", "+KEY_MASS[3]+", "+KEY_MASS[4]+", "+KEY_MASS[5]+", "+KEY_MASS[6]+", "+KEY_MASS[7]+") VALUES " +
			"("+KEY_MASS[0]+", 1, 0.001, 0.000001, 0.00000220462262, 0.0000352739619, 0.00000000110231131, 0.000000000984206528, 0.000000001)," +
			"("+KEY_MASS[1]+", 1000, 1, 0.001, 0.00220462262, 0.0352739619, 0.000001, 0.000000984206528, 0.000001)," +
			"("+KEY_MASS[2]+", 1000000, 1000, 1, 2.20462262, 35.2739619, 0.00110231131, 0.000984206528, 0.001)," +
			"("+KEY_MASS[3]+", 453592.37, 453.59237, 0.45359237, 1, 16, 0.0005, 0.000446428571, 0.00045359237)," +
			"("+KEY_MASS[4]+", 28349.5231, 28.3495231, 0.0283495231, 0.0625, 1, 0.0000312500, 0.0000279017857, 0.0000283495231)," +
			"("+KEY_MASS[5]+", 907184740, 907184.74, 907.18474, 2000, 32000, 1, 0.892857143, 0.90718474),"+
			"("+KEY_MASS[6]+", 1016046910, 1016046.91, 1016.04691, 2240, 35840, 1.12, 1, 1.01604691),"+
			"("+KEY_MASS[7]+", 1000000000, 1000000, 1000, 2204.62262, 35273.9619, 1.10231131, 0.984206528, 1);"
		);
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
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT * FROM "+table+" WHERE name=?", new String[]{from});
        if (cursor != null){
            cursor.moveToFirst();
        }
        
        return cursor.getDouble(cursor.getColumnIndex(to));
	}//	END getConversionRate()	
}

