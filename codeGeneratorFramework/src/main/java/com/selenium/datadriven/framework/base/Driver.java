package com.selenium.datadriven.framework.base;

import java.io.IOException;
import java.util.Properties;

import com.selenium.datadriven.framework.testbase.TestScripts;
import com.selenium.datadriven.framework.util.CommonFunctions;

public class Driver {

	public static Properties prop;

	public static void main(String[] args) throws IOException, InterruptedException {
		CommonFunctions commonFunctions = new CommonFunctions();
		TestScripts tstScripts = new TestScripts();
		
		try {
			prop = commonFunctions.loadPropertiesFile();
			
			for(String testcase:args[0].split(","))
			{
			
				System.out.println(testcase);
				commonFunctions.createRequiredReportFiles(testcase);
				commonFunctions.createHTMLHeader();

			switch (testcase.trim()) {
			
		// Test case1:	
			case "demoModule":
				tstScripts.demoModule();
				break;
			}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		

}
}
