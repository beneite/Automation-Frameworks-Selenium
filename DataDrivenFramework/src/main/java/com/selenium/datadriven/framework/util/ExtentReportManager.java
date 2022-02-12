package com.selenium.datadriven.framework.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReportManager {
	
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports report;
	
	
	public static ExtentReports getReportInstance() {
		
		if(htmlReporter== null && report==null) {
			
			String reportName=DateUtils.getTimeStamp()+".html";
			htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"\\output\\"+reportName);
			report=new ExtentReports();
			report.attachReporter(htmlReporter);
			
			report.setSystemInfo("OS :", "Windows 10");
			report.setSystemInfo("Browser :", "Chrome");
			
		}
		
		return report;
		
	}

}
