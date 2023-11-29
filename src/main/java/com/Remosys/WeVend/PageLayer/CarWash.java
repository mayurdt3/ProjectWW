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
 * @author Remosys Testing Team.
 *
 *         Usage: 1. Create an instance of this class to interact with the web
 *         page. 2. Implement actions and verifications relevant to the
 *         applications functionality.
 * 
 */

public class CarWash {
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

	@FindBy(id = "//div[text()='Select Payment Method']")
	protected WebElement paymentMethodText;

	@FindBy(xpath = "//a[@href='/paymentprocessing']")
	protected WebElement authorize;

	@FindBy(xpath = "//div[class='paymentSelectHeading mt-5 mb-3']")
	protected WebElement gatewayHederText;

	@FindBy(xpath = "//div[@class='timer']//span[1]")
	protected WebElement timerInMin;

	@FindBy(xpath = "//div[@class='timer']//span[3]")
	protected WebElement timerInSec;

	@FindBy(xpath = "//b[text()='Message from the Device:']//following-sibling::br")
	protected WebElement messageFromDevice;

	@FindBy(xpath = "//button[text()='Done']")
	protected WebElement doneBtn;

	@FindBy(xpath = "//h1[text()='Thank you!']")
	protected WebElement thankYouMessage;

	@FindBy(xpath = "//li[1]")
	protected WebElement orderId;

	@FindBy(xpath = "//li[2]")
	protected WebElement billAmount;

	@FindBy(xpath = "//input[@name='contact']")
	protected WebElement mobileNoTextBox;

	@FindBy(xpath = "//input[@name='emailId']")
	protected WebElement emailIdTextBox;

	@FindBy(xpath = "//button[@class='receipt_btn']")
	protected WebElement getReceiptBtn;

	/******** methods *********/

	/**
	 * This method will select Amount from the quick payment options and click on
	 * pay button
	 * 
	 * @param expectedAmount
	 * @return
	 */
	public boolean payUsingQuickPaymentOption(String expectedAmount) {

		try {
			for (WebElement amountOption : amountOptions) {
				String a = amountOption.getText();
				if (a.contains(expectedAmount)) {
					amountOption.click();
					break;
				}
			}
			if (payBtn.getText().contains(expectedAmount)) {
				clickOnPayBtn();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This Method will select Amount manually using increment buttons and click on
	 * pay button
	 *
	 * @param expectedAmount
	 * @return
	 */
	public boolean payUsingManualPayment(String expectedAmount) {

		try {
			if ((manualAmount.getText().contains(expectedAmount))) {
				clickOnPayBtn();
			}
			while (!(manualAmount.getText().contains(expectedAmount))) {
				incrementBtn.click();
				if (payBtn.getText().contains(expectedAmount)) {
					clickOnPayBtn();

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
	public void clickOnPayBtn() {

		util.waitForElementToBeClickable(driver, payBtn);
		try {
			payBtn.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method verifies the launch of the application and returns a boolean
	 * value
	 * 
	 * @return
	 * @throws IOException
	 */
	public boolean verifyHompageUrl() throws IOException {

		util.waitForUrlToLoad(driver, excel.getExcelvalueForKey(0, "carWashUrl"));
		return driver.getCurrentUrl().equals(excel.getExcelvalueForKey(0, "carWashUrl"));
	}

	/**
	 * check successful navigation to payment gateway page
	 * 
	 * @return
	 */
	public boolean verifyNavigateToPaymentGateway() {
		util.waitForVisibilityOfElement(driver, authorize);
		return authorize.isDisplayed();
	}

	/**
	 * check authorize button is enabled
	 * 
	 * @return
	 */
	public boolean isAuthorizeBtnEnabled() {
		util.waitForElementToBeClickable(driver, authorize);
		return authorize.isEnabled();
	}

	/**
	 * This method click on Authorize Button present on payment gateway page
	 */
	public void selectAuthorizeBtn() {

		util.waitForElementToBeClickable(driver, authorize);
		// authorize.click(); /// For Preventing it from doing payment
		util.waitForVisibilityOfElement(driver, timer);
	}

	/**
	 * This method will return the current count down time from the displayed timer
	 * 
	 * @return
	 */
	public String getCountdownTime() {

		String countdownTime = timerInMin.getText() + ":" + timerInSec.getText();
		return countdownTime;
	}

	/**
	 * this method will wait till timer time becomes "04:55" and then will click on
	 * Done Button. * @return
	 */
	public boolean selectDoneAfterSomeTime() {

		try {
			while (!(getCountdownTime().equals("04:55"))) {
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
			while (!(getCountdownTime().equals("00:00"))) {
				Thread.sleep(5000);
			}
			if (getCountdownTime().equals("00:00")) {
				doneBtn.click();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * check if Logo is displayed
	 * 
	 * @return boolean value if Car wash logo is displayed.
	 */

	public boolean isLogoDisplayed() {
		return carwashLogo.isDisplayed();
	}

	/**
	 * checks if Bill Amount is displayed
	 * 
	 * @return boolean value if bill amount is displayed.
	 */
	public boolean isBillAmountDisplayed() {
		return billAmount.isDisplayed();
	}

	/**
	 * checks if Order ID is Displayed
	 * 
	 * @return boolean value if order id is displayed.
	 */
	public boolean isOrderIdDisplayed() {
		return orderId.isDisplayed();
	}

	/**
	 * Retrieves the Order ID
	 * 
	 * @return orderId
	 */
	public String getOrderId() {
		return orderId.getText();
	}

	/**
	 * This method will return a string value by removing text before "$" sign from
	 * the Bill Amount
	 * 
	 * @return AmountString
	 */
	public String getBillAmount() {
		String s = billAmount.getText();
		int dollarIndex = s.indexOf('$');
		if (dollarIndex != -1) {
			String AmountString = s.substring(dollarIndex + 1).trim();

			return AmountString;
		}
		return s;
	}

	/**
	 * Check if Thank You message is Displayed
	 * 
	 * @return thankYoumessage
	 */
	public boolean isThankYouMessageDisplayed() {
		return thankYouMessage.isDisplayed();
	}

	/**
	 * For future implementation
	 * 
	 * @return messageFromeDevice
	 */
	public String getMessageFormDevice() {
		return messageFromDevice.getText();
	}

	/**
	 * For future implementation
	 * 
	 * @return messageFromeDevice
	 */
	public String getDeviceMessageValeForKey(String datakey) {
		String message = getMessageFormDevice();
		message = message.replace("{", "").replace("}", "");

		String[] jsonData = message.split("\",\"");

		for (String keyValue : jsonData) {
			if (keyValue.contains(datakey)) {
				String[] data = keyValue.split("\":\"");

				System.out.println("Value: " + data[1]);
				return data[1];
			}
		}
		return null;
	}

	/**
	 * returns the title of the page
	 * 
	 * @return string title
	 */
	public String getTitle() {
		return driver.getTitle();
	}

}
