package com.selenium.FirstProject;

import org.openqa.selenium.By;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Calendar extends CommonFunctions {

	
	static CommonFunctions cmnFunction = new CommonFunctions();

	public static void main(String[] args) throws InterruptedException {
		
		
			
			//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/resources/chromedriver.exe");
		WebDriverManager.chromedriver().setup();
		cmnFunction.launchBrowserAndURL("https://www.path2usa.com/travel-companions");
		Thread.sleep(10000);
		driver.findElement(By.xpath("//input[@name='travel_date']")).sendKeys("12-23-2021");

		System.out.println("Passed");
	}

}
