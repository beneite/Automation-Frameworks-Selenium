package com.selenium.datadriven.framework.util;

import org.openqa.selenium.By;

public class ObjectRepository {
	
	public static final By loginPage_txtUserName = By.xpath("//input[@name='txtUsername']");
	public static final By loginPage_txtPassWord = By.xpath("//input[@name='txtPassword']");
	public static final By loginPage_btnLogIn = By.xpath("//input[@value='Login']");

}
