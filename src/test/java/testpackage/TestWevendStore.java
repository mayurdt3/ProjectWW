package testpackage;

import org.testng.annotations.Test;

import weVendStore.base.WevendStore;
/**
 * Test class for a 'WeVend Store' application.
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


public class TestWevendStore extends BaseTest{
	
	
	@Test(priority =0)
	public void VerifyWevendLaunch() {
		wevend =new WevendStore(driver);
		wevend.launchWevendStore();
		
	}
	@Test(priority =1)
	public void VerifyAddTocart() {
		
		wevend.AddProductstoCart("Mountain Dew","Diet Coke","Water");
		wevend.VerifyProductInCart();
	}
	
	@Test(priority =2)
	public void VerifyCheckoutPage() {
		
		wevend.NavigateToCheckoutPage();
		wevend.checkOrderTotal();
		
	}
	
	@Test (priority =3)
	public void VerifyPaymentgatway() {
		
		wevend.navigateToPaymentgatway();
		wevend.verifyEntercardDetails();	
	}
	
	@Test(priority = 4)
	public void VerifySuccessPage() {
		
		wevend.navigatToSuccessPage();
		wevend.verifyGrandTotalWithOrderTotal();
		wevend.verifyBuyMoreFunctionality();
		
		
	}
	
	
	
}
