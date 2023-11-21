package com.Remosys.Wevend.TestLayer;

import static org.testng.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.Remosys.WeVend.PageLayer.PaymentGateway;
import com.Remosys.WeVend.PageLayer.WevendStore;
import com.aventstack.extentreports.Status;

/**
 * Test class for a 'WeVend Store' application.
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
 *         actions and verifications, to validate the desired functionality.. 3.
 *         Organize and manage test cases for this specific scenario, keeping
 *         the focus on a single aspect of the application.
 *
 */

public class TestWevendStore extends BaseTest {

	public int numOfProducsAddedtoCart;
	int cartCount;
	String cartSubtotalTxt;
	String orderTotaltxt;
	SoftAssert softAssert;

	/* tests scripts */
	/**
	 * 
	 * This method will launch and verify the WeVend Store URL
	 * 
	 */
	@Test(priority = 0)
	public void testLaunchWevend() {

		wevend = new WevendStore(driver);
		softAssert = new SoftAssert();
		
		test = extent.createTest("Verify 'Wevend Store' payment cycle");
		driver.get(prop.getProperty("WevendStoreUrl"));
		if (wevend.getTitle().equals(excel.getExcelvalueForKey(0, "WevendHompageTitle"))) {
			test.log(Status.PASS, "'WeVend Store' Application launched sucessfully ");
		} else {
			test.log(Status.FAIL, "Application is failed to launch");
		}
		//test.log(Status.INFO, "Title of Home page : '"+ wevend.getTitle()+"'");
		assertEquals(driver.getCurrentUrl(), excel.getExcelvalueForKey(0, "WevendStoreUrl"));
		softAssert.assertAll();
	}

	/**
	 * This method will verify Add to cart functionality and verify count displayed
	 * on cart is same as number of products added in the card
	 */
	@Test(dependsOnMethods = "testLaunchWevend")
	public void testAddTocartFunctionality() {

		List<String> products = Arrays.asList(excel.getExcelvalueForKey(0, "product1"),
				excel.getExcelvalueForKey(0, "product2"), excel.getExcelvalueForKey(0, "product3"));
		for (String product : products) {
			try {
				wevend.clickOnAddToCart(product);
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean result = wevend.getAddedToCartSuccessmsg().contains(product);
			if (result) {
				test.log(Status.PASS, "'" + product + "' is successfully added to the cart");
			} else {
				test.log(Status.FAIL, "'" + product + "' is failed add into the cart");
			}
			softAssert.assertEquals(true, result);
			softAssert.assertAll();
		}
		
		numOfProducsAddedtoCart = products.size();

		cartCount = wevend.getCartItemCount();
		if (!(cartCount == numOfProducsAddedtoCart)) {
			test.log(Status.FAIL,
		"Total Number of items displayed on cart is not matching with items added to the cart: '"
		+ cartCount + "'");
		}
		softAssert.assertEquals(cartCount, numOfProducsAddedtoCart);
		softAssert.assertAll();
		
	}

	/**
	 * This method Verifies successful navigation to Checkout page verifies
	 * Cart sub-total is matching with Order-Total Verifies checkout ItmCount is same
	 * as number of Items And verifies the navigation to payment gateway page
	 */
	@Test(dependsOnMethods = "testAddTocartFunctionality")
	public void testCheckoutPage() {

		wevend.clickOnCartIcon();
		cartSubtotalTxt = wevend.getCartSubtotal();
		wevend.clickOnProceedToCheckoutBtn();

		if (wevend.getTitle().equals(excel.getExcelvalueForKey(0, "CheckoutPageTitle"))) {
			test.log(Status.PASS, "Navigate to 'Checkout' page sucessfully");
		} else {
			test.log(Status.FAIL, "Failed to navigate to 'Checkout' page");
		}
		
		//test.log(Status.INFO, "Title of Checkout page : '"+ wevend.getTitle()+"'");
		softAssert.assertEquals(wevend.getTitle(), excel.getExcelvalueForKey(0, "CheckoutPageTitle"));
		
		wevend.getWevendPaymentMethodText();
		orderTotaltxt = wevend.getCheckoutOrderTotal();
		
		if (!(cartSubtotalTxt.equals(orderTotaltxt))){
			test.log(Status.FAIL, "Cart-subtotal is not matching with Order-Total under OrderSummary");
		}
		softAssert.assertEquals(cartSubtotalTxt, orderTotaltxt);
		
		softAssert.assertEquals(wevend.getWevendPaymentMethodText(), excel.getExcelvalueForKey(0, "PaymentMethodName"));
		test.log(Status.PASS, "Paymet method name is displayed : " + wevend.getWevendPaymentMethodText());
		softAssert.assertAll();
	}

	/**
	 * This method verifies The Payment gateway Functionality
	 */
	@Test(dependsOnMethods = "testCheckoutPage")
	public void testPaymentGatewayPage() {
		try {
			wevend.clickOnProceed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String title = wevend.getTitle();
		if (title.equals(excel.getExcelvalueForKey(0, "PayemtGatwayPageTitle"))) {
			test.log(Status.PASS, "Navigated to 'Payment Gateway' page sucessfully");
		} else {
			test.log(Status.FAIL, "Failed to Navigate to 'Payment Gateway' page");
		}
		//test.log(Status.INFO, "Title of Payment Gateway page : '"+ wevend.getTitle()+"'");
		softAssert.assertEquals(title, excel.getExcelvalueForKey(0, "PayemtGatwayPageTitle"));
		PaymentGateway pay = new PaymentGateway(driver);
		
		try {
			//test.log(Status.INFO, "Selecting the Payment method as : Card Pay");
			pay.enterCardNum(excel.getExcelvalueForKey(0, "CardNo"));
			pay.enterCardExpiryDate(excel.getExcelvalueForKey(0, "CardExpiry"));
			pay.enterCardCvv(excel.getExcelvalueForKey(0, "CardCvv"));
			
		} catch (Exception e) {
			test.log(Status.INFO, "Failed to enter Card detail");
		}
		//test.log(Status.INFO, "Card details are entered successfully");
		if(pay.isPayBtnEnabled()) {
			test.log(Status.INFO, "Pay button is Enabled");
		}
		else {
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
	@Test(dependsOnMethods = "testPaymentGatewayPage")
	public void testSuccessfullPayment() {

		boolean verify = wevend.verifySuccessfulPayment();
		if (verify) {
			test.log(Status.PASS, "Payment is Successfull");
		} else {
			test.log(Status.FAIL, "Payment Failed");
		}
		test.log(Status.INFO, "Title of payment success page : '"+ wevend.getTitle()+"'");
		softAssert.assertEquals(true, verify);
		
		try {
			if (!(wevend.getGrandTotal().equals(orderTotaltxt))) {
				test.log(Status.FAIL, "Grand-total displayed is not same as Order-total");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		softAssert.assertEquals(wevend.getGrandTotal(), orderTotaltxt);
		try {
			wevend.clickOnBuyMoreBtn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!(wevend.getTitle().equals(excel.getExcelvalueForKey(0, "WevendHompageTitle")))) {
			test.log(Status.INFO, "Selection of 'Buy More' button failed to navigate to 'Homepage;");
		}
		softAssert.assertEquals(wevend.getTitle(), excel.getExcelvalueForKey(0, "WevendHompageTitle"));
		//test.log(Status.INFO, "Title of Home page : '"+ wevend.getTitle()+"'");
		softAssert.assertAll();
	}

}
