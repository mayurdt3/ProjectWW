package carwash.base;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import configuration.ConfigFileReader;

import utility.Utility;

public class BaseClass {

	public WebDriver driver;
	public ConfigFileReader prop;
	public ConfigFileReader config;
	public Utility util;

	 protected static ExtentReports extent;
	protected static ExtentTest test;
	protected static ExtentSparkReporter spark;

	
	public CarWash carwash;

}