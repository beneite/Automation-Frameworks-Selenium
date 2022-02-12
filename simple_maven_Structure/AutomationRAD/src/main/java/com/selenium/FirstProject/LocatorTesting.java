package com.selenium.FirstProject;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LocatorTesting extends CommonFunctions {

	 static CommonFunctions cmnFunction = new CommonFunctions();

	/*public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/resources/chromedriver.exe");
		cmnFunction.launchBrowserAndURL("https://www.rahulshettyacademy.com/AutomationPractice/");

		WebElement footerDriver = driver.findElement(By.xpath("//div[@id='gf-BIG']/descendant::ul[1]"));
		// System.out.println(footerDriver.findElements(By.tagName("a")).size());

		List<WebElement> myList = footerDriver.findElements(By.tagName("a"));

		for (int i=2;i<myList.size();i++) {
			CommonFunctions.clickElementInNewTab(myList.get(i));
			Thread.sleep(3000);
		//	driver.navigate().back();
		}

	}*/

}
