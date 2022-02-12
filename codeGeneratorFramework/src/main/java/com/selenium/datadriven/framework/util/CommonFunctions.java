package com.selenium.datadriven.framework.util;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonFunctions extends ObjectRepository{
	public static FileInputStream fis= null;
	public static FileOutputStream fos = null;
	public static XSSFWorkbook workbook = null;
	public static XSSFSheet sheet = null;
	public static XSSFCell cell = null;
	public static XSSFRow row = null;
	String xlFilePath;
	public static WebDriver driver=null;
	private static File resutlTextFile;
	private static File resultTextFileInResultDirectory;
	private static File resutlHtmlFile;
	public static int numOfStep=1;
	private static File resutlPdfFile;
	private static File resultPDFFileInResultDirectory;
	public static int numOfScreenshot=1;
	public static int passedSteps=0;
	public static int failedSteps=0;
	public static String finalStatus="";
	public static String path="";
	static String datasheet=System.getProperty("user.dir")+"\\src\\test\\resources\\testDataSheet\\testDataFile.xlsx";
	public static Properties properties;
	private BufferedWriter bufferedWriter;
	@SuppressWarnings("deprecation")
	public static WebDriver lanuchBrowser(String strURL) throws InterruptedException {
		
			ChromeOptions options = new ChromeOptions();
			options.addArguments("chrome.switches","--disable-extensions");
			options.addArguments("--disable-popup-blocking");

			//Capabilities of Chrome
			/*DesiredCapabilities caps= new DesiredCapabilities();
			caps = DesiredCapabilities.chrome();
			*/options.setCapability(ChromeOptions.CAPABILITY, options);
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\chromedriver.exe");
			//driver = new ChromeDriver(caps);
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS) ;
			launchURL(strURL);
			 driver.manage().window().maximize();
		
		
		return driver; //Launch Browser to return Web Driver
	}
	public static void launchURL(String strURL) throws InterruptedException
	{
		driver.get(strURL);	
		
	}
	
	public Properties loadPropertiesFile() throws IOException {
		properties=new Properties();
		File propFile=new File(System.getProperty("user.dir")+"\\project.properties");
		
		FileInputStream fstream=new FileInputStream(propFile);
		properties.load(fstream);
		return properties;
	}
	
	public static void performactiononobject(String objname, String input, int check) throws NoSuchFieldException,
	SecurityException, IllegalArgumentException, IllegalAccessException, IOException, InterruptedException {
		Actions act = new Actions(driver);
		String[] perform = objname.split("_");
		String objectname = perform[1];
		String btnname=perform[0];
		String action = objectname.substring(0, 3);
		Class<ObjectRepository> r = ObjectRepository.class;
		Field field = r.getField(objname);
		ObjectRepository objectInstance = new ObjectRepository();
		Object value = field.get(objectInstance);
		Thread.sleep(4000);
		WebDriverWait wait=new WebDriverWait(driver, 40);
		/**
		 * commented 114 line added 115 
		 */
		//ExpectedConditions.presenceOfElementLocated((By) value);
		wait.until(ExpectedConditions.presenceOfElementLocated((By) value));
		WebElement elem = driver.findElement((By) value);
		scrollDownToElementInline(elem);
		try {
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", elem);
		Thread.sleep(2000);
		 ((JavascriptExecutor) driver).executeScript("arguments[0].style.border=''", elem);
		}catch(Exception e) {
		
		}
		switch (action) {

		case "btn":
			try
			{
				//elem.click();
			act.moveToElement(elem).click(elem).build().perform();
			//Report.logsuccess(btnname + " is clicked.");
			}
			catch(Exception e)
			{
				System.out.println("btn clicked with java script");
				clickWithJavaScript(elem);
			}
			Thread.sleep(3000);

			break;

		case "lnk":
			try {
				
			elem.click();
				
			}catch(Exception e) {
				clickWithJavaScript(elem);
				System.out.println("lnk clicked with java script");
			}
			Thread.sleep(3000);
			//Report.logsuccess(btnname + " is clicked.");
			break;

		case "chk":
			if (check == 1) {
				if (elem.isSelected())
					break;
				else
					elem.click();
				//Report.logsuccess(btnname + " is checked");
			}

			else if (check == 0) {
				if (elem.isSelected())
					;
				elem.click();
				break;

			}
		case "txt":
			elem.clear();
			try {
			act.moveToElement(elem).click(elem).sendKeys(input).build().perform();
			}catch(Exception e) {
				elem.sendKeys(input);
			}
			Thread.sleep(1000);

			//Report.logsuccess(btnname + " is filled with " + input);
			break;
		
		case "rad":

			if (check == 1) {
				if (elem.isSelected())
					break;
				else
					elem.click();
				/*//Report.logsuccess(btnname + " is checked");*/
			}

			else if (check == 0) {
				if (elem.isSelected())
					;
				elem.click();
				break;

			}
		
		case "lst":
			//selectListItem(objname, input);
			break;

		default:
			System.out.println("Please rename the object accordingly");
			break;

		}Thread.sleep(4000);
	}
	
