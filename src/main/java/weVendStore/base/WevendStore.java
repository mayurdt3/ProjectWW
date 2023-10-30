package weVendStore.base;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BaseClass;
import configuration.ConfigUtil;
import configuration.ExcelFleReader;
import paymentGatway.base.PaymentGateway;
import utility.Utility;

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
public class WevendStore extends BaseClass {


	public WevendStore(WebDriver driver) {
		this.driver = driver;
		prop = new ConfigUtil(driver);
		util = new Utility(driver);
		excel = new ExcelFleReader();
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

	private List<WebElement> buyNowButtons;

	@FindBy(xpath = "//span[text()='Add to Cart']")
	private List<WebElement> addToCartButtons;

	@FindBy(xpath = "//div[contains(text(),'You added ')]")
	private WebElement addToCartSuccessmsg;
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
				util.waitForElementToBeClickable(itemName);
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
				util.waitForElementToBeClickable(itemName);
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
		util.waitForVisibilityOfElement(addToCartSuccessmsg);
		return addToCartSuccessmsg.getText();
	}

	/**
	 * Retrieves the count of items in the shopping cart.
	 * 
	 * @return
	 */
	public int getCartItemCount() {
		util.waitForVisibilityOfElement(cartItemsCount);
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
		util.waitForVisibilityOfElement(cartSubtotal);
		return cartSubtotal.getText();
	}

	/**
	 * Clicks on the "Proceed to Checkout" button in the shopping cart and waits for
	 * the checkout page to load.
	 * 
	 */
	public void clickOnProceedToCheckoutBtn() {
		util.waitForVisibilityOfElement(proceedToCheckoutBtn);
		proceedToCheckoutBtn.click();
		util.waitForTitle(excel.getExcelvalueForKey("CheckoutPageTitle"));
	}

	/*
	 * Review and payments page WebelElements
	 */

	@FindBy(xpath = "//button/span[text()='Proceed']")
	private WebElement proceedButton;

	@FindBy(xpath = "//strong[text()='Order Total']/parent::th/following-sibling::td//span")
	private WebElement orderTotal;

	@FindBy(xpath = "//div[@class='payment-method _active']//div[contains(@class,'payment-method-title')]/span")
	private WebElement paymentMethod;

	@FindBy(xpath = "//span[@data-bind='text: getCartSummaryItemsCount()']")
	private WebElement checkoutItmCount;
	
	
	

	/**
	 * Retrieves the count of items in the shopping cart.
	 * @return
	 */
	public String getCartItemsCount() {
		util.waitForVisibilityOfElement(cartItemsCount);
		return cartItemsCount.getText();
	}

	/**
	 *  Clicks on the "Proceed" button
	 *
	 */
	public void clickOnProceed() {
		util.waitForElementToBeClickable(proceedButton);
		proceedButton.click();
	}
	
	/**
	 * Retrieves the Text displayed for Payment method(Wevend Payment Method)
	 * @return
	 */
	public String getWevendPaymentMethodText() {
		util.waitForVisibilityOfElement(paymentMethod);
		if (paymentMethod.isDisplayed()) {
			return paymentMethod.getText();
		} else
			return "Not Available";
	}


	/**
	 * Retrieves the order Total amount from the Checkout page.
	 * @return
	 */
	public String getCheckoutOrderTotal() {
		return orderTotal.getText();
	}

	/*
	 * PaymentGatway page WebelElements
	 */

	@FindBy(xpath = "//button[contains(text(),'Pay')]")
	private WebElement payButn;

	@FindBy(xpath = "//b[text()='Card Pay']")
	private WebElement cardPayOption;


	
	


	/**
	 * Retrieves the payment option for "Card Payment" as text.
	 *
	 * @return 
	 */
	public String getCardPayAsPaymentOption() {
		util.waitForVisibilityOfElement(cardPayOption);
		return cardPayOption.getText();

	}

