package carwash.base;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BaseClass;
import configuration.ConfigFileReader;
import utility.Utility;

public class CarWash extends BaseClass {
	
	public CarWash(WebDriver driver) {
		this.driver = driver;
		prop = new ConfigFileReader(driver);
		util = new Utility(driver);

		PageFactory.initElements(driver, this);
	}
	

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

	@FindBy(id = "cardNum")
	public WebElement cardNumber;

	@FindBy(id = "validTill")
	public WebElement cardExpiryDate;

	@FindBy(id = "cvv")
	public WebElement cardCvv;

	@FindBy(xpath = "//a[@href='/paymentprocessing']")
	public WebElement authorize;

	@FindBy(xpath = "//div[class='paymentSelectHeading mt-5 mb-3']")
	public WebElement gatwayHederText;

	@FindBy(xpath = "//div[@class='timer']//span[1]")
	public WebElement timerInMin;

	@FindBy(xpath = "//div[@class='timer']//span[3]")
	public WebElement timerInsec;
	
	@FindBy(xpath = "//button[text()='Done']")
	public	WebElement doneBtn;

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
	

	public boolean PayUsingQuickpaymentOption(String expectedAmount) {
		//test = extent.createTest("Pay Using manual payment option");
		try {
			for (WebElement amountOption : amountOptions) {
				String a = amountOption.getText();
				System.out.println(a);

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
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;

	}

	public boolean payUsingManualpayment(String expectedAmount) {// 4.25
		//test = extent.createTest("Pay Using manual payment option");

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
				// TODO: handle exception
				e.printStackTrace();
			}
		
		return false;
	}

	public void ClickOnPayBtn() {

		util.waitForElementToBeClickable(payBtn);
		try {
			payBtn.click();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public boolean verifyHompageUrl() throws IOException {
		return driver.getTitle().equals(prop.getProperty("HomepagePageTitle"));
	}
/**
 * 
 * @param cardNum
 */
	public void enterCardNum(String cardNum) {
		util.waitForVisibilityOfElement(cardNumber);
		cardNumber.sendKeys(cardNum);
	}

	public void enterCardExpiryDate(String string) {
		util.waitForVisibilityOfElement(cardExpiryDate);
		cardExpiryDate.sendKeys(string);
	}

	public void enterCardCvv(String string) {
		util.waitForVisibilityOfElement(cardCvv);
		cardCvv.sendKeys(string);
	}
	public void selectAuthorizeBtn() {
		util.waitForElementToBeClickable(authorize);
		authorize.click();
	}

	public String getcountdownTime() {
		String countdownTime =  timerInMin.getText()+":"+timerInsec.getText();
		return countdownTime;
	}
	public boolean selectDoneAfterSomeTime() {
		try {
			
			while (!(getcountdownTime().equals("04:55")) ){
				
			}
			
				doneBtn.click();
				
			return true;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}
	}
	public boolean selectDoneAfterCountdown() {
		try {
			
		while (!(getcountdownTime().equals("00:00")) ){
		Thread.sleep(5000);	
		}
		if (getcountdownTime().equals("00:00")){
			doneBtn.click();
			
		}
		return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}

	/* tests */

}
