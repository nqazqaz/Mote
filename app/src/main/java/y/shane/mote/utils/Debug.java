package y.shane.mote.utils;

import android.util.Log;

public class Debug
{
	public static final int LV_NONE      = (-1);
	public static final int LV_FATAL     = 0;
	public static final int LV_EXCEPTION = 2;
	public static final int LV_ERROR     = 4;
	public static final int LV_WARNING   = 6;
	public static final int LV_LOG       = 8;
	public static final int LV_TRACE     = 10;
	
	public static final int level = LV_TRACE;
	
	public static void throwable(Throwable a_e)
	{
		String s = a_e.getMessage();
		doLog("THROWABLE", (s == null) ? "???" : s);
	}
	
	public static void fatal(String a_msg)
	{
		if (level >= LV_FATAL)
			doLog("FATAL", a_msg);
	}
	
	public static void exception(Exception a_e)
	{
		if (level >= LV_EXCEPTION)
			doLog("EXCEPTION", getExceptionMessage(a_e));
	}
	
	public static void exception(String a_e)
	{
		if (level >= LV_EXCEPTION)
			doLog("EXCEPTION", a_e);
	}
	
	public static void exception(Exception a_e, String a_msg) 
	{
		if (level >= LV_EXCEPTION)
			doLog("EXCEPTION", a_msg + "(" + getExceptionMessage(a_e) + ")");
	}
	
	public static void error(String a_msg)
	{
		if (level >= LV_ERROR)
			doLog("ERROR", a_msg);
	}
		
	public static void warning(String a_msg)
	{
		if (level >= LV_WARNING)
			doLog("WARNING", a_msg);
	}
		
	public static void log(String a_msg)
	{
		if (level >= LV_LOG)
			doLog("LOG", a_msg);
	}
	
	public static void trace(String a_msg)
	{
		if (level >= LV_TRACE)
			doLog("TRACE", a_msg);
	}

	private static void doLog(String a_header, String a_msg)
	{
		Log.d(a_header, a_msg);
	}
	
	private static String getExceptionMessage(Exception a_e)
	{
		String s = a_e.getMessage();
		return (s == null) ? "???" : s;
	}
}
