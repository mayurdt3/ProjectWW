package com.Remosys.WeVend.PageLayer;

import java.io.IOException;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.Remosys.WeVend.Reader.ExcelFileReader;
import com.Remosys.WeVend.utility.Utility;


/**
 * This class encapsulates the elements, actions, and functionality related to a
 * 'Car Wash' web application.
 * 
 * @author Mayur Takalikar.
 *
 *         Usage: 1. Create an instance of this class to interact with the web
 *         page. 2. Implement actions and verifications relevant to the
 *         applications functionality.
 * 
 */

public class CarWash  {
		protected WebDriver driver;
		public ExcelFileReader excel;
		public Utility util;
		
	/**
	 * this constructor will initialize configFile and Utility class objects so that
	 * they can be used for this class
	 * 
	 * @param driver
	 */
	public CarWash(WebDriver driver) {
		this.driver = driver;
		util = new Utility();
		excel = new ExcelFileReader();
		PageFactory.initElements(driver, this);
	}

	/**
	 * All the Webelements for 'Carwash' application are listed below
	 */

	@FindBy(xpath = "//div[@class='amount_option']")
	protected List<WebElement> amountOptions;

	@FindBy(xpath = "//div[@class='manual_amount']")
	protected WebElement manualAmount;

	@FindBy(xpath = "//button[@class='arrow-btn decrement-btn']")
	protected WebElement decrementBtn;

	@FindBy(xpath = "//button[@class='arrow-btn increment-btn']")
	protected WebElement incrementBtn;

	@FindBy(xpath = "//button[@class='button false blue']")
	protected WebElement payBtn;

	@FindBy(xpath = "//footer//img")
	protected WebElement footerLogo;

	@FindBy(className = "timer")
	protected WebElement timer;

	@FindBy(xpath = "//header//img")
	protected WebElement carwashLogo;

	@FindBy(id = "cardNum")
	protected WebElement cardNumber;
//
//	@FindBy(id = "validTill")
//	public WebElement cardExpiryDate;
//
//	@FindBy(id = "cvv")
//	public WebElement cardCvv;

	@FindBy(id = "//div[text()='Select Payment Method']")
	protected WebElement paymentMethodText;

	@FindBy(xpath = "//a[@href='/paymentprocessing']")
	protected WebElement authorize;

	@FindBy(xpath = "//div[class='paymentSelectHeading mt-5 mb-3']")
	protected WebElement gatwayHederText;

	@FindBy(xpath = "//div[@class='timer']//span[1]")
	protected WebElement timerInMin;

	@FindBy(xpath = "//div[@class='timer']//span[3]")
	protected WebElement timerInsec;

	@FindBy(xpath = "//b[text()='Message from the Device:']//following-sibling::br")
	protected WebElement messageFromeDevice;

	@FindBy(xpath = "//button[text()='Done']")
	protected WebElement doneBtn;

	@FindBy(xpath = "//h1[text()='Thank you!']")
	protected WebElement thankYoumessage;

	@FindBy(xpath = "//li[1]")
	protected WebElement orderId;

	@FindBy(xpath = "//li[2]")
	protected WebElement billAmount;

	@FindBy(xpath = "//input[@name='contact']")
	protected WebElement mobileNoTextBoxt;

	@FindBy(xpath = "//input[@name='emailId']")
	protected WebElement emailIdTextBoxt;

	@FindBy(xpath = "//button[@class='receipt_btn']")
	protected WebElement getReceiptBtn;

