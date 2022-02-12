package com.selenium.datadriven.framework.util;

import org.openqa.selenium.WebDriver;

public class FunctionalLibrary extends ObjectRepository{
	static String datasheet=System.getProperty("user.dir")+"\\src\\test\\resources\\testDataSheet\\testDataFile.xlsx";
	static CommonFunctions cmnFunctn=new CommonFunctions();
	public static  WebDriver driver=null ;
	
public void launchBrowserAndRequiredUrl(String strURL) throws InterruptedException {
	driver = CommonFunctions.lanuchBrowser(strURL);
}
public void launchRequiredUrl(String strURL) throws InterruptedException {
	CommonFunctions.launchURL(strURL);
}

public  static void loginPage (int indexOfTestData,String testCaseName) throws Exception
{
	int rowIDloginPage=CommonFunctions.getStaringRowOfCaseFrmExcel("loginPage", testCaseName, datasheet)+indexOfTestData;
    String loginPage_txtUserName=CommonFunctions.getExceldata("loginPage","txtUserName",rowIDloginPage, datasheet);
    String loginPage_txtPassWord=CommonFunctions.getExceldata("loginPage","txtPassWord",rowIDloginPage, datasheet);
    String loginPage_btnLogIn=CommonFunctions.getExceldata("loginPage","btnLogIn",rowIDloginPage, datasheet);
    
    if (!loginPage_txtUserName.equals(""))
    {
        CommonFunctions.performactiononobject("loginPage_txtUserName",loginPage_txtUserName, 0);
    }

    if (!loginPage_txtPassWord.equals(""))
    {
        CommonFunctions.performactiononobject("loginPage_txtPassWord",loginPage_txtPassWord, 0);
    }

    if (!loginPage_btnLogIn.equals(""))
    {
        CommonFunctions.performactiononobject("loginPage_btnLogIn",loginPage_btnLogIn, 0);
    }
}



}
