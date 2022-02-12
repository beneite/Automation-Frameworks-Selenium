package com.selenium.datadriven.framework.util;

public class ReadTestData {

	public static void main(String args[]) {

		ReadExcelDataFile readData = new ReadExcelDataFile(
				System.getProperty("user.dir") + "\\src\\test\\resources\\TestData.xlsx");

		String sheetName = "SampleData";
		String testName = "Test One";

		// finds starting row of test case
		int startRowNum = 0;
		while (!readData.getCellData(sheetName, 0, startRowNum).equalsIgnoreCase(testName)) {
			startRowNum++;
		}
		System.out.println("Test Starts from:"+startRowNum);
		
		int startTestCol=startRowNum+1;
		int startTestRow=startRowNum+2;
		
		//finds the No of Rows in TestCase..
		int row=0;
		while(!readData.getCellData(sheetName, 0, startTestRow+row).equals("")) {
			row++;
		}
		System.out.println("The no of Test Rows are:"+row);
		
		// find the no of Columns...
		int col=0;
		while(!readData.getCellData(sheetName, col, startTestCol).equals("")) {
			col++;
		}
		System.out.println("The no of Test Columns are:"+col);
		
		// printing the Test Data...
		for(int nr=startTestRow;nr<startTestRow+row;nr++)
			for(int nc=0;nc<col;nc++) {
				System.out.println(readData.getCellData(sheetName, nc, nr));
				
			}
	}

}
