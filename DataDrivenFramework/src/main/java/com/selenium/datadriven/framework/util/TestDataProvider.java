package com.selenium.datadriven.framework.util;

import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestDataProvider {

	@Test(dataProvider = "getTestData")
	public void sampleTestOne(Hashtable<String, String> table) {

	}

	// ***************** data Provider***************
	@DataProvider
	public static Object[][] getTestData(String dataFileNameP, String sheetNameP, String testNameP) {
		ReadExcelDataFile readData = new ReadExcelDataFile(
				System.getProperty("user.dir") + "\\src\\test\\resources\\" + dataFileNameP);

		String sheetName = sheetNameP;
		String testName = testNameP;

		// finds starting row of test case
		int startRowNum = 0;
		while (!readData.getCellData(sheetName, 0, startRowNum).equalsIgnoreCase(testName)) {
			startRowNum++;
		}

		int startTestCol = startRowNum + 1;
		int startTestRow = startRowNum + 2;

		// finds the No of Rows in TestCase..
		int row = 0;
		while (!readData.getCellData(sheetName, 0, startTestRow + row).equals("")) {
			row++;
		}

		// find the no of Columns...
		int col = 0;
		while (!readData.getCellData(sheetName, col, startTestCol).equals("")) {
			col++;
		}

		Object[][] dataSet = new Object[row][1];
		Hashtable<String, String> dataTable = null;
		// printing the Test Data...
		int dataRowNumber = 0, nr, nc;
		for (nr = startTestRow; nr < startTestRow + row; nr++) {
			dataTable = new Hashtable<String, String>();
			for (nc = 0; nc < col; nc++) {
				// dataSet[dataRowNumber][nc]=readData.getCellData(sheetName, nr, nc);
				String key = readData.getCellData(sheetName, nc, startTestCol);
				String value = readData.getCellData(sheetName, nc, nr);
				dataTable.put(key, value);
			}
			dataSet[dataRowNumber][0] = dataTable;
			dataRowNumber++;
		}
		return dataSet;
	}
}
