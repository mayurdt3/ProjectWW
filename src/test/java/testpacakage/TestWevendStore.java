package testpacakage;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import scripts.CarwashScript;
import scripts.WeVendScript;

public class TestWevendStore extends BaseTest{
	WeVendScript wevendobject =new WeVendScript(driver);
	
	@Test(priority =0)
	public void VerifyWevendLaunch() {
		wevendobject.launchwevend();
		
	}
	
	@Test(priority =1)
	public void VerifyAddtoCart() {
	wevendobject.addnumberofproductsTocart(3);
	}
}
