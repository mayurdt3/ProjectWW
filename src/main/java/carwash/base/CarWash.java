package carwash.base;

import java.io.IOException;


import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import base.BaseClass;
import configuration.ConfigFileReader;
import paymentGatway.base.PaymentGateway;
import utility.Utility;


/**
 * This class encapsulates the elements, actions, and functionality related to a 'Car Wash' web application.
 * 
 * @author Mayur Takalikar.
 *
 * Usage:
 * 1. Create an instance of this class to interact with the web page.
 * 2. Implement actions and verifications relevant to the applications functionality.
 * 
 */

public class CarWash extends BaseClass {

	/**
	 * this constructor will initialize configFile and Utility class objects so that
	 * they can be used for this class
	 * 
	 * @param driver
	 */
	public CarWash(WebDriver driver) {
		this.driver = driver;
		prop = new ConfigFileReader(driver);
		util = new Utility(driver);
		
		PageFactory.initElements(driver, this);
	}

	/**
	 * All the Webelements for 'Carwash' application are listed below
	 */

	@FindBy(xpath = "//div[@class='amount_option']")
	public List<WebElement> amountOptions;

	@FindBy(xpath = "//div[@class='manual_amount']")
	public WebElement manualAmount;

	@FindBy(xpath = "//button[@class='arrow-btn decrement-btn']")
	public WebElement decrementBtn;

	@FindBy(xpath = "//button[@class='arrow-btn increment-btn']")
	public WebElement incrementBtn;

	@FindBy(xpath = "//button[@class='button false blue']")
	public WebElement payBtn;

	@FindBy(xpath = "//footer//img")
	public WebElement footerLogo;

	@FindBy(className = "timer")
	public WebElement timer;

	@FindBy(xpath = "//header//img")
	public WebElement carwashLogo;

//	@FindBy(id = "cardNum")
//	public WebElement cardNumber;
//
//	@FindBy(id = "validTill")
//	public WebElement cardExpiryDate;
//
//	@FindBy(id = "cvv")
//	public WebElement cardCvv;

	@FindBy(xpath = "//a[@href='/paymentprocessing']")
	public WebElement authorize;

	@FindBy(xpath = "//div[class='paymentSelectHeading mt-5 mb-3']")
	public WebElement gatwayHederText;

	@FindBy(xpath = "//div[@class='timer']//span[1]")
	public WebElement timerInMin;

	@FindBy(xpath = "//div[@class='timer']//span[3]")
	public WebElement timerInsec;

	@FindBy(xpath = "//button[text()='Done']")
	public WebElement doneBtn;

	@FindBy(xpath = "//h1[text()='Thank you!']")
	public WebElement thankYoumessage;

	@FindBy(xpath = "//li[1]")
	public WebElement orderId;

	@FindBy(xpath = "//li[2]")
	public WebElement billAmount;

	@FindBy(xpath = "//input[@name='contact']")
	public WebElement mobileNoTextBoxt;

	@FindBy(xpath = "//input[@name='emailId']")
	public WebElement emailIdTextBoxt;

	@FindBy(xpath = "//button[@class='receipt_btn']")
	public WebElement getReceiptBtn;

	/******** methods *********/

