
package testpacakage;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import carwash.base.CarWash;
import configuration.ConfigFileReader;
import scripts.CarwashScript;

public class TestCarwash extends BaseTest {

	@Test
	public void CarwashPaymentTest() {
		CarwashScript carwashObject = new CarwashScript(driver);
		carwashObject.VerifylaunchUrl();
		carwashObject.VerifySelectAmount(prop.getProperty("Amount"));
		carwashObject.verifyEntercardDetails();
		carwashObject.verifyCountdownPage();
		carwashObject.verifySuccessfulPayment();
		carwashObject.verifyAmount();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//@Test
	public void Payment() {
		CarwashScript carwashObject = new CarwashScript(driver);
		carwashObject.VerifylaunchUrl();
		carwashObject.VerifySelectAmount(prop.getProperty("Amount"));
		carwashObject.verifyEntercardDetails();
		try {
			util.waitUntillVisibilityof(carwash.carwashLogo);
			test.log(Status.INFO, "navigate to payment success page");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		util.waitUntillVisibilityof(carwash.timer);
		carwashObject.verifySuccessfulPayment();
		
		test.log(Status.INFO, "Payment is successful");

		carwashObject.verifyAmount();

	}
	
	
}
