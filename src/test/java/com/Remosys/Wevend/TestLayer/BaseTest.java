package com.Remosys.Wevend.TestLayer;

import java.io.IOException;
import java.time.Duration;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.Remosys.WeVend.Base.BaseClass;
import com.Remosys.WeVend.Manager.DriverManagerClass;
import com.Remosys.WeVend.PageLayer.CarWash;
import com.Remosys.WeVend.PageLayer.GumBallPage;
import com.Remosys.WeVend.PageLayer.HabcoPage;
import com.Remosys.WeVend.PageLayer.WevendStore;
import com.Remosys.WeVend.Reader.ConfigUtil;
import com.Remosys.WeVend.Reader.ExcelFileReader;
import com.Remosys.WeVend.utility.Utility;
import com.aventstack.extentreports.ExtentReports;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * Base test class serving as the foundation for all test classes.
 * 
 * This class provides common setup and tear down methods, EctentRrport
 * configuration, Logs, screenshot on failure configuration and flush methods,
 * property Configuration and Utilities that are shared across multiple test
 * cases. It follows the Test Automation Framework design pattern, promoting
 * code re usability and maintainability.
 * 
 * @author Remosys Testing Team
 *
 *         Usage: 1. Extend this class when creating specific test classes for
 *         different test scenarios. 2. Implement test cases by defining methods
 *         that perform specific actions and verifications. 3. Organize your
 *         test cases into specific test classes, each focusing on a particular
 *         application.
 * 
 */
public class BaseTest extends BaseClass {

	/**
	 * This method used for configuring Extent Reports before TestNG suite
	 * execution. This method is responsible for setting up and configuring Extent
	 * Reports to prepare for generating detailed and organized test reports.
	 */

	@BeforeSuite
	public void startReporter() {

		util = new Utility();
		String directory = System.getProperty("user.dir") + "//ExtentReports//";
		spark = new ExtentSparkReporter(directory + "ExtentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(spark);

		/* Extent Environmental */

		extent.setSystemInfo("OS", "Windows 11");
		extent.setSystemInfo("Browser", "chrome");
		extent.setSystemInfo("Tester", "Remosys Testing Team");

		/* configuration to change looks of the report */

		spark.config().setDocumentTitle("Payment Cycle");
		spark.config().setReportName("WeVend Apps Payment Cycle");
		spark.config().setTheme(Theme.STANDARD);
		spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a'('zzz')'");
	}

	/**
	 * Initialize the WebDriver and configure browser settings for test execution.
	 * 
	 * @param browser
	 */
	@BeforeClass
	@Parameters("browser")
	public void setUp(@Optional("chrome") String browser) {

		manager = new DriverManagerClass(driver);
		driver = manager.initializeDriver(browser);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		habco = new HabcoPage(driver);
		util = new Utility();
		prop = new ConfigUtil();
		excel = new ExcelFileReader();
		carwash = new CarWash(driver);
		wevend = new WevendStore(driver);
		gumball = new GumBallPage(driver);
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
			String path = util.takeScreenshot(driver);
			
				try {
					test.addScreenCaptureFromPath(path);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			
			
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
	public void closeReporter() {
		extent.flush();
	}

}
