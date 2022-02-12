package com.selenium.FirstProject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonFunctions {
	
	
	public static WebDriver driver;
	
	
	public static void launchBrowserAndURL(String url) 
	{
		
		driver=new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
	}
	
	public static void clickElement(WebElement ele) throws InterruptedException 
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		wait.until(ExpectedConditions.visibilityOfElementLocated((By) ele));
		Thread.sleep(3000);
		 js.executeScript("arguments[0].scrollIntoView();",ele);
		 js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
		 ele.click();
		
	}
	
	//@SuppressWarnings("deprecation")
	public static void clickElementInNewTab(WebElement ele) throws InterruptedException 
	{
	//	WebDriverWait wait = new WebDriverWait(driver,10);
		JavascriptExecutor js=(JavascriptExecutor)driver;
	//	wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
		Thread.sleep(5000);
		 js.executeScript("arguments[0].scrollIntoView();",ele);
		 js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
		 String openInNewTab=Keys.chord(Keys.CONTROL,Keys.ENTER);
		 ele.sendKeys(openInNewTab);
		
	}

}
