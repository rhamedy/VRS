package com.vrs.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {

	public static Date currentDateInSqlFormat() {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			java.util.Date d = new java.util.Date(); 
			
			sdf.format(d); 
			
			Date current = new Date(d.getTime()); 
			
			return current; 

		} catch (Exception ex) {
			ex.printStackTrace();
			
			return null; 
		}
	}
	
	public static Date parseDateInSqlFormat(String source) { 
		try { 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			java.util.Date d = sdf.parse(source); 
			
			return new Date(d.getTime()); 
		} catch(ParseException ex) { 
			ex.printStackTrace(); 
			
			return null;
		}
	}
}
