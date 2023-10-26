package weVendStore.base;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import base.BaseClass;
import configuration.ConfigFileReader;
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

	int NumOfProducsAddedtoCart;
	int cartCount;
	String cartSubtotaltxt;
	String orderTotaltxt;

	public WevendStore(WebDriver driver) {
		this.driver = driver;
		prop = new ConfigFileReader(driver);
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

	// @FindBy(xpath =
	// "//div[@class='actions-primary']/form[@data-role='tocart-form']")
	@FindBy(xpath = "//div[@class='actions-primary']/form")
	private List<WebElement> AddtoCartButtonContainer;

	@FindBy(xpath = "//li[@class ='product-item']")
	public List<WebElement> productList;

	@FindBy(xpath = "//div[@data-block='minicart']/a/descendant::span[@class='counter-number']")
	private WebElement cartItemsCount;

	@FindBy(xpath = "//button[@title='Buy Now']")
	private List<WebElement> buyNowButtons;

	@FindBy(xpath = "//span[text()='Add to Cart']")
	private List<WebElement> addToCartButtons;

	@FindBy(xpath = "//div[contains(text(),'You added ')]")
	private WebElement addToCartSuccessmsg;
	// div[@role='alert']

	@FindBy(xpath = "//a[@class='action showcart']")
	private WebElement cartIcon;

	@FindBy(xpath = "//div[@class='amount price-container']//span[@class='price']")
	private WebElement cartSubtotal;

	@FindBy(xpath = "//button[@id='top-cart-btn-checkout']")
	private WebElement ProceedToCheckoutBtn;
	
	

	public void clickOnAddToCart(String product) {
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

	public String getAddedToCartSuccessmsg() {
		util.waitForVisibilityOfElement(addToCartSuccessmsg);
		return addToCartSuccessmsg.getText();
	}

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

	public void AddRandomProductToCart() {
		int productscount = productList.size();
		double randomDouble = Math.random();
		int randomNumber = (int) (randomDouble * productscount) + 1;
		addToCartButtons.get(randomNumber).click();
	}

	public void clickOnCartIcon() {
		cartIcon.click();
	}

	public String getCartSubtotal() {
		return cartSubtotal.getText();
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

	public String getCartItemsCount() {
		util.waitForVisibilityOfElement(cartItemsCount);
		return cartItemsCount.getText();
	}

	public void clickOnProceed() {
		util.waitForElementToBeClickable(proceedButton);
		proceedButton.click();
	}

	public String getWevendPaymentMethodText() {
		util.waitForVisibilityOfElement(paymentMethod);
		if (paymentMethod.isDisplayed()) {
			return paymentMethod.getText();
		} else
			return "Not Available";
	}

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

	public boolean verifySuccessfulPayment() {
		util.waitForVisibilityOfElement(ThankYouText);
		if (ThankYouText.isDisplayed()) {
			return true;
		} else
			return false;

	}

	public boolean orderIdIsDisplayed() {
		util.waitForVisibilityOfElement(orderId);
		if (orderId.isDisplayed()) {
			return true;
		} else
			return false;
	}

	public String getOrderId() {
		return orderId.getText();
	}

	public String getGrandTotal() {
		return grandTotal.getText();
	}

	/* tests scripts */

	/**
	 * 
	 * This method will launch and verify the WeVend Store url
	 * 
	 */

	public void launchWevendStore() {

		test = extent.createTest("Verify Wevend payment cycle");
		// test.log(Status.INFO, "Launching the App");
		driver.get(prop.getProperty("WevendStoreUrl"));

		util.waitForUrlToLoad(prop.getProperty("WevendStoreUrl"));

		if (driver.getTitle().equals(prop.getProperty("WevendHompageTitle"))) {
			test.log(Status.INFO, "Application launched sucessfully ");
		} else {
			test.log(Status.FAIL, "Application not launched sucessfully ");
		}
		assertEquals(driver.getCurrentUrl(), prop.getProperty("WevendStoreUrl"));
	}

	/**
	 * 
	 * This method will launch and verify the WeVend Store url
	 * 
	 */

	public void AddProductstoCart(String product1, String product2, String product3) {
		if (driver.getCurrentUrl().equals(prop.getProperty("WevendStoreUrl"))) {
			test.log(Status.INFO, "Application launched sucessfully ");
		} else {
			test.log(Status.FAIL, "Application not launched sucessfully ");
		}
		List<String> products = Arrays.asList(product1, product2, product3);
		for (String product : products) {
			try {
				clickOnAddToCart(product);
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean result = getAddedToCartSuccessmsg().contains(product);
			if (result) {
				test.log(Status.PASS, product + " is successfully added to the cart");
			} else {
				test.log(Status.FAIL, product + " is not added to the cart");
			}
			Assert.assertTrue(result);
		}
		NumOfProducsAddedtoCart = products.size();
	}

	public void VerifyProductInCart() {
		cartCount = getCartItemCount();
		if (cartCount == NumOfProducsAddedtoCart) {
			test.log(Status.INFO, "Total Number of items displayed on the cart is : " + cartCount);
		} else {
			test.log(Status.FAIL,
					"Total Number of items displayed on cart is not matching with items added to the cart: "
							+ cartCount);
		}
		Assert.assertEquals(cartCount, NumOfProducsAddedtoCart);

	}

	public void NavigateToCheckoutPage() {
		clickOnCartIcon();
		util.waitForVisibilityOfElement(cartSubtotal);
		cartSubtotaltxt = getCartSubtotal();
		util.waitForVisibilityOfElement(ProceedToCheckoutBtn);
		ProceedToCheckoutBtn.click();
		util.waitForTitle(prop.getProperty("CheckoutPageTitle"));

		if (driver.getTitle().equals(prop.getProperty("CheckoutPageTitle"))) {
			test.log(Status.PASS, "successfully navigated to checkout page");
		} else {
			test.log(Status.FAIL, "Failed to navigated to checkout page");
		}
		Assert.assertEquals(driver.getTitle(), prop.getProperty("CheckoutPageTitle"));
	}

	public void checkOrderTotal() {

		getWevendPaymentMethodText();
		orderTotaltxt = getCheckoutOrderTotal();
		if (cartSubtotaltxt.equals(orderTotaltxt)) {
			proceedButton.click();
			test.log(Status.PASS, "Cart subtotal is matching with Order Total");
		} else {
			test.log(Status.FAIL, "Cart subtotal is not matching with Order Total");
		}
		Assert.assertEquals(cartSubtotaltxt, orderTotaltxt);
		Assert.assertEquals(getCartItemsCount(), checkoutItmCount.getText());
		test.log(Status.PASS, "Cart itemcount is matching with Order items");
		Assert.assertEquals(getWevendPaymentMethodText(), "weVend Gateway");
		test.log(Status.PASS, "paymet method name is displayed");

	}

	public void navigateToPaymentgatway() {
		try {
			clickOnProceed();
		} catch (Exception e) {
			
		}
		util.waitForVisibilityOfElement(payButn);
		Assert.assertEquals(driver.getTitle(), prop.getProperty("PayemtGatwayPage"));
	}

	public void verifyEntercardDetails() {
		PaymentGateway pay = new PaymentGateway(driver);
		pay.enterCardNum(prop.getProperty("CardNo"));
		pay.enterCardExpiryDate(prop.getProperty("CardExpiry"));
		pay.enterCardCvv(prop.getProperty("CardCvv"));
		clickonPayBtn();
	}

	public void navigatToSuccessPage() {
		boolean verify = verifySuccessfulPayment();
		if (verify) {
			test.log(Status.PASS, "Payment is Successfull");
		} else {
			test.log(Status.FAIL, "payment is unsuccessfull");
		}
		Assert.assertTrue(verifySuccessfulPayment());

	}

	public void verifyOrderId() {
		if (orderIdIsDisplayed()) {
			test.log(Status.PASS, "order id " + getOrderId() + "is displayed");
		}
		Assert.assertTrue(orderIdIsDisplayed());
	}

	public void verifyGrandTotalWithOrderTotal() {
		try {
			if (getGrandTotal().equals(orderTotaltxt)) {
				test.log(Status.PASS, "Grand Total displayed is same as Order Total");
			}
		} catch (Exception e) {	
			e.printStackTrace();
		}

		Assert.assertEquals(getGrandTotal(), orderTotaltxt);
	}
	
	public void verifyBuyMoreFunctionality() {
		util.waitForElementToBeClickable(buyMoreBtn);
		buyMoreBtn.click();
		try {
		util.waitForTitle(prop.getProperty("WevendHompageTitle"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		test.log(Status.INFO,"selection of Buy More button Navigated to Hompage");
		Assert.assertEquals(driver.getTitle(),prop.getProperty("WevendHompageTitle") );
		
		
	}

}
