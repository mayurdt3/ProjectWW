package configuration;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import base.BaseClass;
/**
 * ExcelFileReader class for reading and managing data stored in Excel spreadsheets.
 *
 * @author Mayur Takalikar
 *
 * Usage:
 * 1. Create an instance of ExcelFileReader to work with Excel files.
 * 2. Retrieve data from specific cells or rows using methods like getCellValue or getRowCount.
 * 4. Handle exceptions that may occur during file loading or data retrieval.
 *
 */
public class ExcelFleReader extends BaseClass{
	XSSFWorkbook work_book;
	XSSFSheet sheet;

	public ExcelFleReader() {
		try {
			FileInputStream stream = new FileInputStream(System.getProperty("user.dir") + "//TestData.xlsx");
			work_book = new XSSFWorkbook(stream);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String getCellValue(int sheetnumber, int row, int column) {
		sheet = work_book.getSheetAt(sheetnumber);
		String data = sheet.getRow(row).getCell(column).getStringCellValue();
		return data;
	}

	public int getRowCount(int sheetIndex) {
		int row = work_book.getSheetAt(sheetIndex).getLastRowNum();
		row = row + 1;
		return row;
	}
}
