package base;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import carwash.base.CarWash;
import configuration.ConfigFileReader;

import utility.Utility;
import weVendStore.base.WevendStore;
/**
 * 
 * @author Mayur
 * info of class
 *
 */
public class BaseClass {

	public WebDriver driver;
	public ConfigFileReader prop;
	//public ConfigFileReader config;
	public Utility util;

	protected static ExtentReports extent;
	protected static ExtentTest test;
	protected static ExtentSparkReporter spark;

	
	public CarWash carwash;
	public WevendStore wvStore;

}