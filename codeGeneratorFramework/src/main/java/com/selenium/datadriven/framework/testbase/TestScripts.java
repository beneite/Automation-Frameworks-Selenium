package com.selenium.datadriven.framework.testbase;

import java.util.Properties;

import com.selenium.datadriven.framework.util.CommonFunctions;
import com.selenium.datadriven.framework.util.FunctionalLibrary;

public class TestScripts extends FunctionalLibrary {
	
	CommonFunctions commonFunctions=new CommonFunctions();
	public String methodName;
	
			public void demoModule() throws Exception {
		
				methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println(methodName);
				//#1.	
				launchBrowserAndRequiredUrl(CommonFunctions.properties.getProperty("prop.RandomURL"));
				//#2.	
				loginPage(0,methodName);
					commonFunctions.updateReport("Enter UserName", "UserName entered","PASS");
				//#3.	
				loginPage(1,methodName);
					commonFunctions.updateReport("Enter Password", "Password entered","PASS");
				//#4.		
				loginPage(2,methodName);
					commonFunctions.updateReport("Login", "Login Successfull","PASS");
					
				commonFunctions.closeDriver();
			}

}
