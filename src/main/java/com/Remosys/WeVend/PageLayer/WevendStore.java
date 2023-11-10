package com.Remosys.WeVend.PageLayer;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.Remosys.WeVend.Reader.ConfigUtil;
import com.Remosys.WeVend.Reader.ExcelFileReader;
import com.Remosys.WeVend.utility.Utility;


/**
 * This class encapsulates the elements, actions, and functionality related to a
 * 'WeVend Store' web application.
 * 
 * @author Mayur Takalikar.
 *
 *         Usage: 1. Create an instance of this class to interact with the web
 *         page. 2. Implement actions and verifications relevant to the
 *         applications functionality.
 * 
 */
public class WevendStore {
	protected WebDriver driver;
	public ExcelFileReader excel;
	public Utility util;
	public ConfigUtil prop;
	public PaymentGateway pay;
	
	
	public WevendStore(WebDriver driver) {
		this.driver = driver;
		prop = new ConfigUtil();
		util = new Utility();
		excel = new ExcelFileReader();
		pay = new PaymentGateway(driver);

		PageFactory.initElements(driver, this);
	}

	/**
	 * 
	 * Home page WebelElements
	 * 
	 **/

	@FindBy(xpath = "//a[@class='logo']")
	private WebElement avsLogo;

	// "//div[@class='actions-primary']/form[@data-role='tocart-form']")
	@FindBy(xpath = "//div[@class='actions-primary']/form")
	private List<WebElement> AddtoCartButtonContainer;

	@FindBy(xpath = "//li[@class ='product-item']")
	private List<WebElement> productList;

	@FindBy(xpath = "//div[@data-block='minicart']/a/descendant::span[@class='counter-number']")
	private WebElement cartItemsCount;
	
	@FindBy(xpath = "//div[@class='actions-primary']/form")
	private List<WebElement> buyNowButtons;

	@FindBy(xpath = "//span[text()='Add to Cart']")
	private List<WebElement> addToCartButtons;

	@FindBy(xpath = "//div[contains(text(),'You added ')]")
	private WebElement addToCartSuccessMsg;
	// div[@role='alert']

	@FindBy(xpath = "//a[@class='action showcart']")
	private WebElement cartIcon;

	@FindBy(xpath = "//div[@class='amount price-container']//span[@class='price']")
	public WebElement cartSubtotal;

	@FindBy(xpath = "//button[@id='top-cart-btn-checkout']")
	private WebElement proceedToCheckoutBtn;

	/**
	 * Clicks on the "Add to Cart" button for a specific product.
	 *
	 * @param product
	 */
	public void clickOnAddToCart(String product) {
		// Iterate through the "Add to Cart" button container.
		for (int i = 0; i < AddtoCartButtonContainer.size(); i++) {
			WebElement productName = AddtoCartButtonContainer.get(i);
			String productNameText = productName.getAttribute("data-product-sku");
			if (product.equalsIgnoreCase(productNameText)) {
				WebElement itemName = addToCartButtons.get(i);
				util.waitForElementToBeClickable(driver, itemName);
				itemName.click();
			}
		}
	}

	/**
	 * Clicks on the "Buy Now" button for a specific product.
	 * 
	 * @param itemneeded
	 */

	public void clickOnBuyNow(String itemneeded) {
		for (int i = 0; i < AddtoCartButtonContainer.size(); i++) {
			WebElement productName = AddtoCartButtonContainer.get(i);
			String productNameText = productName.getAttribute("data-product-sku");
			if (itemneeded.equalsIgnoreCase(productNameText)) {
				WebElement itemName = buyNowButtons.get(i);
				util.waitForElementToBeClickable(driver, itemName);
				itemName.click();
			}
		}
	}

	/**
	 * Retrieves the success message displayed when an item is added to the cart.
	 * 
	 * @return
	 */
	public String getAddedToCartSuccessmsg() {
		util.waitForVisibilityOfElement(driver, addToCartSuccessMsg);
		return addToCartSuccessMsg.getText();
	}

	/**
	 * Retrieves the count of items in the shopping cart.
	 * 
	 * @return
	 */
	public int getCartItemCount() {
		util.waitForVisibilityOfElement(driver, cartItemsCount);
		int itemCount;
		String cartText = cartItemsCount.getText();
		if (!cartText.isEmpty()) {
			itemCount = Integer.parseInt(cartText);
		} else {
			return 0;
		}
		return itemCount;
	}

	/**
	 * Adds a randomly selected product to the shopping cart from a list of
	 * products.
	 */
	public void AddRandomProductToCart() {
		int productscount = productList.size();
		double randomDouble = Math.random();
		int randomNumber = (int) (randomDouble * productscount) + 1;
		addToCartButtons.get(randomNumber).click();
	}

