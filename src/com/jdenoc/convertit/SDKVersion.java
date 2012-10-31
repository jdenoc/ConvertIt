package com.jdenoc.convertit;
// SDKVersion.java
// GUI:	n/a
// Author: Denis O'Connor
// Last modified: 29-OCT-2012
// Obtains SDK version. If the SDK version is 11 or greater, then the App can use the ActionBar feature

import android.util.Log;

public class SDKVersion {
	
	private static int sdkVersion = 0;
	private final static String TAG = "";

	public static void setSDKVersion(){
		try {
			sdkVersion = android.os.Build.VERSION.SDK_INT;
			Log.d(TAG, "SDK="+sdkVersion);
		} catch (Exception ex) {
			Log.e(TAG, ex.toString());
		}
	}
	
	public static boolean useActionBar(){
		return sdkVersion >= 11;
	}
}
