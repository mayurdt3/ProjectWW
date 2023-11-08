package com.Remosys.Wevend.TestLayer;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.Remosys.WeVend.PageLayer.PaymentGateway;
import com.aventstack.extentreports.Status;

public class TestGumBallPage extends BaseTest {
	
	public int NumOfProducsAddedtoCart;
	int cartCount;
	String cartSubtotaltxt;
	String orderTotaltxt;

	@Test(priority = 0)
	public void verifyGumballLaunch() {

		driver.get(prop.getProperty("GumballUrl"));
		test = extent.createTest("Gumball payment cycle");

		if (driver.getTitle().equals(excel.getExcelvalueForKey(2, "GumballTitle"))) {
			test.log(Status.PASS, "Gumball application launched sucessfully ");
		} else {
			test.log(Status.FAIL, "Application is failed to launch");
		}
		test.log(Status.INFO, "Page Title : '"+ gumball.getTitle()+"'");
		assertEquals(driver.getCurrentUrl(), excel.getExcelvalueForKey(2, "GumballUrl"));
	}

	@Test(dependsOnMethods ="verifyGumballLaunch" )
	public void verifyAddtocartFunctionality() {

		List<String> products = Arrays.asList(excel.getExcelvalueForKey(2, "product1"),
				excel.getExcelvalueForKey(2, "product2"));
		for (String product : products) {
			try {
				gumball.AddProductToCart(product);
				//test.log(Status.PASS, "'" + product + "' is successfully added to the cart");
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
		//gumball.clickOnCartIcon();
		NumOfProducsAddedtoCart = products.size();

		cartCount = gumball.getCartItemCount();
		if (cartCount == NumOfProducsAddedtoCart) {
			test.log(Status.INFO, "Total Number of items displayed on the cart is : '" + cartCount + "'");
		} else {
			test.log(Status.FAIL,
					"Total Number of items displayed on cart is not matching with items added to the cart: '"
							+ cartCount + "'");
		}

		Assert.assertEquals(cartCount, NumOfProducsAddedtoCart);
	}
		

	@Test(dependsOnMethods = "verifyAddtocartFunctionality")
	public void navigateCheckoutPageTest() {
		
		String productName = gumball.buyNow(excel.getExcelvalueForKey(2, "product3"));
		test.log(Status.PASS, productName + " is added to the cart using Buy Now function");
	
		
		if (driver.getTitle().equals(excel.getExcelvalueForKey(0, "CheckoutPageTitle"))) {
			test.log(Status.PASS, "Navigate to 'Checkout' page sucessfully");
		} else {
			test.log(Status.FAIL, "Failed to navigated to 'Checkout' page");
		}
		Assert.assertEquals(driver.getTitle(), excel.getExcelvalueForKey(0, "CheckoutPageTitle"));
		test.log(Status.INFO, "Page Title : '"+ gumball.getTitle()+"'");
		}

	@Test(dependsOnMethods = "navigateCheckoutPageTest")
	public void navigatePaymentPage() {

		gumball.clickOnProceed();
		pay = new PaymentGateway(driver);

		String title = pay.getPaymentPageTitle();
		System.out.println(title);
		if (title.equals(excel.getExcelvalueForKey(0, "PaymentPageTitle"))) {
			test.log(Status.PASS, "User is navigated to the 'Payment Gateway'");
			Assert.assertTrue(true);
		}else
		{
			test.log(Status.FAIL, "Navigate to the 'Payment Gateway' page has failed");
			Assert.assertTrue(false);
		}	
		test.log(Status.INFO, "Page Title : '"+ gumball.getTitle()+"'");
	}

	@Test(dependsOnMethods = "navigatePaymentPage")
	public void doPayment() throws InterruptedException {

		pay = new PaymentGateway(driver);
		String wevendPaymentMethodText = pay.getWevendPaymentMethodText();
		System.out.println(wevendPaymentMethodText);
		test.log(Status.INFO, "Selecting the Payment method as : Card Pay");
		pay.enterCardNum(excel.getExcelvalueForKey(0, "CardNo"));
		pay.enterCardExpiryDate(excel.getExcelvalueForKey(0, "CardExpiry"));
		pay.enterCardCvv(excel.getExcelvalueForKey(0, "CardCvv"));
		if(pay.isPayBtnEnabled()) {
			test.log(Status.INFO, "Pay button is Enabled");
		}
		else {
			test.log(Status.FAIL, "Pay button is disabled");
		}
		pay.clickOnPayBtn();
		
		boolean verify = habco.verifySuccessfulPayment();
		test.log(Status.INFO, "Page Title : '"+ gumball.getTitle()+"'");
		
		if (verify) {
			test.log(Status.PASS, "Payment is Successfull");
		} else {
			test.log(Status.FAIL, "Payment Failed");
		}
		Assert.assertTrue(verify);
	}

	
	
	
	
	
}