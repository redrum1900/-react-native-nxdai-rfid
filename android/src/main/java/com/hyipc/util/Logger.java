package com.hyipc.util;

import android.util.Log;

public class Logger {
	public static final String TAG = "UHFR2000";
	
	public static void D(String str){
		Log.d(TAG, str);
	}
	
	public static void I(String str){
		Log.i(TAG, str);
	}
	
}