	/**
	 * User will select Amount from the quick payment options and click on pay btn
	 * 
	 * @param expectedAmount
	 * @return
	 */
	public boolean PayUsingQuickpaymentOption(String expectedAmount) {
		// test = extent.createTest("Pay Using manual payment option");
		try {
			for (WebElement amountOption : amountOptions) {
				String a = amountOption.getText();
				if (a.contains(expectedAmount)) {
					amountOption.click();
					break;
				}
			}
			if (payBtn.getText().contains(expectedAmount)) {
				ClickOnPayBtn();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * User will select Amount manually using increment buttons and click on pay
	 * btn.
	 *
	 * @param expectedAmount
	 * @return
	 */
	public boolean payUsingManualpayment(String expectedAmount) {// 4.25
		// test = extent.createTest("Pay Using manual payment option");

		try {
			if ((manualAmount.getText().contains(expectedAmount))) {
				ClickOnPayBtn();
			}
			while (!(manualAmount.getText().contains(expectedAmount))) {
				incrementBtn.click();
				if (payBtn.getText().contains(expectedAmount)) {
					ClickOnPayBtn();
					break;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	
	/**
	 * This method click on pay Button
	 */
	public void ClickOnPayBtn() {

		util.waitForElementToBeClickable(payBtn);
		try {
			payBtn.click();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifies the launch of the application and returns a boolean value
	 * 
	 * @return
	 * @throws IOException
	 */
	public boolean verifyHompageUrl() throws IOException {
		return driver.getTitle().equals(prop.getProperty("HomepagePageTitle"));
	}
	/**
	 * This method click on Authorize Button present on payment gateway page
	 */
	public void selectAuthorizeBtn() {
		util.waitForElementToBeClickable(authorize);
		authorize.click();
	}

	/**
	 * This method will return the current count down time from the displayed timer
	 * 
	 * @return
	 */
	public String getcountdownTime() {
		String countdownTime = timerInMin.getText() + ":" + timerInsec.getText();
		return countdownTime;
	}
	
	

	/**
	 * this method will wait till timer time becomes "04:55" and then will click on
	 * Done Button. * @return
	 */
	public boolean selectDoneAfterSomeTime() {
		try {

			while (!(getcountdownTime().equals("04:55"))) {
			}
			doneBtn.click();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * this method will wait till timer count down is over. and then will click on
	 * Done Button. * @return
	 */

	public boolean selectDoneAfterCountdown() {
		try {

			while (!(getcountdownTime().equals("00:00"))) {
				Thread.sleep(5000);
			}
			if (getcountdownTime().equals("00:00")) {
				doneBtn.click();

			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
/**
 * Thos method verifies the launch of Car wash application
 */
	public void VerifylaunchUrl() {
		boolean a;
		driver.get(prop.getProperty("carWashUrl"));
		util.waitForUrlToLoad(prop.getProperty("carWashUrl"));
		if (driver.getTitle().equals(prop.getProperty("CarwashHomepagePageTitle"))) {
			a = true;
		} else {
			a = false;
		}
		test = extent.createTest("Verify Car wash payment cycle");
		Assert.assertTrue(a);
		test.log(Status.INFO, "Application launched sucessfully");
		
		
	}
/**
 * This method verifies the Selection of given amount using manual selection method
 * @param amount
 */
	public void VerifySelectAmount(String amount) {
		util.waitForVisibilityOfElement(manualAmount);
		Assert.assertTrue(payUsingManualpayment(amount));
		test.log(Status.INFO, "Amount selected");
		util.waitForVisibilityOfElement(authorize);
		Assert.assertEquals(driver.getTitle(), prop.getProperty("PaymentPageTitle"));
		test.log(Status.INFO, "navigate to PaymentGateWay");
	}

	/**
	 * This method Verifies the Payment gateway page Functionality
	 */
	public void verifyEntercardDetails() {
		PaymentGateway pay = new PaymentGateway(driver);
		pay.enterCardNum(prop.getProperty("CardNo"));
		pay.enterCardExpiryDate(prop.getProperty("CardExpiry"));
		pay.enterCardCvv(prop.getProperty("CardCvv"));
		selectAuthorizeBtn();
	}

	/**
	 * This method verifies the navigation to the count down page.
	 * 
	 */
	public void verifyNavigationToCountdownPage() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOf(carwashLogo));
			wait.until(ExpectedConditions.visibilityOf(timer));
			if(carwashLogo.isDisplayed()) {
			test.log(Status.PASS, "Successfuly navigate to payment success page");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to navigat to payment success page");
			e.printStackTrace();
		}

	}

	/**
	 * This method Verifies if successful payment message is displayed.
	 */
	public void verifySuccessfulPayment() {
		try {
			if (timer.isDisplayed()) {
				test.log(Status.PASS, "Payment done Successfuly and Timer countdown started");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Payment Unsuccessful");
			e.printStackTrace();
			
		}
	}

	/**
	 * This method Verifies the bill is generated with same amount as the Amount selected before payment.
	 */
	public void verifyAmount() {
		// carwash.selectDoneAfterCountdown();
		if(selectDoneAfterSomeTime()){
		test.log(Status.PASS, "Click on Done button after 5 seconds");
		}else {
			test.log(Status.FAIL, "Failed to Click on Done button after 5 seconds");
		}
			
		util.waitForElementWithFrequency(billAmount, 20, 2);
		//System.out.println(getAmmount(billAmount.getText()));
		if(getAmmount(billAmount.getText()).equals(prop.getProperty("Amount"))) {
			test.log(Status.PASS, "Ammount selected before payment and Bill amount is same");
		}
		else {
			test.log(Status.FAIL, "Ammount selected before payment and Bill amount is different");
		}
		Assert.assertEquals(getAmmount(billAmount.getText()),prop.getProperty("Amount"));
	}
	

	
/**
 * This method will return a string value by removing text before "$" sign from the given string
 * @param s
 * @return
 */
	public static String getAmmount(String s) {
		int dollarIndex = s.indexOf('$');
		if (dollarIndex != -1) {
			String AmountString = s.substring(dollarIndex + 1).trim();
			// System.out.println(carString);

//		  String[] parts = s.split("\\$");
//	        
//	        if (parts.length == 2) {
//	            String text = parts[0].trim(); // "Amount"
//	            String number = parts[1].trim();

			return AmountString;
		}
		return s;

	}
}
