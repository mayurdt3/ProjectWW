package com.Remosys.Wevend.TestLayer;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.Remosys.WeVend.PageLayer.PaymentGateway;
import com.aventstack.extentreports.Status;

/**
 * Test class for a 'Habco Store' application.
 * 
 * This class is responsible for defining and executing test cases that validate
 * a particular aspect of the application's functionality. It follows the Test
 * Automation Framework design pattern, ensuring structured and repeatable
 * testing.
 * 
 * @author Mayur Takalikar.
 *
 *         Usage: 1. Create test methods within this class that represent
 *         specific test cases or scenarios. 2. Implement test steps, including
 *         actions and verifications, to validate the desired functionality. 3.
 *         Organize and manage test cases for this specific scenario, keeping
 *         the focus on a single aspect of the application.
 *
 */

public class TestHabcoPage extends BaseTest {

	public int NumOfProducsAddedtoCart;
	int cartCount;
	String cartSubtotaltxt;
	String orderTotaltxt;
	String productName;

	/* tests scripts */

	/**
	 * 
	 * This method will launch and verify the WeVend Store URL
	 * 
	 */
	@Test(priority = 0)
	public void verifyHabcoLaunch() {

		driver.get(prop.getProperty("habcoUrl"));
		test = extent.createTest("Habco payment cycle");

		if (driver.getTitle().equals(excel.getExcelvalueForKey(1, "HabcoTitle"))) {
			test.log(Status.PASS, "Habco application launched sucessfully ");
		} else {
			test.log(Status.FAIL, "Application is failed to launch");
		}
		test.log(Status.INFO, "Title of Home page : '" + habco.getTitle() + "'");
		assertEquals(driver.getCurrentUrl(), excel.getExcelvalueForKey(1, "habcoUrl"));
	}

	/**
	 * This method will verify Add to cart functionality and verify count displayed
	 * on cart is same as number of products added in the card
	 */
	@Test(dependsOnMethods = "verifyHabcoLaunch")
	public void verifyAddtocartFunctionality() {

		List<String> products = Arrays.asList(excel.getExcelvalueForKey(1, "product1"),
				excel.getExcelvalueForKey(1, "product2"));
		for (String product : products) {
			try {
				habco.AddProductToCart(product);
				// test.log(Status.PASS, "'" + product + "' is successfully added to the cart");
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean result = wevend.getAddedToCartSuccessmsg().contains(product);
			if (result) {
				test.log(Status.PASS, "'" + product + "' is successfully added to the cart");
			} else {
				test.log(Status.FAIL, "'" + product + "' is failed add into the cart");
			}
			Assert.assertTrue(result);
		}
		// habco.clickOnCartIcon();
		NumOfProducsAddedtoCart = products.size();

		cartCount = habco.getCartItemCount();
		if (cartCount == NumOfProducsAddedtoCart) {
			test.log(Status.INFO, "Total Number of items displayed on the cart is : '" + cartCount + "'");
		} else {
			test.log(Status.FAIL,
					"Total Number of items displayed on cart is not matching with items added to the cart: '"
							+ cartCount + "'");
		}
		Assert.assertEquals(cartCount, NumOfProducsAddedtoCart);

	}

	/**
	 * This method Verifies successful navigation to Checkout page, verifies Cart
	 * sub-total is matching with Order-Total Verifies checkout ItmCount is same as
	 * number of Items And verifies the navigation to payment gateway page
	 */

	@Test(dependsOnMethods = "verifyAddtocartFunctionality")
	public void navigateCheckoutPageTest() {

		productName = habco.buyNow(excel.getExcelvalueForKey(1, "product3"));

		test.log(Status.PASS, productName + " is added to the cart using Buy Now function");

		String title = habco.getTitle();

		if (title.contains(excel.getExcelvalueForKey(1, "CheckoutPageTitle"))) {
			test.log(Status.PASS, "User is navigated to the Checkout page");
			Assert.assertTrue(true);
		} else {
			test.log(Status.FAIL, "Navigation to the checkout page has failed");
			Assert.assertTrue(false);
		}
		test.log(Status.INFO, "Title of Checkout page : '" + habco.getTitle() + "'");

	}

	/**
	 * This method verifies The Payment gateway Functionality
	 */
	@Test(dependsOnMethods = "navigateCheckoutPageTest")
	public void navigatePaymentPage() {

		orderTotaltxt = habco.getCheckoutOrderTotal();
		habco.clickOnProceed();
		pay = new PaymentGateway(driver);

		String title = pay.getPaymentPageTitle();
		System.out.println(title);
		if (title.equals(excel.getExcelvalueForKey(0, "PaymentPageTitle"))) {
			test.log(Status.PASS, "User is navigated to the Payment Gateway Page");
			Assert.assertTrue(true);
		} else {
			test.log(Status.FAIL, "Nvigation to the Payment Gateway Page has failed");
			Assert.assertTrue(false);
		}
		test.log(Status.INFO, "Title of 'Payment Gateway' page : '" + habco.getTitle() + "'");

	}

	/**
	 * This method verifies The Payment gateway Functionality
	 */

	@Test(dependsOnMethods = "navigatePaymentPage")
	public void doPayment() throws InterruptedException {

		pay = new PaymentGateway(driver);
		String wevendPaymentMethodText = pay.getWevendPaymentMethodText();
		System.out.println(wevendPaymentMethodText);
		test.log(Status.INFO, "Selecting the Payment method as : Card Pay");
		pay.enterCardNum(excel.getExcelvalueForKey(0, "CardNo"));
		pay.enterCardExpiryDate(excel.getExcelvalueForKey(0, "CardExpiry"));
		pay.enterCardCvv(excel.getExcelvalueForKey(0, "CardCvv"));
		if (pay.isPayBtnEnabled()) {
			test.log(Status.INFO, "Pay button is Enabled");
		} else {
			test.log(Status.FAIL, "Pay button is disabled");
		}
		pay.clickOnPayBtn();

		boolean verify = habco.verifySuccessfulPayment();

		if (verify) {
			test.log(Status.PASS, "Payment is Successfull");
		} else {
			test.log(Status.FAIL, "Payment Failed");
		}
		Assert.assertTrue(verify);
	}

	/**
	 * this method verifies if Payment is successful, Verifies the Grand-Total is
	 * same as order-Total
	 * 
	 */
	@Test(dependsOnMethods = "doPayment")
	public void verifySuccessPage() {

		boolean verify = habco.verifySuccessfulPayment();
		if (verify) {
			test.log(Status.PASS, "Payment is Successfull");
		} else {
			test.log(Status.FAIL, "Payment Failed");
		}
		test.log(Status.INFO, "Title of payment success page : '" + habco.getTitle() + "'");
		Assert.assertTrue(verify);
		try {
			if (habco.getGrandTotal().equals(orderTotaltxt)) {
				test.log(Status.PASS, "Grand-total displayed is same as Order-total");
			} else {
				test.log(Status.FAIL, "Grand-total displayed is not same as Order-total");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(habco.getGrandTotal(), orderTotaltxt);
		try {
			habco.clickOnBuyMoreBtn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (habco.getTitle().equals(excel.getExcelvalueForKey(1, "WevendHompageTitle"))) {
			test.log(Status.INFO, "Selection of 'Buy More' button leads to 'Homepage;");
		} else {
			test.log(Status.INFO, "Selection of 'Buy More' button failed to navigate to 'Homepage;");
		}
		Assert.assertEquals(driver.getTitle(), excel.getExcelvalueForKey(1, "WevendHompageTitle"));
		test.log(Status.INFO, "Title of Home page : '" + driver.getTitle() + "'");
	}

}
