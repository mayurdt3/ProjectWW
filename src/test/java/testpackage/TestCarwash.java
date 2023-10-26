
package testpackage;

import org.testng.annotations.Test;

import carwash.base.CarWash;

/**
 * Test class for a Car wash application.
 * 
 * This class is responsible for defining and executing test cases that validate a particular
 * aspect of the application's functionality. It follows the Test Automation Framework design pattern,
 * ensuring structured and repeatable testing.
 * 
 * @author Mayur Takalikar.
 *
 * Usage:
 * 1. Create test methods within this class that represent specific test cases or scenarios.
 * 2. Implement test steps, including actions and verifications, to validate the desired functionality..
 * 3. Organize and manage test cases for this specific scenario, keeping the focus on a single aspect of the application.
 *
 */
public class TestCarwash extends BaseTest {
	//@Test
	public void CarwashPaymentCycle () {
		CarWash carwash = new CarWash(driver);
		carwash.VerifylaunchUrl();
		carwash.VerifySelectAmount(prop.getProperty("Amount"));
		carwash.verifyEntercardDetails();
		carwash.verifyNavigationToCountdownPage();
		carwash.verifySuccessfulPayment();
		carwash.verifyAmount();	
	}
	@Test(priority = 1)
	public void verifylaunchCarWash() {
		carwash = new CarWash(driver);
		carwash.VerifylaunchUrl();	
	}
	
	@Test(priority = 2)
	public void verifyAmountSelection() {
		
		carwash.VerifySelectAmount(prop.getProperty("Amount"));
	}
	
	@Test(priority = 3)
	public void verifyPaymentGatewaypage() {
		carwash.verifyEntercardDetails();
	}
	

	@Test(priority = 4)
	public void verifyCountdownTimerPage() {
	
		carwash.verifyNavigationToCountdownPage();
		
	}
	
	@Test(priority = 5)
	public void verifySuccessfulPaymentPage() {
	
		carwash.verifySuccessfulPayment();
		carwash.verifyAmount();
	}
	
	
	
	
}