package com.Remosys.WeVend.PageLayer;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.Remosys.WeVend.Reader.ExcelFileReader;
import com.Remosys.WeVend.utility.Utility;
/**
 * This class encapsulates the elements, actions, and functionality related to a
 * 'Habco' web application.
 * 
 * @author Remosys Testing Team.
 *
 *         Usage: 1. Create an instance of this class to interact with the web
 *         page. 2. Implement actions and verifications relevant to the
 *         applications functionality.
 * 
 */
public class HabcoPage {

	private WebDriver driver;
	Utility util;
	ExcelFileReader excel;

	public HabcoPage(WebDriver driver) {
		this.driver = driver;
		util = new Utility();
		excel = new ExcelFileReader();

		PageFactory.initElements(driver, this);
	}

	private By logoHabco = By.xpath("//a[@class='logo']//img");

	@FindBy(xpath = "//div[contains(text(),'You added ')]")
	public WebElement cartSuccessText;

	@FindBy(xpath = "//a[@class='action showcart']")
	public WebElement cartIcon;

	@FindBy(xpath = "//div[@class='amount price-container']//span[@class='price']")
	public WebElement cartSubtotal;

	@FindBy(xpath = "//button[@id='top-cart-btn-checkout']")
	private WebElement proceedToCheckoutBtn;

	@FindBy(xpath = "//div[@class='payment-method _active']//div[contains(@class,'payment-method-title')]/span")
	private WebElement paymentMethod;

	@FindBy(xpath = "//button[contains(text(),'Pay')]")
	private WebElement payBtn;

	@FindBy(xpath = "//b[text()='Card Pay']")
	private WebElement cardPayOption;

	private By countProductList = By.xpath("//div[@class='products-grid grid']//li");

	@FindBy(xpath = "//button/span[text()='Proceed']")
	private WebElement proceedButton;

	@FindBy(xpath = "//a[@class='logo']")
	private WebElement avsLogo;

	@FindBy(xpath = "//div[@class='actions-primary']/form")
	private List<WebElement> addtoCartButtonContainer;

	@FindBy(xpath = "//li[@class ='product-item']")
	private List<WebElement> productList;

	@FindBy(xpath = "//span[text()='Add to Cart']")
	private List<WebElement> addToCartButtons;

	@FindBy(xpath = "//div[contains(text(),'You added ')]")
	private WebElement addToCartSuccessMsg;

	@FindBy(xpath = "//div[@data-block='minicart']/a/descendant::span[@class='counter-number']")
	private WebElement cartItemsCount;

	@FindBy(xpath = "//span[contains(text(),'Thank you')]")
	private WebElement thankYouText;

	@FindBy(xpath = "//strong[text()='Order Total']/parent::th/following-sibling::td//span")
	private WebElement orderTotal;

	@FindBy(xpath = "//td[text()='Grand Total']/following-sibling::td")
	private WebElement grandTotal;

	@FindBy(xpath = "//table")
	private WebElement orderDetailTable;

	@FindBy(xpath = "//div[text()='Buy More']")
	private WebElement buyMoreBtn;

	/**
	 * This method is used to test the logo of the Habco HomePage
	 * 
	 * @return logoFlag boolean value
	 */
	public boolean isLogoDisplayed() {

		WebElement logo = driver.findElement(logoHabco);
		boolean logoFlag = logo.isDisplayed();
		return logoFlag;
	}

	/**
	 * Buy now and proceed to the checkout page
	 * 
	 * @param itemneeded
	 */

	public String buyNow(String productName) {

		WebElement buyItem = driver.findElement(By.xpath("//div[@class='product-item-info']//strong//a[@title='"
				+ productName + "']/../following-sibling::div[@class='product-item-inner']//button[@title='Buy Now']"));
		util.waitForElementWithFrequency(driver, buyItem, getProductListCount(), 2);
		buyItem.click();
		return productName;

	}

	/**
	 * Retrieves the success message displayed when an item is added to the cart.
	 * 
	 * @return Cart Success Text
	 */
	public String cartSuccessmsg() {

		util.waitForVisibilityOfElement(driver, cartSuccessText);
		return cartSuccessText.getText();
	}

	/**
	 * Clicks on the cart icon to view the shopping cart.
	 */
	public void clickOnCartIcon() {

		util.waitForElementToBeClickable(driver, cartIcon);

		cartIcon.click();
	}

	/**
	 * Retrieves the subtotal amount from the shopping cart.
	 * 
	 * @return
	 */
	public String getCartSubtotal() {
		util.waitForElementToBeClickable(driver, cardPayOption);

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
		util.waitForTitle(driver, excel.getExcelvalueForKey(1, "CheckoutPageTitle"));
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
	 * Clicks on the "Pay" button to initiate a payment
	 */
	public void clickonPayBtn() {
		util.waitForElementToBeClickable(driver, payBtn);
		// payBtn.click();
	}

	public int getProductListCount() {
		List<WebElement> count = driver.findElements(countProductList);
		return count.size();
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
	 * returns the title of the page
	 * 
	 * @return
	 */
	public String getTitle() {
		return driver.getTitle();
	}

	/**
	 * Adds a randomly selected product to the shopping cart from a list of
	 * products.
	 */
	public void addProductToCart(String s) {
		String addtocartbuttonlocator = "//a[@title ='" + s
				+ "']/parent :: strong// following-sibling:: div//button[@title='Add to Cart']";
		WebElement pr = driver.findElement(By.xpath(addtocartbuttonlocator));
		pr.click();

	}

	/**
	 * Retrieves the count of items in the shopping cart.
	 * 
	 * @return
	 */
	public int getCartItemCount() {
		util.waitForElementToBeClickable(driver, cartItemsCount);
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
	 * Verifies the Thank you text is visible after payment is done
	 * 
	 * @return
	 */
	public boolean verifySuccessfulPayment() {
		util.waitForVisibilityOfElement(driver, thankYouText);
		if (thankYouText.isDisplayed()) {
			return true;
		} else
			return false;
	}

	/**
	 * Retrieves the order Total amount from the Checkout page.
	 * 
	 * @return
	 */
	public String getCheckoutOrderTotal() {
		return orderTotal.getText();
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
		util.waitForTitle(driver, excel.getExcelvalueForKey(1, "HabcoTitle"));
	}

}
