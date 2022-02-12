package com.selenium.datadriven.framework.util;

import java.util.Date;

public class DateUtils {

	public static String getTimeStamp() {
		
		Date date=new Date();
		return (date.toString().replaceAll(":", "_").replaceAll(" ","_"));
	}
}


