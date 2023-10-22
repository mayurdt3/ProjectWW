package testpacakage;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
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
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import carwash.base.BaseClass;
import carwash.base.CarWash;
import configuration.ConfigFileReader;
import scripts.CarwashScript;
import utility.Utility;

public class BaseTest extends BaseClass {

	public ConfigFileReader prop;
	public Utility util;
	


	@BeforeClass
	public void startReporter() {
		String directory = System.getProperty("user.dir") + "//ExtentReports//";
		spark = new ExtentSparkReporter(directory + "ExtentReport" + timeStamp()+".html" );
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
			//test.pass(result.getName());
		} else if (result.getStatus() == ITestResult.SKIP) {
			
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIP", ExtentColor.YELLOW));
			//test.skip(result.getName());
		} else {

		}

	}

	@BeforeClass
	public void objectmethod() {

		util = new Utility(driver);
		prop = new ConfigFileReader(driver);
		carwash = new CarWash(driver);
		CarwashScript carwashObject = new  CarwashScript(driver);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterClass
	public void closereporter() {
		extent.flush();
	}

	@AfterClass
	public void close() {

		 driver.quit();
	}
}
