package com.zilker.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

	public static String getCurrentFormattedDate(){
		 Date date = new Date();
	     SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd");
	    // System.out.println(formatdate.format(date));
	    
	     return formatdate.format(date);
	}
}
