package configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

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
	
	
	
	


	
	
	
}
