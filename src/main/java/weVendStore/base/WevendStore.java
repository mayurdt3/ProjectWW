package weVendStore.base;


import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BaseClass;
import configuration.ConfigFileReader;
import utility.Utility;

public class WevendStore extends BaseClass {

	public WevendStore(WebDriver driver) {
		this.driver = driver;
		prop = new ConfigFileReader(driver);
		util = new Utility(driver);

		PageFactory.initElements(driver, this);
	}
	
	/*
	 * Home page WebelElements
	 */

	@FindBy(xpath = "//div[@class='actions-primary']/form[@data-role='tocart-form']")
	private List<WebElement> AddtoCartButtonContainer;
	
	@FindBy(xpath = "//li[@class ='product-item']")
	public List<WebElement> productList;
	

	@FindBy(xpath = "//div[@data-block='minicart']/a/descendant::span[@class='counter-number']")
	private WebElement cartItemsCount;
	//div[@class='actions-primary']/form
	@FindBy(xpath = "//div[@class='actions-primary']/form")
	private WebElement ActionContainer;

	@FindBy(xpath = "//button[@title='Buy Now']")
	private List<WebElement> buyNowButtons;

	@FindBy(xpath = "//span[text()='Add to Cart']")
	private List<WebElement> addToCartButtons;

	@FindBy(xpath = "//div[contains(text(),'You added ')]")
	private WebElement addToCart_Successmsg;

	
	public void AddRandomProductToCart() {
		int productscount = productList.size();

		 double randomDouble = Math.random();
	       
		 int randomNumber = (int) (randomDouble * productscount) + 1;
	      
		addToCartButtons.get(randomNumber).click();
		
	
		    }
//	public static void main(String[] args) {
//		System.out.println(car);
//	}
//	



//public void clickOnAllAddToCartBtn() {
//	for()
//}
//
//	public void clickOnAddToCart(String product) throws InterruptedException {
//		for (int i = 0; i < productNames.size(); i++) {
//			WebElement productName = productNames.get(i);
//			String productNameText = productName.getAttribute("data-product-sku");
//			if (product.equalsIgnoreCase(productNameText)) {
//				WebElement itemName = addToCartButtons.get(i);
//				util.waitForElementToBeClickable(itemName);
//				itemName.click();
//			}
//		}
//	}
	/*
	 * Order Sucsess page WebelElements
	 */

	@FindBy(xpath = "//span[contains(text(),'Thank you')]")
	private WebElement order_successText;

	@FindBy(xpath = "//div[@class='checkout-success']/p/span")
	private WebElement order_id;

	public String orderSuccessTextIsDisplayed() {
		util.waitForVisibilityOfElement(order_successText);
		if (order_successText.isDisplayed()) {
			return order_successText.getText();
		} else
			return null;

	}

	public String orderIdIsDisplayed() {
		util.waitForVisibilityOfElement(order_id);
		if (order_id.isDisplayed()) {
			return order_id.getText();
		} else
			return null;

	}

	/*
	 * PaymentGatway page WebelElements
	 */

	@FindBy(id = "cardNum")
	private WebElement cardNumber;

	@FindBy(id = "validTill")
	private WebElement card_expiryDate;

	@FindBy(id = "cvv")
	private WebElement card_cvv;

	@FindBy(xpath = "//button[contains(text(),'Pay')]")
	private WebElement payButn;

	public void enter_CardNum(String cardNum) {
		util.waitForVisibilityOfElement(cardNumber);
		cardNumber.sendKeys(cardNum);
	}

	public void enter_CardExpiryDate(String string) {
		util.waitForVisibilityOfElement(card_expiryDate);
		card_expiryDate.sendKeys(string);
	}

	public void enter_CardCvv(String string) {
		util.waitForVisibilityOfElement(card_cvv);
		card_cvv.sendKeys(string);
	}

	public void clickOnPayButn() {
		util.waitForElementToBeClickable(payButn);
		payButn.click();
	}

	/*
	 * PaymentGatway page WebelElements
	 */

	@FindBy(xpath = "//b[text()='Card Pay']")
	private WebElement cardPayOption;

	@FindBy(xpath = "//a[@href='/cardpay']//b[text()='Card Pay']")
	private WebElement cardPayButn;

	public void clickOnCardPayButn() {
		util.waitForVisibilityOfElement(cardPayButn);
		cardPayButn.click();
	}

	public String getCardPayAsPaymentOption() {
		util.waitForVisibilityOfElement(cardPayOption);
		return cardPayOption.getText();

	}

	/*
	 * Review and payments page WebelElements
	 */

	@FindBy(xpath = "//button/span[text()='Proceed']")
	private WebElement proceedButton;

	@FindBy(xpath = "//div[@class='payment-method _active']//div[contains(@class,'payment-method-title')]/span")
	private WebElement wevendPayText;

	public void getCartItemsCount() {
		util.waitForVisibilityOfElement(cartItemsCount);
		cartItemsCount.getText();
	}

	public void clickOnProceed() {
		util.waitForElementToBeClickable(proceedButton);
		proceedButton.click();
	}

	public boolean ProceedIsEnabled() {
		util.waitForElementToBeClickable(proceedButton);
		return proceedButton.isEnabled();

	}

	public String wevendPayTextIsDisplayed() {
		util.waitForVisibilityOfElement(wevendPayText);
		if (wevendPayText.isDisplayed()) {
			return wevendPayText.getText();
		} else
			return "Not Available";
	}

}
