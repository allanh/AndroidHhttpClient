package com.fuhu.middleware.componet;

public class Log {
	public static int ENABLE_VERBOSE 	= 4;
	public static int ENABLE_DEBUG 		= 3;
	public static int ENABLE_INFO 		= 2;
	public static int ENABLE_WARN 		= 1;
	public static int ENABLE_ERROR 		= 0;
	public static int ENABLE_ALL 		= 5;
	
	public final static int sLogLevel = ENABLE_ALL;
	
	public static void v(String tag, String msg){
		if(sLogLevel < ENABLE_VERBOSE)
			return;
		android.util.Log.v(tag, msg);
	}
	
	public static void d(String tag, String msg){
		if(sLogLevel < ENABLE_DEBUG)
			return;
		android.util.Log.d(tag, msg);
	}
	
	public static void i(String tag, String msg){
		if(sLogLevel < ENABLE_INFO)
			return;
		android.util.Log.i(tag, msg);
	}
	
	public static void w(String tag, String msg){
		if(sLogLevel < ENABLE_WARN)
			return;
		android.util.Log.w(tag, msg);
	}
	
	public static void e(String tag, String msg){
		if(sLogLevel <= ENABLE_ERROR)
			return;
		android.util.Log.e(tag, msg);
	}
}