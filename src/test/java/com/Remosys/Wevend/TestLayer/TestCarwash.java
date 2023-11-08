
package com.Remosys.Wevend.TestLayer;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.Remosys.WeVend.PageLayer.CarWash;
import com.Remosys.WeVend.PageLayer.PaymentGateway;
import com.aventstack.extentreports.Status;

/**
 * Test class for a Car wash application.
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
 *         actions and verifications, to validate the desired functionality.. 3.
 *         Organize and manage test cases for this specific scenario, keeping
 *         the focus on a single aspect of the application.
 *
 */
public class TestCarwash extends BaseTest {
	


	/**
	 * This method verifies the launch of Car wash application
	 */
	@Test(priority = 1)
	public void verifylaunchCarWash() {
		
		carwash = new CarWash(driver);
		boolean a;
		driver.get(prop.getProperty("carWashUrl"));
		try {
			carwash.verifyHompageUrl();
		} catch (IOException e) {

			e.printStackTrace();
		}
		if (driver.getTitle().equals(excel.getExcelvalueForKey(0,"CarwashHomepagePageTitle"))) {
			a = true;
		} else {
			a = false;
		}
		test = extent.createTest("Verify 'Car Wash' payment cycle");
		Assert.assertTrue(a);
		test.log(Status.PASS, "'Car Wash' application launched sucessfully");

	}

	/**
	 * This method verifies the Selection of given amount using manual selection
	 * method
	 * 
	 */
	@Test(dependsOnMethods = "verifylaunchCarWash")
	public void verifyAmountSelection() {
		try {
		carwash.payUsingManualpayment(excel.getExcelvalueForKey(0,"Amount"));
		}
		catch (Exception e) {
		e.printStackTrace();
		}
		test.log(Status.INFO, "Amount selected");
		Assert.assertTrue(carwash.verifyNavigateToPaymentGateway());
		test.log(Status.PASS, "Navigate to 'Payment GateWay' successfuly");
	}
	
	/**
	 * This method verifies the successful navigation of Payment Gateway
	 * 
	 */

	@Test(dependsOnMethods = "verifyAmountSelection")
	public void verifyPaymentGatewaypage() {

		PaymentGateway pay = new PaymentGateway(driver);
		try {
			Assert.assertEquals(driver.getTitle(), excel.getExcelvalueForKey(0,"PayemtGatwayPageTitle"));
			test.log(Status.PASS, "Navigate to 'Payment Gateway' page sucessfully");
			pay.enterCardNum(excel.getExcelvalueForKey(0,"CardNo"));
			pay.enterCardExpiryDate(excel.getExcelvalueForKey(0,"CardExpiry"));
			pay.enterCardCvv(excel.getExcelvalueForKey(0,"CardCvv"));
		} catch (Exception e) {
			test.log(Status.INFO, "Failed to enter Card detail");
		}
		Assert.assertTrue(carwash.isAuthorizebtnEnabled());

	}

	
	
	
	/**
	 * Verifies Count down page Functionality.
	 */
	@Test(dependsOnMethods = "verifyPaymentGatewaypage")
	public void verifyCountdownTimerPage() {
		carwash.selectAuthorizeBtn();
		try {
			if (carwash.isLogoDispayed()) {
				test.log(Status.PASS, "Navigate to 'Payment Success' page Successfuly");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to navigat to 'payment success' page");
			e.printStackTrace();
		}
	
		
//		try {
//			carwash.selectDoneAfterSomeTime();
//			test.log(Status.PASS, "Click on 'Done' button after count down");
//		} catch (Exception e) {
//			test.log(Status.FAIL, "Failed to Click on Done button after count down");
//			e.printStackTrace();
//		}
		
		try {
			carwash.selectDoneAfterSomeTime();
			test.log(Status.INFO, "Click on 'Done' button after '5' seconds");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to Click on Done button after 5 seconds");
			e.printStackTrace();
		}
		Assert.assertTrue(carwash.isThankYouMessageDisplayed());
	}

	
	/**
	 * Verifies the Successful navigation to Payment success page
	 * Verifies bill amount with selected amount
	 */
	@Test(dependsOnMethods = "verifyCountdownTimerPage")
	public void verifySuccessfulPaymentPage() {
		if(carwash.isOrderIdDisplayed()&&carwash.isBillAmmountDisplayed()) {
			test.log(Status.INFO, carwash.getOrderId());
			test.log(Status.INFO, "Bill amount :"+carwash.getBillAmmount());
		}
		if (carwash.getBillAmmount().equals(excel.getExcelvalueForKey(0,"Amount"))) {
			test.log(Status.PASS, "Ammount selected before payment and Bill-amount is same");
			
		} else {
			test.log(Status.FAIL, "Ammount selected before payment and Bill-amount is different");
		}
		Assert.assertTrue(carwash.getBillAmmount().equals(excel.getExcelvalueForKey(0,"Amount")));
		

	}
	
	


}
