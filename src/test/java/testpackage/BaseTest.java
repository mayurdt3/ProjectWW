package testpackage;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import base.BaseClass;
import carwash.base.CarWash;
import configuration.ConfigFileReader;
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

	@BeforeSuite
	public void startReporter() {
		String directory = System.getProperty("user.dir") + "//ExtentReports//";
		spark = new ExtentSparkReporter(directory + "ExtentReport" + timeStamp() + ".html");
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

	@BeforeClass
	public void setUp() {

		ChromeOptions op = new ChromeOptions();
		op.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(op);

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

	}

	@AfterMethod
	public void testResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " FAIL", ExtentColor.RED));
			takeSS();

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASS", ExtentColor.GREEN));
			// test.pass(result.getName());
		} else if (result.getStatus() == ITestResult.SKIP) {

			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIP", ExtentColor.YELLOW));
			// test.skip(result.getName());
		} else {

		}

	}

	@BeforeClass
	public void objectmethod() {

		util = new Utility(driver);
		prop = new ConfigFileReader(driver);
		excel = new ExcelFleReader();
		carwash = new CarWash(driver);
		wevend = new WevendStore(driver);

	}

	public String timeStamp() {
		Instant timestamp = Instant.now();

		// Define a custom timestamp format pattern
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy,MM,dd HH.mm.ss.SSS");

		// Format the timestamp as a string
		String timestampString = timestamp.atZone(java.time.ZoneId.systemDefault()).format(formatter);

		return timestampString;

	}

	public void takeSS() {
		String directory = System.getProperty("user.dir") + "//ScreenShot//SS ";
		TakesScreenshot ss = (TakesScreenshot) driver;
		try {
			FileHandler.copy(ss.getScreenshotAs(OutputType.FILE), new File(directory + timeStamp() + ".png"));
		} catch (WebDriverException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void close() {

		driver.quit();
	}

	@AfterSuite
	public void closereporter() {
		extent.flush();
	}

}
