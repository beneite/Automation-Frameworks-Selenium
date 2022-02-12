package com.selenium.datadriven.framework.logintest;
import java.util.Hashtable;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.selenium.datadriven.framework.base.BaseUI;
import com.selenium.datadriven.framework.util.ExtentReportManager;
import com.selenium.datadriven.framework.util.TestDataProvider;

public class LoginTest extends BaseUI{
	
	ExtentReports report=ExtentReportManager.getReportInstance();
	
	@Test(dataProvider="getTestOneData")
	public void testOne(Hashtable<String,String> dataTable) throws  Exception {
		
		logger=report.createTest("Test One");
		openBrowser("Chrome");	
		openURL("WebsiteURL");		
		enterText("userName_Xpath",dataTable.get("Col1"));	
		enterText("passWord_Xpath",dataTable.get("Col2"));	
		takeScreenShotOnPass();		
		clickElement("logIn_Xpath");
		Thread.sleep(2000);
		takeScreenShotOnPass();
		tearDown();
		
	}

	@DataProvider
	public Object[][] getTestOneData(){
		return	TestDataProvider.getTestData("TestData.xlsx","SampleData","Test One");
	}
	
	//@Test(dependsOnMethods="testOne")
	public void testTwo() throws IOException {
		logger=report.createTest("Test Two");
		openBrowser("Chrome");	
		openURL("WebsiteURL");		
		enterText("userName_Xpath","userName2");	
		enterText("passWord_Xpath","passWord2");	
		clickElement("logIn_Xpath");		
		tearDown();
		
	}
	
	//@Test(dependsOnMethods="testTwo")
	public void testThree() throws IOException {
		logger=report.createTest("Test Three");
		openBrowser("Chrome");	
		openURL("WebsiteURL");		
		enterText("userName_Xpath","userName3");	
		enterText("passWord_Xpath","passWord3");	
		clickElement("logIn_Xpath");		
		tearDown();
		
	}
	
	@AfterTest
	public void report() {
		report.flush();
	}
}
