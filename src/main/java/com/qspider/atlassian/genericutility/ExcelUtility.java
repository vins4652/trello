package com.qspider.atlassian.genericutility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
/**
 * ExcelUtility can be used to read the test case specific data from the excel sheet
 * @author Vinaykumar Mannur
 *
 */
public class ExcelUtility {
	
	/**
	 * This method will get the cell value in the string form
	 * @param sheetName
	 * @param rowNum
	 * @param cellNum
	 * @return String
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @author Vinaykumar Mannur
	 */
	public String readStringData(String sheetName, int rowNum, int cellNum)
			throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream("./src/test/resources/trelloAppTC.xlsx");
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.getCell(cellNum);
		String testCaseStringdata = cell.getStringCellValue();
		workbook.close();
		return testCaseStringdata;
	}
	
	/**
	 * This method will get the cell value in the double form
	 * @param sheetName
	 * @param rowNum
	 * @param cellNum
	 * @return double
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @author  Vinaykumar Mannur
	 */
	public double readNumericData(String sheetName, int rowNum, int cellNum)
			throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream("./src/test/resources/trelloAppTC.xlsx");
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.getCell(cellNum);
		double testCaseNumericdata = cell.getNumericCellValue();
		workbook.close();
		return testCaseNumericdata;
	}
	
	public int getLastCellNumInSheet(String sheetName, int rowNum) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream("./src/test/resources/trelloAppTC.xlsx");
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rowNum);
		short lastCellNum = row.getLastCellNum();
		workbook.close();
		return lastCellNum;
	}

}
