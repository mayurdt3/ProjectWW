package com.Remosys.WeVend.Reader;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.Remosys.WeVend.Base.BaseClass;

import java.util.HashMap;
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
public class ExcelFileReader extends BaseClass{
	XSSFWorkbook workBook;
	XSSFSheet sheet;

	public ExcelFileReader() {
		try {
			FileInputStream stream = new FileInputStream(System.getProperty("user.dir") + "//TestData.xlsx");
			workBook = new XSSFWorkbook(stream);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * This Method will read the excel and return a given cell value
	 * @param sheetnumber
	 * @param row
	 * @param column
	 * @return  
	 */
	public String getCellValue(int sheetnumber, int row, int column) {
		try{
			sheet = workBook.getSheetAt(sheetnumber);
		String data = sheet.getRow(row).getCell(column).getStringCellValue();
		return data;
	}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * This method is used to read Excel data using Hash map which will store the data in key and value format
	 * 
	 * @param excelKey  : String parameter for the test Data key 
	 * @return
	 * @throws IOException
	 */
	public String getExcelvalueForKey(String excelKey) {
		sheet = workBook.getSheetAt(0); 

  HashMap<String, String> testDataMap = new HashMap<>();

     for (Row row : sheet) {
        if (row.getCell(0) != null && row.getCell(1) != null) {
           String key = row.getCell(0).getStringCellValue();
           String value = row.getCell(1).getStringCellValue();
           testDataMap.put(key, value);
        }
     }
     for ( String key : testDataMap.keySet()) {
    	String value = testDataMap.get(key);
    	 // System.out.println("Key: " + key + ", Value: " + value);
    	 if (key.equalsIgnoreCase(excelKey)){
        return   value;
     }
     }
     return null;
}
	
//	public static void main(String[] args) {
//		ExcelFleReader obj = new ExcelFleReader();
//		try {
//			System.out.println(obj.getExcelvalueForKey("product3"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}

