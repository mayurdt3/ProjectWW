package com.Remosys.WeVend.Reader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;

/**
 * ConfigFileReader class for reading and managing application configuration
 * settings. This class provides methods to read and parse configuration files,
 * typically in formats like properties (key-value pairs) It allows easy access
 * to configuration parameters used by the application, centralizing
 * configuration management for improved maintainability.
 *
 * @author Remosys Testing Team.
 *
 *         * Usage: 1. Create an instance of ConfigFileReader to read and access
 *         configuration settings. 2. Load the configuration file using methods
 *         like initializeProperty. 3. Access configuration parameters using
 *         methods like getProperty, setProperty and initializeDriver etc.
 */
public class ConfigUtil {

	protected WebDriver driver;
	private final String propertyFilePath = System.getProperty("user.dir")
			+ "/resources/config/configuration.properties";
	private Properties properties;

	/**
	 * This method will configure the Properties file and rerun properties object
	 * reference
	 * 
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
	 * 
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
	 * 
	 * @param key
	 * @return
	 */

	public String getProperty(String key) {
		return initProp().getProperty(key);

	}

}
