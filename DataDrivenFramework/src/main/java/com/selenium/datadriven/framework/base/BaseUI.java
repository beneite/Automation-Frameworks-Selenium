package com.selenium.datadriven.framework.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.selenium.datadriven.framework.util.DateUtils;
import com.selenium.datadriven.framework.util.ExtentReportManager;

public class BaseUI {

	public WebDriver driver;
	public Properties prop;
	public Actions builder;
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;

	// opens Browser...
	public void openBrowser(String browser) {

		try {
			if (browser.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
				driver = new ChromeDriver();
			}
		} catch (Exception e) {
			reportFail(e.getMessage());				
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream file = new FileInputStream(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\com\\selenium\\object\\repository\\projectConfig.properties");
				prop.load(file);
			} catch (Exception e) {
					reportFail(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// opens the URL...
	public void openURL(String WebsiteURlKey) {
		try {
		driver.get(prop.getProperty(WebsiteURlKey));
		reportPass(WebsiteURlKey+" Identified Successfully");
		}catch(Exception e) {
			reportFail(e.getMessage());
		}
	}

	// driver close...
	public void tearDown() {
		driver.close();
	}

	// driver quit...
	public void quitBrowser() {
		driver.quit();
	}

	// Sending data...
	public void enterText(String searchboxXpathKey, String dataKey) {
		try {
		getWebElement(searchboxXpathKey).sendKeys(dataKey);
		reportPass(dataKey+" Entered Successfully in Locator Element:"+searchboxXpathKey);
		}catch(Exception e) {
			reportFail(e.getMessage());
		}
	}

	// clicking the element....
	public void clickElement(String SearchButtonxPathKey) {
		try {
		getWebElement(SearchButtonxPathKey).click();
		reportPass("Element Found Successfull:"+SearchButtonxPathKey);
		}catch(Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	// verify Page title....
	public void verifyPageTitle(String pageTitle) {
		try {
			String actualTitle=driver.getTitle();
			Assert.assertEquals(actualTitle, pageTitle);
			logger.log(Status.INFO, "Page Title:"+pageTitle);
		}catch(Exception e) {
			reportFail(e.getMessage());
		}
	}

	/* Sending the data by Enter Action...
	public void enterInput(String searchboxXpathKey, String dataKey) {
		builder = new Actions(driver);
		try {
		getWebElement(searchboxXpathKey).sendKeys(prop.getProperty(dataKey), Keys.RETURN); // builder.sendKeys(Keys.RETURN)
		reportPass(prop.getProperty(dataKey)+" Entered Successfully in Locator Element:"+searchboxXpathKey);
		}catch(Exception e) {
			reportFail(e.getMessage());
		}
	}*/

	// Getting WebElement by Xpath,CSS,Name,Id...
	public WebElement getWebElement(String locatorKey) {

		WebElement element = null;

		try {
			if (locatorKey.endsWith("_Xpath")) {						// xpath
				element = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
				logger.log(Status.INFO,"Locator Identified: "+locatorKey);
			}else if (locatorKey.endsWith("_Id")) {						// id
				element = driver.findElement(By.id(prop.getProperty(locatorKey)));
				logger.log(Status.INFO,"Locator Identified: "+locatorKey);
			}else if (locatorKey.endsWith("_CSS")) {					// CSS Selector
				element = driver.findElement(By.cssSelector(prop.getProperty(locatorKey)));
				logger.log(Status.INFO,"Locator Identified: "+locatorKey);
			}else if (locatorKey.endsWith("_Name")) {					// name
				element = driver.findElement(By.name(prop.getProperty(locatorKey)));
				logger.log(Status.INFO,"Locator Identified: "+locatorKey);
			}else if(locatorKey.endsWith("_ClassName")) {				// Class name
				element = driver.findElement(By.className(prop.getProperty(locatorKey)));
				logger.log(Status.INFO,"Locator Identified: "+locatorKey);
			}else {
				reportFail("TestCase Failed,Invalid Locator:" + locatorKey);
				Assert.fail("TestCase Failed,Invalid Locator:" + locatorKey);
			}
		} catch (Exception e) {
				reportFail(e.getMessage());
			
			e.printStackTrace();
			Assert.fail("***** TestCase Failed,Invalid Locator: " + e.getMessage());

		}

		return element;
	}

	// Reporting methods.....
	public void reportFail(String reportString)  {
		logger.log(Status.FAIL, reportString);
		try {
			takeScreenShotOnFailure();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		Assert.fail(reportString);
	}

	public void reportPass(String reportString) throws IOException {
		logger.log(Status.PASS, reportString);
		//takeScreenShotOnPass();
	}

	// takes the screen Shot on PASS...
	public void takeScreenShotOnPass() throws IOException {

		TakesScreenshot takeSS = (TakesScreenshot) driver;

		File source = takeSS.getScreenshotAs(OutputType.FILE);
		File destination = new File(
				System.getProperty("user.dir") + "\\screenshots\\" + DateUtils.getTimeStamp() + ".png");

		FileUtils.copyFile(source, destination); // using apache common io dependency....

		logger.addScreenCaptureFromPath(
				System.getProperty("user.dir") + "\\screenshots\\" + DateUtils.getTimeStamp() + ".png");
	}

	// takes the screen Shot on Failure...
	public void takeScreenShotOnFailure() throws IOException {

		TakesScreenshot takeSS = (TakesScreenshot) driver;

		File source = takeSS.getScreenshotAs(OutputType.FILE);
		File destination = new File(
				System.getProperty("user.dir") + "\\screenshots\\" + DateUtils.getTimeStamp() + ".png");

		FileUtils.copyFile(source, destination); // using apache common io dependency....

		logger.addScreenCaptureFromPath(
				System.getProperty("user.dir") + "\\screenshots\\" + DateUtils.getTimeStamp() + ".png");
	}

}
