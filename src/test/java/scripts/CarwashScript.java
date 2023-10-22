package scripts;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.internal.BaseTestMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import carwash.base.BaseClass;
import carwash.base.CarWash;
import configuration.ConfigFileReader;
import utility.Utility;

public class CarwashScript extends BaseClass  {
	



	public CarwashScript(WebDriver driver) {
		this.driver = driver;
		prop = new ConfigFileReader(driver);
		util = new Utility(driver);
		carwash = new CarWash(driver);
		PageFactory.initElements(driver, this);
	}
	
	

	public static String  getAmmount(String s) {
		  int dollarIndex = s.indexOf('$');
	        if (dollarIndex != -1) {
	            String carString = s.substring(dollarIndex + 1).trim();
	           // System.out.println(carString);

//		  String[] parts = s.split("\\$");
//	        
//	        if (parts.length == 2) {
//	            String text = parts[0].trim(); // "Amount"
//	            String number = parts[1].trim();
	           
	return carString;
  }
			return s;


	}
//	public static void main(String[] args) {
//		getAmmount("Amount:  $4");
//	}

	public boolean isPaymentSuccessfull() {

		try {
			if (carwash.timer.isDisplayed()) {
				// test.log(Status.INFO, "Payment Successful");
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;
	}


//@Test (priority = 0)
	public void VerifylaunchUrl() {
		boolean a;
		driver.get(prop.getProperty("carWashUrl"));
		if (driver.getTitle().equals(prop.getProperty("HomepagePageTitle"))) {
			a = true;
		} else {
			a = false;
		}

		test = extent.createTest("Verify Full payment cycle");
		Assert.assertTrue(a);
		test.log(Status.INFO, "Launching the App");

	}

	//@Test(priority = 0)
	public void VerifySelectAmount(String amount) {
		util.waitForVisibilityOfElement(carwash.manualAmount);
		Assert.assertTrue(carwash.payUsingManualpayment(amount));
		test.log(Status.INFO, "Amount selected");
		util.waitForVisibilityOfElement(carwash.cardCvv);
		Assert.assertEquals(driver.getTitle(), prop.getProperty("PaymentPageTitle"));
		test.log(Status.INFO, "navigate to PaymentGateWay");
	}

	//@Test(priority = 0)
	public void verifyEntercardDetails() {
		carwash.enterCardNum(prop.getProperty("CardNo"));
		carwash.enterCardExpiryDate(prop.getProperty("CardExpiry"));
		carwash.enterCardCvv(prop.getProperty("CardCvv"));
		carwash.selectAuthorizeBtn();
	}
	public void verifyCountdownPage() {
		
	try {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOf(carwash.carwashLogo));
		wait.until(ExpectedConditions.visibilityOf(carwash.timer));
		test.log(Status.INFO, "navigate to payment success page");
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
	}

	//@Test
	public void verifyAmount() {
		// carwash.selectDoneAfterCountdown();
		carwash.selectDoneAfterSomeTime();
		test.log(Status.INFO, "Select Done after countdown");
		util.waitForElementWithFrequency(carwash.billAmount, 20, 2);
		System.out.println(getAmmount(carwash.billAmount.getText()));
		Assert.assertTrue(getAmmount(carwash.billAmount.getText()).equals(prop.getProperty("Amount")));
	}

	
	public boolean verifySuccessfulPayment() {
	
		return isPaymentSuccessfull();
	}
//	@Test
//	public void Payment() {
//
//		VerifylaunchUrl();
//		VerifySelectAmount();
//		verifyEntercardDetails();
//		try {
//			util.waitUntillVisibilityof(carwash.carwashLogo);
//			test.log(Status.INFO, "navigate to payment success page");
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		util.waitUntillVisibilityof(carwash.timer);
//		verifySuccessfulPayment();
//		
//		test.log(Status.INFO, "Payment is successful");
//
//		verifyNavigateToReceipt();
//
//	}

}
