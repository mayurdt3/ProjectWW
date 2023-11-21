package com.Remosys.Wevend.TestLayer;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.Remosys.WeVend.PageLayer.PaymentGateway;
import com.aventstack.extentreports.Status;

/**
 * Test class for a 'Gumball Store' application.
 * 
 * This class is responsible for defining and executing test cases that validate
 * a particular aspect of the application's functionality. It follows the Test
 * Automation Framework design pattern, ensuring structured and repeatable
 * testing.
 * 
 * @author Remosys Testing Team.
 *
 *         Usage: 1. Create test methods within this class that represent
 *         specific test cases or scenarios. 2. Implement test steps, including
 *         actions and verifications, to validate the desired functionality. 3.
 *         Organize and manage test cases for this specific scenario, keeping
 *         the focus on a single aspect of the application.
 *
 */

public class TestGumBallPage extends BaseTest {

	public int NumOfProducsAddedtoCart;
	int cartCount;
	String cartSubtotaltxt;
	String orderTotaltxt;
	SoftAssert 	softAssert;

	/**
	 * 
	 * This method will launch and verify the WeVend Store URL
	 * 
	 */
	@Test(priority = 0)
	public void testGumballLaunch() {

		driver.get(prop.getProperty("GumballUrl"));
		test = extent.createTest("Gumball payment cycle");
		softAssert = new SoftAssert();

		if (gumball.getTitle().equals(excel.getExcelvalueForKey(2, "GumballTitle"))) {
			test.log(Status.PASS, "Gumball application launched sucessfully ");
		} else {
			test.log(Status.FAIL, "Application is failed to launch");
		}
		// test.log(Status.INFO, "Title of Home page : '" + gumball.getTitle() + "'");
		assertEquals(driver.getCurrentUrl(), excel.getExcelvalueForKey(2, "GumballUrl"));
		softAssert.assertAll();
	}

	/**
	 * This method will verify Add to cart functionality and verify count displayed
	 * on cart is same as number of products added in the card
	 */
	@Test(dependsOnMethods = "testGumballLaunch")
	public void testAddToCartFunctionality() {

		List<String> products = Arrays.asList(excel.getExcelvalueForKey(2, "product1"),
				excel.getExcelvalueForKey(2, "product2"));
		for (String product : products) {
			try {
				gumball.addProductToCart(product);
			} catch (Exception e) {

			}
			boolean result = gumball.getAddedToCartSuccessmsg().contains(product);
			if (result) {
				test.log(Status.PASS, "'" + product + "' is successfully added to the cart");
			} else {
				test.log(Status.FAIL, "'" + product + "' is failed add into the cart");
			}
			softAssert.assertEquals(true, result);
			softAssert.assertAll();
		}

		NumOfProducsAddedtoCart = products.size();

		cartCount = gumball.getCartItemCount();
		if (!(cartCount == NumOfProducsAddedtoCart)) {
			
			test.log(Status.FAIL,
					"Total Number of items displayed on cart is not matching with items added to the cart: '"
							+ cartCount + "'");
		}
		

		softAssert.assertEquals(cartCount, NumOfProducsAddedtoCart);  //holding assert from execution pov, since app under dev.
		softAssert.assertAll();
	}

	/**
	 * This method Verifies successful navigation to Checkout page, verifies Cart
	 * sub-total is matching with Order-Total Verifies checkout ItmCount is same as
	 * number of Items And verifies the navigation to payment gateway page
	 */

	@Test(dependsOnMethods = "testAddToCartFunctionality")
	public void testNavigationToCheckoutPage() {

		String productName = gumball.buyNow(excel.getExcelvalueForKey(2, "product3"));
		
		test.log(Status.PASS, productName + " is added to the cart using Buy Now function");

		String title = gumball.getTitle();
		if (title.equals(excel.getExcelvalueForKey(0, "CheckoutPageTitle"))) {
			test.log(Status.PASS, "Navigate to 'Checkout' page sucessfully");
			
		} else {
			test.log(Status.FAIL, "Failed to navigated to 'Checkout' page");
		}
		//test.log(Status.INFO, "Title of Checkout page : '" + gumball.getTitle() + "'");
		softAssert.assertEquals(title, excel.getExcelvalueForKey(0, "CheckoutPageTitle"));
		softAssert.assertAll();
	}

	/**
	 * This method verifies The Payment gateway Functionality
	 */

	@Test(dependsOnMethods = "testNavigationToCheckoutPage")
	public void testNavigationToPaymentPage() {

		orderTotaltxt = gumball.getCheckoutOrderTotal();
		gumball.clickOnProceed();
		pay = new PaymentGateway(driver);

		String title = pay.getPaymentPageTitle();

		if (title.equals(excel.getExcelvalueForKey(0, "PaymentPageTitle"))) {
			test.log(Status.PASS, "User is navigated to the 'Payment Gateway'");

		} else {
			test.log(Status.FAIL, "Navigate to the 'Payment Gateway' page has failed");

		}
		//test.log(Status.INFO, "Title of 'Payment Gateway' page : '" + gumball.getTitle() + "'");
		softAssert.assertEquals(title, excel.getExcelvalueForKey(0, "PaymentPageTitle"));
		softAssert.assertAll();
	}

	/**
	 * This method verifies The Payment gateway Functionality
	 */
	@Test(dependsOnMethods = "testNavigationToPaymentPage")
	public void testPaymentProcess() {

		pay = new PaymentGateway(driver);
		try {
		//test.log(Status.INFO, "Selecting the Payment method as : Card Pay");
		pay.enterCardNum(excel.getExcelvalueForKey(0, "CardNo"));
		pay.enterCardExpiryDate(excel.getExcelvalueForKey(0, "CardExpiry"));
		pay.enterCardCvv(excel.getExcelvalueForKey(0, "CardCvv"));
		
	} catch (Exception e) {
		test.log(Status.INFO, "Failed to enter Card detail");
	}
		if (pay.isPayBtnEnabled()) {
			test.log(Status.INFO, "Pay button is Enabled");
		} else {
			test.log(Status.FAIL, "Pay button is disabled");
		}
		pay.clickOnPayBtn();
		softAssert.assertAll();
	}

	/**
	 * this method verifies if Payment is successful, Verifies the Grand-Total is
	 * same as order-Total
	 * 
	 */
	@Test(dependsOnMethods = "testPaymentProcess")
	public void testSuccessfullPayment() {

		boolean verify = gumball.verifySuccessfulPayment();
		if (verify) {
			test.log(Status.PASS, "Payment is Successfull");
		} else {
			test.log(Status.FAIL, "Payment Failed");
		}
		//test.log(Status.INFO, "Title of payment success page : '" + gumball.getTitle() + "'");
		softAssert.assertEquals(true, verify);
		try {
			if (!(gumball.getGrandTotal().equals(orderTotaltxt))) {
				test.log(Status.FAIL, "Grand-total displayed is not same as Order-total");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		softAssert.assertEquals(gumball.getGrandTotal(), orderTotaltxt);
		try {
			gumball.clickOnBuyMoreBtn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!(gumball.getTitle().equals(excel.getExcelvalueForKey(2, "GumballTitle")))) {
			test.log(Status.INFO, "Selection of 'Buy More' button failed to navigate to 'Homepage;");
		}
		softAssert.assertEquals(gumball.getTitle(), excel.getExcelvalueForKey(2, "GumballTitle"));
		//test.log(Status.INFO, "Title of Home page : '" + gumball.getTitle() + "'");
		softAssert.assertAll();

	}

}