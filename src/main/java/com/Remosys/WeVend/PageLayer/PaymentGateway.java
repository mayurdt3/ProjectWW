package com.Remosys.WeVend.PageLayer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.Remosys.WeVend.Reader.ExcelFileReader;
import com.Remosys.WeVend.utility.Utility;

/**
 * PaymentGateway class encapsulates the common elements and actions for payment
 * gateway page
 * 
 * @author Remosys Testing Team,
 *
 *         usage 1.Create an instance of ExcelFileReader to work with Excel
 *         files. And access the common methods used
 * 
 * 
 */
public class PaymentGateway {
	protected WebDriver driver;
	public Utility util;
	public ExcelFileReader excel;

	public PaymentGateway(WebDriver driver) {
		this.driver = driver;
		util = new Utility();
		excel = new ExcelFileReader();
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "cardNum")
	private WebElement cardNumber;

	@FindBy(id = "validTill")
	private WebElement cardExpiryDate;

	@FindBy(id = "cvv")
	private WebElement cardCvv;

	@FindBy(xpath = "//div[@class='paymentMethodHolder']//div[contains(text(),'Method')]")
	private WebElement paymentMethod;

	@FindBy(xpath = "//button[contains(text(),'Pay')]")
	private WebElement payBtn;

	@FindBy(xpath = "//a[@href='/paymentprocessing']")
	protected WebElement authorizePayBtn;

	/**
	 * This method used to Enters the card number in Card number text field
	 * 
	 * @param cardNum
	 */
	public void enterCardNum(String cardNum) {
		util.waitForVisibilityOfElement(driver, cardNumber);
		cardNumber.sendKeys(cardNum);
	}

	/**
	 * This method used to Enters the card ExpiryDate in cardExpiryDate text field
	 * 
	 * @param cardNum
	 */
	public void enterCardExpiryDate(String string) {
		util.waitForVisibilityOfElement(driver, cardExpiryDate);
		cardExpiryDate.sendKeys(string);
	}

	/**
	 * This method used to Enters the card CVV number in cvv text field
	 * 
	 * @param string
	 */
	public void enterCardCvv(String string) {
		util.waitForVisibilityOfElement(driver, cardCvv);
		cardCvv.sendKeys(string);
	}

	public String getWevendPaymentMethodText() {
		util.waitForVisibilityOfElement(driver, paymentMethod);
		if (paymentMethod.isDisplayed()) {
			return paymentMethod.getText();
		} else
			return "Not Available";
	}

	public String getPaymentPageTitle() {
		util.waitForTitle(driver, excel.getExcelvalueForKey(0, "PaymentPageTitle"));
		return driver.getTitle();
	}

	/**
	 * Clicks on the "Pay" button to initiate a payment
	 */
	public void clickOnPayBtn() {
		util.waitForElementToBeClickable(driver, payBtn);
		// payBtn.click(); /// For Preventing it from doing payment
	}

	public boolean isPayBtnEnabled() {
		util.waitForElementToBeClickable(driver, payBtn);
		return payBtn.isEnabled();

	}

}
