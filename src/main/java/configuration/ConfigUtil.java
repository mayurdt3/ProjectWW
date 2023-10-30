package configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.io.FileHandler;

import base.BaseClass;
/**
 * ConfigFileReader class for reading and managing application configuration settings.
 * This class provides methods to read and parse configuration files, typically in formats like
 * properties (key-value pairs) It allows easy access to configuration parameters
 * used by the application, centralizing configuration management for improved maintainability.
 *
 * @author Mayur Takalikar
 *
 * * Usage:
 * 1. Create an instance of ConfigFileReader to read and access configuration settings.
 * 2. Load the configuration file using methods like initializeProperty.
 * 3. Access configuration parameters using methods like getProperty, setProperty and initializeDriver etc.
 */
public class ConfigUtil extends BaseClass {
	WebDriver driver;
	

	public ConfigUtil(WebDriver driver) {
		this.driver = driver;
	}
	
	
	private final String propertyFilePath = System.getProperty("user.dir")
			+ "/resources/config/configuration.properties";
	private Properties properties;
	
	
	
	
	/**
	 * This method will configure the Properties file and rerun properties object reference
	 * @return
	 */
	public Properties initProp() {
		properties = new Properties();
		try (FileInputStream fileInputStream = new FileInputStream(propertyFilePath)) {
			properties.load(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	
	/**
	 * This method will write properties to a property file
	 * @param filePath
	 * @param properties
	 */
	
	public void writeProperty(String filePath, Properties properties) {
		try (FileOutputStream output = new FileOutputStream(propertyFilePath)) {
			properties.store(output, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method Will return the String from the property file 
	 * @param key
	 * @return
	 */

	public String getProperty(String key) {
		return initProp().getProperty(key);

	}
	
	/**
	 * Generates a timestamp in a custom format as a string.
	 *
	 * @return A string for current timestamp 
	 */
	public String getTimeStamp() {
		Instant timestamp = Instant.now();

		// Define a custom timestamp format pattern
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy,MM,dd HH.mm.ss.SSS");

		// Format the timestamp as a string
		String timestampString = timestamp.atZone(java.time.ZoneId.systemDefault()).format(formatter);

		return timestampString;

	}
	

	/**
	 * configure take screenshot
	 */
	public void takeSS() {
		String directory = System.getProperty("user.dir") + "//ScreenShot//SS ";
		TakesScreenshot ss = (TakesScreenshot) driver;
		try {
			FileHandler.copy(ss.getScreenshotAs(OutputType.FILE), new File(directory + getTimeStamp() + ".png"));
		} catch (WebDriverException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	


	
	
	
}
