
package com.Remosys.Wevend.TestLayer;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
 * @author Remosys Testing Team.
 *
 *         Usage: 1. Create test methods within this class that represent
 *         specific test cases or scenarios. 2. Implement test steps, including
 *         actions and verifications, to validate the desired functionality.. 3.
 *         Organize and manage test cases for this specific scenario, keeping
 *         the focus on a single aspect of the application.
 *
 */
public class TestCarwash extends BaseTest {
	SoftAssert softAssert;

	/**
	 * This method verifies the launch of Car wash application
	 * 
	 * @throws InterruptedException
	 */
	@Test(priority = 1)
	public void testCarWashLaunch() {
		test = extent.createTest("Verify 'Car Wash' payment cycle");
		carwash = new CarWash(driver);
		softAssert = new SoftAssert();

		driver.get(prop.getProperty("carWashUrl"));

		try {
			carwash.verifyHompageUrl();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean a = carwash.getTitle().equals(excel.getExcelvalueForKey(0, "CarwashHomepagePageTitle"));

		if (a) {
			test.log(Status.PASS, "'Car Wash' application launched sucessfully");
		} else {
			test.log(Status.FAIL, "'Failed to launch the application");
		}

		softAssert.assertEquals(carwash.getTitle(), excel.getExcelvalueForKey(0, "CarwashHomepagePageTitle"));
		softAssert.assertAll();
	}

	/**
	 * This method verifies the Selection of given amount using manual selection
	 * method
	 * 
	 */
	@Test(dependsOnMethods = "testCarWashLaunch")
	public void testAmountSelection() {
		try {
			carwash.payUsingManualPayment(excel.getExcelvalueForKey(0, "Amount"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean verify = carwash.verifyNavigateToPaymentGateway();
		test.log(Status.INFO, "Amount selected");
		Assert.assertEquals(true, verify);
		test.log(Status.PASS, "Navigate to 'Payment GateWay' successfuly");
		softAssert.assertAll();
	}

	/**
	 * This method verifies the successful navigation of Payment Gateway
	 * 
	 */

	@Test(dependsOnMethods = "testAmountSelection")
	public void testPaymentProcess() {

		PaymentGateway pay = new PaymentGateway(driver);
		try {
			if (carwash.getTitle().equals(excel.getExcelvalueForKey(0, "PayemtGatwayPageTitle"))) {
				test.log(Status.PASS, "Navigate to 'Payment Gateway' page sucessfully");
			} else {
				test.log(Status.FAIL, "Failed to Navigate to 'Payment Gateway' page");
			}

			pay.enterCardNum(excel.getExcelvalueForKey(0, "CardNo"));
			pay.enterCardExpiryDate(excel.getExcelvalueForKey(0, "CardExpiry"));
			pay.enterCardCvv(excel.getExcelvalueForKey(0, "CardCvv"));
		} catch (Exception e) {
			test.log(Status.INFO, "Failed to enter Card detail");
		}
		boolean verify = carwash.isAuthorizeBtnEnabled();
		if (!verify) {
			test.log(Status.FAIL, "Pay button is disabled");
		}
		softAssert.assertEquals(true, verify);
		carwash.selectAuthorizeBtn();
		softAssert.assertAll();

	}

	/**
	 * Verifies Count down page Functionality.
	 */
	@Test(dependsOnMethods = "testPaymentProcess")
	public void testCountdownTimerPage() {

		try {
			if (carwash.isLogoDisplayed()) {
				test.log(Status.PASS, "Navigate to 'Payment Success' page Successfully");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to navigat to 'payment success' page");
			e.printStackTrace();
		}

		try {
			carwash.selectDoneAfterSomeTime();
			test.log(Status.INFO, "Click on 'Done' button after '5' seconds");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to Click on Done button after 5 seconds");
			e.printStackTrace();
		}
		boolean verify = carwash.isThankYouMessageDisplayed();
		softAssert.assertEquals(true, verify);
		softAssert.assertAll();
	}

	/**
	 * Verifies the Successful navigation to Payment success page Verifies bill
	 * amount with selected amount
	 */
	@Test(dependsOnMethods = "testCountdownTimerPage")
	public void testSuccessfulPaymentPage() {
		if (carwash.isOrderIdDisplayed() && carwash.isBillAmountDisplayed()) {
			test.log(Status.INFO, carwash.getOrderId());
			test.log(Status.INFO, "Bill amount :" + carwash.getBillAmount());
		}
		if (carwash.getBillAmount().equals(excel.getExcelvalueForKey(0, "Amount"))) {
			test.log(Status.PASS, "Ammount selected before payment and Bill-amount is same");

		} else {
			test.log(Status.FAIL, "Ammount selected before payment and Bill-amount is different");
		}
		softAssert.assertEquals(carwash.getBillAmount(), (excel.getExcelvalueForKey(0, "Amount")));
		softAssert.assertAll();
	}

}
