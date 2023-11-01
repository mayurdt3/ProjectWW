package com.Remosys.WeVend.PageLayer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.Remosys.WeVend.utility.Utility;

/**
 * PaymentGateway class encapsulates the common elements and actions for payment gateway page
 * @author Mayur
 *
 * usage
 * 1.Create an instance of ExcelFileReader to work with Excel files.
 * And access the common methods used 
 * 
 * 
 */
public class PaymentGateway{
	protected WebDriver driver;
	public Utility util;
	
	public PaymentGateway(WebDriver driver) {
	 this.driver= driver;
		util = new Utility();
		PageFactory.initElements(driver, this);
 }
 

	@FindBy(id = "cardNum")
	private WebElement cardNumber;

	@FindBy(id = "validTill")
	private WebElement cardExpiryDate;

	@FindBy(id = "cvv")
	private WebElement cardCvv;

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
 
 
 
 
 
}
