package base;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import carwash.base.CarWash;
import configuration.ConfigUtil;
import configuration.ExcelFleReader;
import paymentGatway.base.PaymentGateway;
import utility.Utility;
import weVendStore.base.WevendStore;
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

	protected WebDriver driver;
	protected ConfigUtil prop;
	protected ExcelFleReader excel;
	protected Utility util;

	protected static ExtentReports extent;
	protected static ExtentTest test;
	protected static ExtentSparkReporter spark;

	
	protected CarWash carwash;
	protected WevendStore wevend ;
	protected PaymentGateway pay;
}