package com.selenium.datadriven.framework.logintest;

import java.util.Hashtable;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.selenium.datadriven.framework.base.BaseUI;
import com.selenium.datadriven.framework.util.TestDataProvider;

public class ZohoLoginTest extends BaseUI {

	@Test(dataProvider = "getZoloTestData")
	public void doZohoLoginTest(Hashtable<String, String> dataTable) throws Exception {

		logger = report.createTest("Zoho Login Test:" + dataTable.get("Comment"));
		openBrowser("Chrome");
		openURL("ZohoWebsiteURL");
		Thread.sleep(2000);
		clickElement("ZohoLogin_Xpath");
		enterText("ZohouserName_Xpath", dataTable.get("User Name"));
		Thread.sleep(2000);
		clickElement("ZohoEmailSubmit_Xpath");
		Thread.sleep(2000);
		enterText("ZohopassWord_Xpath", dataTable.get("Password"));

		takeScreenShotOnPass();
		clickElement("ZohoSubmit_Xpath");
		Thread.sleep(2000);
		takeScreenShotOnPass();
		verifyPageTitle(dataTable.get("Page Title"));
		tearDown();

	}

	@DataProvider
	public Object[][] getZoloTestData() {
		return TestDataProvider.getTestData("ZohoTestData.xlsx", "Login Test", "doZohoLoginTest");
	}

	@AfterTest
	public void report() {
		report.flush();
	}
}
