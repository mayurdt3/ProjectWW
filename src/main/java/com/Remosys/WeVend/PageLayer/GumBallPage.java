package com.Remosys.WeVend.PageLayer;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.Remosys.WeVend.Reader.ExcelFileReader;
import com.Remosys.WeVend.utility.Utility;

/**
 * This class encapsulates the elements, actions, and functionality related to a
 * 'Gum Ball' web application.
 * 
 * @author Remosys Testing Team.
 *
 *         Usage: 1. Create an instance of this class to interact with the web
 *         page. 2. Implement actions and verifications relevant to the
 *         applications functionality.
 * 
 */

public class GumBallPage {

	protected WebDriver driver;
	public ExcelFileReader excel;
	public Utility util;

	public GumBallPage(WebDriver driver) {
		this.driver = driver;
		util = new Utility();
		excel = new ExcelFileReader();
		PageFactory.initElements(driver, this);
	}

	/**
	 * 
	 * Home page WebelElements
	 * 
	 **/

	@FindBy(xpath = "//a[@class='logo']")
	private WebElement logoGumball;

	@FindBy(xpath = "//div[@class='actions-primary']/form")
	private List<WebElement> addtoCartButtonContainer;

	@FindBy(xpath = "//li[@class ='product-item']")
	private List<WebElement> productList;

	@FindBy(xpath = "//a//span[@class='counter-number']")
	private WebElement cartItemsCount;

	@FindBy(xpath = "//div[@data-block='minicart']/a/descendant::span[@class='counter-number']")
	private List<WebElement> buyNowButtons;

	@FindBy(xpath = "//span[text()='Add to Cart']")
	private List<WebElement> addToCartButtons;

	@FindBy(xpath = "//div[contains(text(),'You added ')]")
	private WebElement addToCartSuccessMsg;

	@FindBy(xpath = "//div[contains(text(),'You added ')]")
	public WebElement cartSuccessText;

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

	@FindBy(xpath = "//strong[text()='Order Total']/parent::th/following-sibling::td//span")
	private WebElement orderTotal;

	@FindBy(xpath = "//span[contains(text(),'Thank you')]")
	private WebElement thankYouText;

	@FindBy(xpath = "//td[text()='Grand Total']/following-sibling::td")
	private WebElement grandTotal;

	@FindBy(xpath = "//table")
	private WebElement orderDetailTable;

	@FindBy(xpath = "//div[text()='Buy More']")
	private WebElement buyMoreBtn;

	@FindBy(xpath = "//a[@class='action showcart']")
	private WebElement cartIcon;

	@FindBy(xpath = "//span[@class='count']")
	private WebElement itemsInCart;

	@FindBy(xpath = "//button[@id='btn-minicart-close']")
	private WebElement closeCartButton;

	/**
	 * Clicks on the "Add to Cart" button for a specific product.
	 *
	 * @param product
	 * @throws InterruptedException
	 */

	public void clickOnAddToCart(String product) throws InterruptedException {
		// Iterate through the "Add to Cart" button container.
		System.out.println(addtoCartButtonContainer.size());
		for (int i = 0; i < addtoCartButtonContainer.size(); i++) {
			Thread.sleep(2000);
			WebElement productName = addtoCartButtonContainer.get(i);
			String productNameText = productName.getAttribute("data-product-sku");
			if (product.equalsIgnoreCase(productNameText)) {
				WebElement itemName = addToCartButtons.get(i);
				util.waitForElementToBeClickable(driver, itemName);
				itemName.click();
			}
		}
	}

	public boolean isLogoDisplayed() {
		util.waitForVisibilityOfElement(driver, logoGumball);
		boolean logoFlag = logoGumball.isDisplayed();
		return logoFlag;
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
		util.waitForElementWithFrequency(driver, cartItemsCount, 10, 3);
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
	public void addProductToCart(String s) {

		String addtocartbuttonlocator = "//a[@title ='" + s
				+ "']/parent :: strong// following-sibling:: div//button[@title='Add to Cart']";
		WebElement pr = driver.findElement(By.xpath(addtocartbuttonlocator));
		util.waitForElementToBeClickable(driver, pr);
		pr.click();
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
	public void clickOnPayBtn() {
		util.waitForElementToBeClickable(driver, payBtn);
		// payBtn.click(); // for now to avoid payment
	}

	/**
	 * will retrieve List of products available
	 * 
	 * @return
	 */
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
	 * Retrieves the count of items in the shopping cart.
	 * 
	 * @return
	 */
	public String getCartItemsCount() {
		util.waitForVisibilityOfElement(driver, cartItemsCount);
		return cartItemsCount.getText();
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
	 * click on Buy More Button
	 */
	public void clickOnBuyMoreBtn() {
		util.waitForElementToBeClickable(driver, buyMoreBtn);
		buyMoreBtn.click();
		util.waitForTitle(driver, excel.getExcelvalueForKey(2, "GumballTitle"));
	}

	/**
	 * This method will wait for cartcount to update
	 */
	public void waitForCartCountToUpdate(int count) {
		String cartCountLocator = "//span[@class='counter-number' and text()='" + count + "']";
		util.waitForPrecensOfElementByLocator(driver, cartCountLocator);
	}

	/**
	 * will retrieve items in the cart of int time
	 */
	public int itemsInTheCart() {
		util.waitForElementWithFrequency(driver, itemsInCart, 10, 2);
		int itemCount = 0;
		String cartText;
		try {
			cartText = itemsInCart.getText();

			if (!cartText.isEmpty()) {
				itemCount = Integer.parseInt(cartText);
			} else {
				return itemCount;
			}
			return itemCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemCount;
	}

	/**
	 * click on close Button of cart
	 */
	public void clickOnClose() {
		closeCartButton.click();
	}

	public void waitForCountToBePresentWithFrequency(int count) {
		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofSeconds(2)) // Adjust the polling interval as needed
				.ignoring(NoSuchElementException.class);

		String locator = "//span[@class='counter-number' and text()='" + count + "']";
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
	}

}
