package configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;


import carwash.base.BaseClass;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class ConfigFileReader extends BaseClass {
	WebDriver driver;

	public ConfigFileReader(WebDriver driver) {
		this.driver = driver;
	}
	
	

	private final String propertyFilePath = System.getProperty("user.dir")
			+ "/resources/config/configuration.properties";
	private Properties properties;

	public Properties initProp() {
		properties = new Properties();
		try (FileInputStream fileInputStream = new FileInputStream(propertyFilePath)) {
			properties.load(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	
	
	
	// Write properties to a property file
	public void writeProperty(String filePath, Properties properties) {
		try (FileOutputStream output = new FileOutputStream(propertyFilePath)) {
			properties.store(output, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	

	public String getProperty(String key) {
		return initProp().getProperty(key);

	}
	
	
	
	

	public WebDriver initializeDriver(String browserName) throws IOException {

		if (browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browserName.equals("firefox")) {			
			driver = new FirefoxDriver();
		} else if (browserName.equals("ie")) {
			
			driver = new InternetExplorerDriver();
		} else {
			System.out.println(browserName + " is not a valid browser");
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		return driver;

	}

	public String readExcel(int sheetVal, int row, int column) throws IOException {
		FileInputStream stream = new FileInputStream(System.getProperty("user.dir") + "//TestData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(stream);
		XSSFSheet sheet = workbook.getSheetAt(sheetVal);
		String s = sheet.getRow(row).getCell(column).getStringCellValue();
		System.out.println(s);
		return s;
	}
}