public static void scrollDownToElementInline(WebElement element) {
		
		WebDriverWait wait = new WebDriverWait(driver, 40);
	    wait.until(ExpectedConditions.visibilityOf(element));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'auto', block: 'center'});",element);
	}

public static void clickWithJavaScript(WebElement element) {
	
	WebDriverWait wait = new WebDriverWait(driver, 40);
    wait.until(ExpectedConditions.visibilityOf(element));
	((JavascriptExecutor) driver).executeScript("arguments[0].click();",element);
}
public static int getStaringRowOfCaseFrmExcel(String sheetName, String testCaseName, String xlFilePath) throws IOException {
	int startRowNum=0;
	fis = new FileInputStream(xlFilePath);
	workbook = new XSSFWorkbook(fis);
	fis.close();			
	sheet = workbook.getSheet(sheetName);
	for(int i=0; i<=sheet.getLastRowNum();i++)
	{
		row=sheet.getRow(i);
		row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
		if(row.getCell(0).getStringCellValue().trim().equals(testCaseName))
		{
			startRowNum = i+1;
			break;
		}
	}
	return startRowNum;
}
public void closeDriver() {
	// TODO Auto-generated method stub
	driver.close();
}

public static String getExceldata(String sheetName, String colName, int rowIDccsLoginPage, String xlFilePath)
{
	String cellValue="";
	try
	{
		//xlFilePath = xlFilePath;
		fis = new FileInputStream(xlFilePath);
		workbook = new XSSFWorkbook(fis);
		FormulaEvaluator evaluator=workbook.getCreationHelper().createFormulaEvaluator();
		fis.close();			
		int colNum=-1;
		sheet = workbook.getSheet(sheetName);
		row=sheet.getRow(0);
		for(int i=0; i<row.getLastCellNum();i++)
		{
			row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
			if(row.getCell(i).getStringCellValue().trim().equals(colName))
				colNum = i;
		}
		row = sheet.getRow(rowIDccsLoginPage - 1);
		cell=row.getCell(colNum);
		if(cell!=null)
		{
			if((cell.getCellType()==Cell.CELL_TYPE_FORMULA) )
			{
				CellValue cellV = evaluator.evaluate(cell);
				if((cellV.getCellType()==Cell.CELL_TYPE_NUMERIC) )
				{
					DataFormatter formatter=new DataFormatter();
					cellValue=formatter.formatCellValue(cell);
					System.out.println("cellValue"+cellValue);
					//cellValue=""+cell.getDateCellValue();
				}
				else
				{
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cellValue=cellV.getStringValue();
				}
			}

			else if((cell.getCellType()==Cell.CELL_TYPE_NUMERIC) && DateUtil.isCellDateFormatted(cell))
			{
				DataFormatter formatter=new DataFormatter();
				cellValue=formatter.formatCellValue(cell);
				System.out.println("cellValue"+cellValue);
				//cellValue=""+cell.getDateCellValue();
			}
			else
			{
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue=cell.getStringCellValue();
			}
		}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	//System.out.println("cellValue"+cellValue);
	return cellValue;
}
public  void  updateReport(String desc, String expected, String status) throws Exception {
	// TODO Auto-generated method stub
	File screenshotDirectory=new File(path+File.separator+"ScreenShots");
	screenshotDirectory.mkdir();
	String screenShotPath=path+File.separator+"ScreenShots"+File.separator+numOfStep+"_"+numOfScreenshot+".png";
	String screenshotLinkPath=".."+File.separator+"ScreenShots"+File.separator+numOfStep+"_"+numOfScreenshot+".png";
	takeScreenshot(screenShotPath);
	if(status.equalsIgnoreCase("Pass"))
		passedSteps++;
	else
		failedSteps++;
	
	createHTMLLog(""+numOfStep, desc, expected, status, screenshotLinkPath);
	numOfScreenshot++;
	numOfStep++;
}
public  void  updateReport(String status) throws Exception {
	// TODO Auto-generated method stub
	File screenshotDirectory=new File(path+File.separator+"ScreenShots");
	screenshotDirectory.mkdir();
	String screenShotPath=path+File.separator+"ScreenShots"+File.separator+numOfStep+"_"+numOfScreenshot+".png";
	String screenshotLinkPath=".."+File.separator+"ScreenShots"+File.separator+numOfStep+"_"+numOfScreenshot+".png";
	takeScreenshot(screenShotPath);
	if(status.equalsIgnoreCase("Pass"))
	{
		//passedSteps++;
	}
	else
		failedSteps++;
	
	createHTMLLog("", "", "", status, screenshotLinkPath);
	numOfScreenshot++;
}
public void takeScreenshot(String screenshotPath) throws Exception {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension screenSize = toolkit.getScreenSize();
	Rectangle rectangle = new Rectangle(0, 0, screenSize.width,
			screenSize.height);
	Robot robot;

	try {
		robot = new Robot();
	} catch (AWTException e) {
		e.printStackTrace();
		throw new Exception(
				"Error while creating Robot object (for taking screenshot)");
	}

	BufferedImage screenshotImage = robot.createScreenCapture(rectangle);
	File screenshotFile = new File(screenshotPath);

	try {
		ImageIO.write(screenshotImage, "png", screenshotFile);
	} catch (IOException e) {
		e.printStackTrace();
		throw new Exception(
				"Error while writing screenshot to .jpg file");
	}
}
public void createHTMLLog(String stepNum,String stepDesc,String stepExpec, String status, String screenShotPath) throws IOException
{
	String content="<tr><td class='justified'>"+stepNum+"</td><td class='justified'>"+stepDesc+"</td><td class='justified'>"+stepExpec+"</td><td class='justified'>"+status+"</td><td class='justified'><a href='"+screenShotPath+"'><img width='50%' height='40%' src='"+screenShotPath+"'/></a></td><td>"+LocalDateTime.now()+"</td></tr>";
	appendResultToHtmlResultFile(content);
}

public void createTextResult() throws IOException {
	bufferedWriter=new BufferedWriter(new FileWriter(resutlTextFile, true));
	bufferedWriter.write(finalStatus);
	bufferedWriter.close();
	bufferedWriter=new BufferedWriter(new FileWriter(resultTextFileInResultDirectory, true));
	bufferedWriter.write(finalStatus);
	bufferedWriter.close();
	System.out.println(finalStatus);
	finalStatus="";
	
	
}

public void appendResultToHtmlResultFile(String content) throws IOException
{
	bufferedWriter=new BufferedWriter(new FileWriter(resutlHtmlFile, true));
	bufferedWriter.write(content);
	bufferedWriter.close();
}
public void createHTMLHeader() throws IOException
{
	String header="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" + 
			"<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
			"<head>"+
			"<style type='text/css'>"+
			"\t\t\t td { \n" +
			"\t\t\t\t padding: 8px; \n" +
			"\t\t\t\t text-align: inherit\\0/; \n" +
			"\t\t\t\t word-wrap: break-word; \n" +
			"\t\t\t\t max-width: 450px; \n" +
			"\t\t\t } \n\n" +
			"\t\t\t th { \n" +
			"\t\t\t\t padding: 8px; \n" +
			"\t\t\t\t text-align: inherit\\0/; \n" +
			"\t\t\t\t word-break: break-all; \n" +
			"\t\t\t\t max-width: 450px; \n" +
			"\t\t\t\t background-color:#E7ED63; \n" +
			"\t\t\t } \n\n" +
			"\t\t\t img { \n" +
			"\t\t\t\t width:325px; \n" +
			"\t\t\t\t height:250px; \n" +
			"\t\t\t } \n" +
			"\t\t\t td.justified { \n" +
				"\t\t\t\t text-align: justify; \n" +
			"\t\t\t } \n\n" +
			"\t\t\t table { \n" +
			"\t\t\t\t width: 95%; \n" +
			"\t\t\t\t margin-left: auto; \n" +
			"\t\t\t\t margin-right: auto; \n" +
			"\t\t\t\t background-color: #21E8F5; \n" +
		"\t\t\t } \n\n" +
			"</style>"+
			"</head>"+
			"<table border='3'><tbody><tr><th>Step N.O</th><th>Step Description</th><th>Step Expected</th><th>Status</th><th>screenShot</th><th>excutionTime</th></tr>";
	appendResultToHtmlResultFile(header);
}
public void createRequiredReportFiles(String testCase) throws IOException {
	String time=LocalTime.now().toString();
	path=new File(System.getProperty("user.dir")).getAbsolutePath()+File.separator+"Results"+File.separator+"Run_"+testCase+"_"+LocalDate.now()+"_"+time.substring(0,time.lastIndexOf(":")).replaceAll(":","_" );
	File rundirectoryPath=new File(path);
	rundirectoryPath.mkdir();
	File txtdirectoryPath=new File(path+File.separator+"TextResults");
	txtdirectoryPath.mkdir();
	File htmldirectoryPath=new File(path+File.separator+"HTMLResults");
	htmldirectoryPath.mkdir();
	File pdfdirectoryPath=new File(path+File.separator+"PDFResults");
	pdfdirectoryPath.mkdir();
	
	resutlTextFile = new File(path+File.separator+"TextResults"+File.separator+testCase+".txt");
	resutlTextFile.createNewFile();
	resultTextFileInResultDirectory=new File(new File(System.getProperty("user.dir")).getAbsolutePath()+File.separator+"Results"+File.separator+testCase+".txt");
	resultPDFFileInResultDirectory=new File(new File(System.getProperty("user.dir")).getAbsolutePath()+File.separator+"Results"+File.separator+testCase+".pdf");
	if(resultPDFFileInResultDirectory.exists())
	resultTextFileInResultDirectory.delete();
	resultTextFileInResultDirectory.createNewFile();
	 resutlHtmlFile = new File(path+File.separator+"HTMLResults"+File.separator+testCase+".html");
	resutlHtmlFile.createNewFile();
	 resutlPdfFile = new File(path+File.separator+"PDFResults"+File.separator+testCase+".pdf");
	resutlPdfFile.createNewFile();
	if(resultPDFFileInResultDirectory.exists())
	resultPDFFileInResultDirectory.delete();
	resultPDFFileInResultDirectory.createNewFile();
	
}
}