	/**
	 * Clicks on the "Pay" button to initiate a payment 
	 */
	public void clickonPayBtn() {
		util.waitForElementToBeClickable(payButn);
		payButn.click();
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
		util.waitForVisibilityOfElement(ThankYouText);
		if (ThankYouText.isDisplayed()) {
			return true;
		} else
			return false;
	}

	/**
	 *  Checks if the order ID is displayed on the web page
	 * @return
	 */
	public boolean orderIdIsDisplayed() {
		util.waitForVisibilityOfElement(orderId);
		if (orderId.isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	/**
	 * Retrives Order ID from the Success page.
	 * @return
	 */
	public String getOrderId() {
		return orderId.getText();
	}

	/**
	 * Retrieves the grand total amount from the Success page.
	 * @return
	 */
	public String getGrandTotal() {
		return grandTotal.getText();
	}

	/**
	 * click on Buy More ButtonS
	 */
	public void clickOnBuyMoreBtn() {
		util.waitForElementToBeClickable(buyMoreBtn);
		buyMoreBtn.click();
		util.waitForTitle(excel.getExcelvalueForKey("WevendHompageTitle"));
	}
	
	
	

	/* tests scripts */
//
//	/**
//	 * 
//	 * This method will launch and verify the WeVend Store url
//	 * 
//	 */
//
//	public void launchWevendStore() {
//
//		test = extent.createTest("Verify Wevend payment cycle");
//		// test.log(Status.INFO, "Launching the App");
//		driver.get(prop.getProperty("WevendStoreUrl"));
//
//		util.waitForUrlToLoad(driver, prop.getProperty("WevendStoreUrl"));
//
//		if (driver.getTitle().equals(prop.getProperty("WevendHompageTitle"))) {
//			test.log(Status.INFO, "Application launched sucessfully ");
//		} else {
//			test.log(Status.FAIL, "Application not launched sucessfully ");
//		}
//		assertEquals(driver.getCurrentUrl(), prop.getProperty("WevendStoreUrl"));
//	}
//
//	/**
//	 * 
//	 * This method will launch and verify the WeVend Store url
//	 * 
//	 */
//
//	public void AddProductstoCart(String product1, String product2, String product3) {
//		if (driver.getCurrentUrl().equals(prop.getProperty("WevendStoreUrl"))) {
//			test.log(Status.INFO, "Application launched sucessfully ");
//		} else {
//			test.log(Status.FAIL, "Application not launched sucessfully ");
//		}
//		List<String> products = Arrays.asList(product1, product2, product3);
//		for (String product : products) {
//			try {
//				clickOnAddToCart(product);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			boolean result = getAddedToCartSuccessmsg().contains(product);
//			if (result) {
//				test.log(Status.PASS, product + " is successfully added to the cart");
//			} else {
//				test.log(Status.FAIL, product + " is not added to the cart");
//			}
//			Assert.assertTrue(result);
//		}
//		NumOfProducsAddedtoCart = products.size();
//	}
//
	/**
	 * Verifies the Products are added to the cart
	 */
//	public void VerifyProductInCart() {
//		cartCount = getCartItemCount();
//		if (cartCount == NumOfProducsAddedtoCart) {
//			test.log(Status.INFO, "Total Number of items displayed on the cart is : " + cartCount);
//		} else {
//			test.log(Status.FAIL,
//					"Total Number of items displayed on cart is not matching with items added to the cart: "
//							+ cartCount);
//		}
//		Assert.assertEquals(cartCount, NumOfProducsAddedtoCart);
//
//	}
//
	/**
	 * verifies the navigation to Checkout page
	 */
//	public void NavigateToCheckoutPage() {
//		clickOnCartIcon();
//		util.waitForVisibilityOfElement(cartSubtotal);
//		cartSubtotaltxt = getCartSubtotal();
//		util.waitForVisibilityOfElement(proceedToCheckoutBtn);
//		proceedToCheckoutBtn.click();
//		util.waitForTitle(prop.getProperty("CheckoutPageTitle"));
//
//		if (driver.getTitle().equals(prop.getProperty("CheckoutPageTitle"))) {
//			test.log(Status.PASS, "successfully navigated to checkout page");
//		} else {
//			test.log(Status.FAIL, "Failed to navigated to checkout page");
//		}
//		Assert.assertEquals(driver.getTitle(), prop.getProperty("CheckoutPageTitle"));
//	}
//
	/**
	 * Verify Order Total with Cart sub-total.
	 */
//	public void checkOrderTotal() {
//
//		getWevendPaymentMethodText();
//		orderTotaltxt = getCheckoutOrderTotal();
//		if (cartSubtotaltxt.equals(orderTotaltxt)) {
//			proceedButton.click();
//			test.log(Status.PASS, "Cart sub-total is matching with Order Total");
//		} else {
//			test.log(Status.FAIL, "Cart sub-total is not matching with Order Total");
//		}
//		Assert.assertEquals(cartSubtotaltxt, orderTotaltxt);
//		Assert.assertEquals(getCartItemsCount(), checkoutItmCount.getText());
//		test.log(Status.PASS, "Cart item-count is matching with Order items");
//		Assert.assertEquals(getWevendPaymentMethodText(), "weVend Gateway");
//		test.log(Status.PASS, "Payment method name is displayed");
//	}
//
	/**
	 * verifies the navigation to Payment Gateway page
	 */
//	public void navigateToPaymentgatway() {
//		try {
//			clickOnProceed();
//		} catch (Exception e) {
//			
//		}
//		util.waitForVisibilityOfElement(payButn);
//		Assert.assertEquals(driver.getTitle(), prop.getProperty("PayemtGatwayPage"));
//	}

	/**
	 * verifies the Enter card detail functionality.
	 */
//	public void verifyEntercardDetails() {
//		PaymentGateway pay = new PaymentGateway(driver);
//		pay.enterCardNum(prop.getProperty("CardNo"));
//		pay.enterCardExpiryDate(prop.getProperty("CardExpiry"));
//		pay.enterCardCvv(prop.getProperty("CardCvv"));
//		clickonPayBtn();
//	}

	/**
	 * verifies the navigation to Success page
	 */
//	public void navigatToSuccessPage() {
//		boolean verify = verifySuccessfulPayment();
//		if (verify) {
//			test.log(Status.PASS, "Payment is Successfull");
//		} else {
//			test.log(Status.FAIL, "payment is unsuccessfull");
//		}
//		Assert.assertTrue(verifySuccessfulPayment());
//
//	}
//
	
	/**
	 * verifies the Order ID is Displayed 
	 */
//	public void verifyOrderId() {
//		if (orderIdIsDisplayed()) {
//			test.log(Status.PASS, "order id " + getOrderId() + "is displayed");
//		}
//		Assert.assertTrue(orderIdIsDisplayed());
//	}
//
	
	/**
	 * Verifies Grand Total with Order total.
	 */
//	public void verifyGrandTotalWithOrderTotal() {
//		try {
//			if (getGrandTotal().equals(orderTotaltxt)) {
//				test.log(Status.PASS, "Grand Total displayed is same as Order Total");
//			}
//		} catch (Exception e) {	
//			e.printStackTrace();
//		}
//
//		Assert.assertEquals(getGrandTotal(), orderTotaltxt);
//	}
//	
	
	/**
	 * Verifies selection of Buy More button Navigated to Home Page.
	 */
//	public void verifyBuyMoreFunctionality() {
//		util.waitForElementToBeClickable(buyMoreBtn);
//		buyMoreBtn.click();
//		try {
//		util.waitForTitle( prop.getProperty("WevendHompageTitle"));
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		test.log(Status.INFO,"selection of Buy More button Navigated to Home page");
//		Assert.assertEquals(driver.getTitle(),prop.getProperty("WevendHompageTitle") );
//		
//		
//	}
//
}
