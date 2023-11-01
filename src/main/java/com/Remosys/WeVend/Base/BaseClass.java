package com.Remosys.WeVend.Base;

import org.openqa.selenium.WebDriver;

import com.Remosys.WeVend.Manager.DrievrManagerClass;
import com.Remosys.WeVend.PageLayer.CarWash;
import com.Remosys.WeVend.PageLayer.PaymentGateway;
import com.Remosys.WeVend.PageLayer.WevendStore;
import com.Remosys.WeVend.Reader.ConfigUtil;
import com.Remosys.WeVend.Reader.ExcelFileReader;
import com.Remosys.WeVend.utility.Utility;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * This class provides a foundation for all page classes. It contains
 * common properties and object variables of all the classes that are 
 * used across multiple classes of the application.
 * 
 * @author Mayur Takaliar
 * Usage:
 * 1. Extend this class when creating specific page classes for different web pages.
 * 2. Implement page-specific elements and methods in the derived classes.
 * 3. Reuse common methods and elements from this base class in the derived page classes.  
 *
 */
public class BaseClass {

	public WebDriver driver;
	
	public ExcelFileReader excel;
	public Utility util;
	public ConfigUtil prop;
	
	
	public CarWash carwash;
	public WevendStore wevend ;
	public PaymentGateway pay;
	
	public DrievrManagerClass manager;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentSparkReporter spark;
}