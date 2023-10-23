package scripts;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseClass;
import com.aventstack.extentreports.Status;


import configuration.ConfigFileReader;
import utility.Utility;

import weVendStore.base.WevendStore;

public class WeVendScript extends BaseClass{

	//@Test 
	public WeVendScript(WebDriver driver) {
		this.driver = driver;
		prop = new ConfigFileReader(driver);
		util = new Utility(driver);
		wvStore = new WevendStore(driver);
		PageFactory.initElements(driver, this);
	}
	@Test (priority =0)
	public void launchwevend() {
		test = extent.createTest("Verify Wevemd store payment cycle");
		driver.get(prop.getProperty("wevendStoreUrl"));
		String pageTitle = driver.getTitle();
		if(pageTitle.equals("WeVend Store")) {
			test.log(Status.INFO, "Application launched sucessfully ");
					}
		else {
			test.log(Status.FAIL, "Application not launched sucessfully ");		
		}	
		Assert.assertTrue(pageTitle.equals(prop.getProperty("wevendStoreUrl")));
	}
	
	
	
	@Test (priority =1)
	public void addnumberofproductsTocart(int count) {
		
		while(count>=0)
		wvStore.AddRandomProductToCart();
		count--;
	}
	
	
	
}
