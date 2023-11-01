package com.Remosys.WeVend.Manager;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;

import com.Remosys.WeVend.Base.BaseClass;

public class DrievrManagerClass extends BaseClass{
	protected WebDriver driver;
	
	public DrievrManagerClass(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * This method will take a string as browser name and returns WebDriver object
	 * reference for the selected browser
	 * 
	 * @param browserName
	 * @return
	 * @throws IOException
	 */
	public WebDriver initializeDriver(String browser) {
		switch (browser.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "ie":
			driver = new InternetExplorerDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}
		return driver;
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
