package testpackage;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import base.BaseClass;
import carwash.base.CarWash;
import configuration.ConfigUtil;
import configuration.ExcelFleReader;
import utility.Utility;
import weVendStore.base.WevendStore;

/**
 * Base test class serving as the foundation for all test classes.
 * 
 * This class provides common setup and teardown methods, EctentRrport
 * configuration, Logs, screenshot on failure configuration and flush methods,
 * property Configuration and Utilities that are shared across multiple test
 * cases. It follows the Test Automation Framework design pattern, promoting
 * code reusability and maintainability.
 * 
 * @author Mayur Takalikar
 *
 *         Usage: 1. Extend this class when creating specific test classes for
 *         different test scenarios. 2. Implement test cases by defining methods
 *         that perform specific actions and verifications. 3. Organize your
 *         test cases into specific test classes, each focusing on a particular
 *         application.
 * 
 */
public class BaseTest extends BaseClass {
	protected static ExtentTest test;
	/**
	 * This method used for configuring Extent Reports before TestNG suite
	 * execution. This method is responsible for setting up and configuring Extent
	 * Reports to prepare for generating detailed and organized test reports.
	 */
	
	

	
	@BeforeSuite
	public void startReporter() {
		prop = new ConfigUtil(driver);
		String directory = System.getProperty("user.dir") + "//ExtentReports//";
		spark = new ExtentSparkReporter(directory + "ExtentReport" + prop.getTimeStamp() + ".html");
		extent = new ExtentReports();
		extent.attachReporter(spark);

		/* Extent Environmentals */

		extent.setSystemInfo("OS", "Windows 11");
		extent.setSystemInfo("Browser", "chrome");
		extent.setSystemInfo("Tester", "Mayur");

		/* configuration to cheng looks of the report */

		spark.config().setDocumentTitle("Extent Report Demo");
		spark.config().setReportName("Test Rport");
		spark.config().setTheme(Theme.DARK);
		spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a'('zzz')'");
	}

	/**
	 * Initialize the WebDriver and configure browser settings for test execution.
	 * 
	 * @param browser
	 */
	@BeforeClass
	@Parameters("browser")
	public void setUp(String browser) {
		// public void setUp() {
		// String browser = "chrome";
		initializeDriver(browser);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

	}

	/**
	 * 
	 * Handles test result status and logs it in the test report.
	 * 
	 * @param result
	 */
	@AfterMethod
	public void testResult(ITestResult result) {
		
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " FAIL", ExtentColor.RED));
			prop.takeSS();

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASS", ExtentColor.GREEN));
			// test.pass(result.getName());
		} else if (result.getStatus() == ITestResult.SKIP) {

			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIP", ExtentColor.YELLOW));
			// test.skip(result.getName());
		} else {

		}

	}

	/**
	 * Initialize object instances. This method initializes utility, configuration
	 * reader, Excel file reader, and page object instances.
	 *
	 */

	@BeforeClass
	public void objectmethod() {

		util = new Utility(driver);
		prop = new ConfigUtil(driver);
		excel = new ExcelFleReader();
		carwash = new CarWash(driver);
		wevend = new WevendStore(driver);
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
	 * This method will close the browser Instance After execution of each test
	 * Class
	 */
	@AfterClass
	public void close() {

		driver.quit();
	}

	/**
	 * This Method Will Flush the report and create the .html file
	 */
	@AfterSuite
	public void closereporter() {
		extent.flush();
	}

}