	/**
	 * Clicks on the cart icon to view the shopping cart.
	 */
	public void clickOnCartIcon() {
		cartIcon.click();
	}

	/**
	 * Retrieves the subtotal amount from the shopping cart.
	 * 
	 * @return
	 */
	public String getCartSubtotal() {
		util.waitForVisibilityOfElement(driver, cartSubtotal);
		return cartSubtotal.getText();
	}

	/**
	 * Clicks on the "Proceed to Checkout" button in the shopping cart and waits for
	 * the checkout page to load.
	 * 
	 */
	public void clickOnProceedToCheckoutBtn() {
		util.waitForVisibilityOfElement(driver, proceedToCheckoutBtn);
		proceedToCheckoutBtn.click();
		util.waitForTitle(driver, excel.getExcelvalueForKey(0,"CheckoutPageTitle"));
	}

	/*
	 * Review and payments page WebelElements
	 */

	@FindBy(xpath = "//button[@title='Proceed']")
	private WebElement proceedButton;

	@FindBy(xpath = "//strong[text()='Order Total']/parent::th/following-sibling::td//span")
	private WebElement orderTotal;

	@FindBy(xpath = "//div[@class='payment-method _active']//div[contains(@class,'payment-method-title')]/span")
	private WebElement paymentMethod;

	@FindBy(xpath = "//span[@data-bind='text: getCartSummaryItemsCount()']")
	private WebElement checkoutItmCount;

	/**
	 * Retrieves the count of items in the shopping cart.
	 * 
	 * @return
	 */
	public String getCartItemsCount() {
		util.waitForVisibilityOfElement(driver, cartItemsCount);
		return cartItemsCount.getText();
	}

	/**
	 * Clicks on the "Proceed" button
	 *
	 */
	public void clickOnProceed() {
		util.waitForElementToBeClickable(driver, proceedButton);
		proceedButton.click();
	}

	/**
	 * Retrieves the Text displayed for Payment method(Wevend Payment Method)
	 * 
	 * @return
	 */
	public String getWevendPaymentMethodText() {
		util.waitForVisibilityOfElement(driver, paymentMethod);
		if (paymentMethod.isDisplayed()) {
			return paymentMethod.getText();
		} else
			return "Not Available";
	}

	/**
	 * Retrieves the order Total amount from the Checkout page.
	 * 
	 * @return
	 */
	public String getCheckoutOrderTotal() {
		return orderTotal.getText();
	}

	/*
	 * PaymentGatway page WebelElements
	 */



	@FindBy(xpath = "//b[text()='Card Pay']")
	private WebElement cardPayOption;

	/**
	 * Retrieves the payment option for "Card Payment" as text.
	 *
	 * @return
	 */
	public String getCardPayAsPaymentOption() {
		util.waitForVisibilityOfElement(driver, cardPayOption);
		return cardPayOption.getText();

	}

	

	/*
	 * Order Sucsess page WebelElements
	 */

	@FindBy(xpath = "//span[contains(text(),'Thank you')]")
	private WebElement ThankYouText;

	@FindBy(xpath = "//div[@class='checkout-success']/p/span")
	private WebElement orderId;

	@FindBy(xpath = "//td[text()='Grand Total']/following-sibling::td")
	private WebElement grandTotal;

	@FindBy(xpath = "//table")
	private WebElement orderDetailTable;

	@FindBy(xpath = "//div[text()='Buy More']")
	private WebElement buyMoreBtn;

	/**
	 * Verifies if a successful payment confirmation message is displayed.
	 * 
	 * @return
	 */
	public boolean verifySuccessfulPayment() {
		util.waitForVisibilityOfElement(driver, ThankYouText);
		if (ThankYouText.isDisplayed()) {
			return true;
		} else
			return false;
	}

	/**
	 * Checks if the order ID is displayed on the web page
	 * 
	 * @return
	 */
	public boolean isOrderIdDisplayed() {
		util.waitForVisibilityOfElement(driver, orderId);
		if (orderId.isDisplayed()) {
			return true;
		} else
			return false;
	}

	/**
	 * Retrives Order ID from the Success page.
	 * 
	 * @return
	 */
	public String getOrderId() {
		return orderId.getText();
	}

	/**
	 * Retrieves the grand total amount from the Success page.
	 * 
	 * @return
	 */
	public String getGrandTotal() {
		return grandTotal.getText();
	}

	/**
	 * click on Buy More ButtonS
	 */
	public void clickOnBuyMoreBtn() {
		util.waitForElementToBeClickable(driver, buyMoreBtn);
		buyMoreBtn.click();
		util.waitForTitle(driver, excel.getExcelvalueForKey(0,"WevendHompageTitle"));
	}

}