	/******** methods *********/

	
	/**
	 * This method will select Amount from the quick payment options and click on pay button
	 * 
	 * @param expectedAmount
	 * @return
	 */
	public boolean PayUsingQuickpaymentOption(String expectedAmount) {
		
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
	 * This Method will select Amount manually using increment buttons and click on pay button
	 *
	 * @param expectedAmount
	 * @return
	 */
	public boolean payUsingManualpayment(String expectedAmount) {// 4.25
		
		try {
			if ((manualAmount.getText().contains(expectedAmount))) {
				ClickOnPayBtn();
			}
			while (!(manualAmount.getText().contains(expectedAmount))) {
				incrementBtn.click();
				if (payBtn.getText().contains(expectedAmount)) {
					ClickOnPayBtn();
					// util.waitForVisibilityOfElement(carwash.cardNumber);
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
	 * This method returns the string containing selected Amount text
	 */
	public String getSelectedAmount() {
		
		util.waitForVisibilityOfElement(driver, manualAmount);
		return manualAmount.getText();
	}

	/**
	 * This method click on pay Button
	 */
	public void ClickOnPayBtn() {

		util.waitForElementToBeClickable(driver, payBtn);
		try {
			payBtn.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method verifies the launch of the application and returns a boolean value
	 * 
	 * @return
	 * @throws IOException
	 */
	public boolean verifyHompageUrl() throws IOException {
		
		util.waitForUrlToLoad(driver, excel.getExcelvalueForKey("carWashUrl"));
		return driver.getCurrentUrl().equals(excel.getExcelvalueForKey("carWashUrl"));
	}
	
	
	/**
	 * check successful navigation to payment gateway page
	 * @return
	 */
	public boolean verifyNavigateToPaymentGateway() {
		util.waitForVisibilityOfElement(driver, authorize);
		return authorize.isDisplayed();
	}
	
	/**
	 * check authorize button is enabled 
	 * @return
	 */
	public boolean isAuthorizebtnEnabled() {
		util.waitForElementToBeClickable(driver, authorize);
		return authorize.isEnabled();
	}

	/**
	 * This method click on Authorize Button present on payment gateway page
	 */
	public void selectAuthorizeBtn() {
		
		util.waitForElementToBeClickable(driver, authorize);
		authorize.click();
		util.waitForVisibilityOfElement(driver, timer);

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
//
//	/**
//	 * This method verifies the launch of Car wash application
//	 */
//	public void VerifylaunchUrl() {
//		
//		boolean a;
//		driver.get(prop.getProperty("carWashUrl"));
//		util.waitForUrlToLoad(driver, prop.getProperty("carWashUrl"));
//		if (driver.getTitle().equals(prop.getProperty("CarwashHomepagePageTitle"))) {
//			a = true;
//		} else {
//			a = false;
//		}
//		test = extent.createTest("Verify 'Car Wash' payment cycle");
//		Assert.assertTrue(a);
//		test.log(Status.INFO, "'Car Wash' application launched sucessfully");
//
//	}
//
	/**
	 * This method verifies the Selection of given amount using manual selection
	 * method
	 * @param amount
	 */
//	public void VerifySelectAmount(String amount) {
//		
//		util.waitForVisibilityOfElement(driver, manualAmount);
//		Assert.assertTrue(payUsingManualpayment(amount));
//		test.log(Status.INFO, "Amount selected");
//		util.waitForVisibilityOfElement(driver, authorize);
//		Assert.assertEquals(driver.getTitle(), prop.getProperty("PaymentPageTitle"));
//		test.log(Status.PASS, "Navigate to 'Payment GateWay' successfuly");
//	}
//
	/**
	 * This method Verifies the Payment gateway page Functionality
	 */
//	public void verifyEntercardDetails() {
//		
//		PaymentGateway pay = new PaymentGateway(driver);
//		pay.enterCardNum(prop.getProperty("CardNo"));
//		pay.enterCardExpiryDate(prop.getProperty("CardExpiry"));
//		pay.enterCardCvv(prop.getProperty("CardCvv"));
//		test.log(Status.INFO, "Card detail entered");
//		selectAuthorizeBtn();
//	}
//
	/**
	 * This method verifies the navigation to the count down page.
	 */
//	public void verifyNavigationToCountdownPage() {
//
//		try {
//			util.waitForVisibilityOfElement(driver, carwashLogo);
//			util.waitForVisibilityOfElement(driver, timer);
//			if (carwashLogo.isDisplayed()) {
//				test.log(Status.PASS, "Navigate to 'Payment Success' page Successfuly");
//			}
//		} catch (Exception e) {
//			test.log(Status.FAIL, "Failed to navigat to 'payment success' page");
//			e.printStackTrace();
//		}
//
//	}

	/**
	 * This method Verifies if successful payment message is displayed.
	 */
//	public void verifySuccessfulPayment() {
//		
//		try {
//			if (timer.isDisplayed()) {
//				test.log(Status.PASS, "Payment successful, and Timer countdown started");
//			}
//		} catch (Exception e) {
//			test.log(Status.FAIL, "Payment Unsuccessful");
//			e.printStackTrace();
//
//		}
//	}
//
	/**
	 * this method will wait till timer time becomes "04:55" and then will click on
	 * Done Button. * @return
	 */
	public boolean selectDoneAfterSomeTime() {
		
		try {
			while (!(getcountdownTime().equals("04:55"))) {
			}
			doneBtn.click();
			util.waitForElementWithFrequency(driver, billAmount, 20, 2);
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
//
//	/**
//	 * This method Verifies the bill is generated with same amount as the Amount
//	 * selected before payment.
//	 */
//	public void verifyAmount() {
//	
//		if (selectDoneAfterSomeTime()) {
//			test.log(Status.PASS, "Click on 'Done' button after '5' seconds");
//		} else {
//			test.log(Status.FAIL, "Failed to Click on Done button after 5 seconds");
//		}
//		if (getBillAmmount().equals(prop.getProperty("Amount"))) {
//			test.log(Status.PASS, "Ammount selected before payment and Bill-amount is same");
//		} else {
//			test.log(Status.FAIL, "Ammount selected before payment and Bill-amount is different");
//		}
//		Assert.assertEquals(getBillAmmount(), prop.getProperty("Amount"));
//	}
	
	/**
	 * check if Logo is displayed
	 * @return
	 */
	public boolean isLogoDispayed() {
		return carwashLogo.isDisplayed();
	}
	
	/**
	 * checks if Bill Amount is displayed
	 * @return
	 */
	public boolean isBillAmmountDisplayed() {
		return billAmount.isDisplayed();
	}
	
	/**
	 * checks if Order ID is Displayed
	 * @return
	 */
	public boolean isOrderIdDisplayed() {
		return orderId.isDisplayed();
	}
	
	/**
	 * Retrieves the Order ID
	 * @return
	 */
	public String getOrderId() {
		return orderId.getText();
	}

	/**
	 * This method will return a string value by removing text before "$" sign from
	 * the Bill Amount
	 * 
	 * @return
	 */
	public String getBillAmmount() {
		String s = billAmount.getText();
		int dollarIndex = s.indexOf('$');
		if (dollarIndex != -1) {
			String AmountString = s.substring(dollarIndex + 1).trim();	
//		  String[] parts = s.split("\\$");        
//	        if (parts.length == 2) {
//	            String text = parts[0].trim(); // "Amount"
//	            String number = parts[1].trim();
			return AmountString;
		}
		return s;
	}
	
	/**
	 * Check if Thank You message is Displayed
	 * @return
	 */
	public boolean isThankYouMessageDisplayed() {
		return thankYoumessage.isDisplayed();
	}

	//not working
	public String getMessageFormDevice() {
		return messageFromeDevice.getText();
	}

	//not working
	public String getDeviceMessageValeforKey(String datakey) {
		String message = getMessageFormDevice();
		message = message.replace("{", "").replace("}", "");

		String[] jsonData = message.split("\",\""); // ","
		// System.out.println(jsonData.length);
		for (String keyValue : jsonData) {
			if (keyValue.contains(datakey)) {
				String[] data = keyValue.split("\":\"");
				// System.out.println("key: " + data[0]);
				System.out.println("Value: " + data[1]);
				return data[1];
			}
		}
		return null;
	}

}
