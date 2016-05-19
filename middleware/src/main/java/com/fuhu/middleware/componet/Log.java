package com.fuhu.middleware.componet;

import com.fuhu.middleware.BuildConfig;

public class Log {
	public static int ENABLE_VERBOSE 	= 4;
	public static int ENABLE_DEBUG 		= 3;
	public static int ENABLE_INFO 		= 2;
	public static int ENABLE_WARN 		= 1;
	public static int ENABLE_ERROR 		= 0;
	public static int ENABLE_ALL 		= 5;
	
	public final static int sLogLevel = ENABLE_ALL;
	private final static String TAG = "middleware_" + BuildConfig.VERSION_NAME;
	
	public static void v(String tag, String msg){
		if(sLogLevel < ENABLE_VERBOSE)
			return;

		StringBuilder builder = new StringBuilder(tag)
				.append(": ").append(msg);
		android.util.Log.v(TAG, builder.toString());
	}
	
	public static void d(String tag, String msg){
		if(sLogLevel < ENABLE_DEBUG)
			return;
		StringBuilder builder = new StringBuilder(tag)
				.append(": ").append(msg);
		android.util.Log.d(TAG, builder.toString());
	}
	
	public static void i(String tag, String msg){
		if(sLogLevel < ENABLE_INFO)
			return;
		StringBuilder builder = new StringBuilder(tag)
				.append(": ").append(msg);
		android.util.Log.i(TAG, builder.toString());
	}
	
	public static void w(String tag, String msg){
		if(sLogLevel < ENABLE_WARN)
			return;
		StringBuilder builder = new StringBuilder(tag)
				.append(": ").append(msg);
		android.util.Log.w(TAG, builder.toString());
	}
	
	public static void e(String tag, String msg){
		if(sLogLevel <= ENABLE_ERROR)
			return;
		StringBuilder builder = new StringBuilder(tag)
				.append(": ").append(msg);
		android.util.Log.e(TAG, builder.toString());
	}
}