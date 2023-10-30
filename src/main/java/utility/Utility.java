package utility;


import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BaseClass;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.FluentWait;

/**
 * Utility class providing a collection of common utility methods.
 * 
 * This class contains a variety of utility methods that can be used across the
 * application for performing common tasks, such as differet kind of waits, date
 * or time stamp, string processing, mathematical operations, and more.
 * 
 * @author Mayur takalikar
 * 
 *         Usage: 1. Utilize utility methods in this class for common utility
 *         tasks. 2. Create an instance of Utility class to access the utility
 *         common methods.
 * 
 * 
 */
public class Utility extends BaseClass {
	 WebDriver driver;

	public Utility(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Converts a WebElement into a By locator object.
	 * 
	 * @param 
	 * @return
	 */
	private By getByFromWebElement(WebElement element) {
		String elementAsString = element.toString();
		String locatorString = elementAsString.substring(elementAsString.indexOf("-> ") + 3);
		return By.xpath(locatorString);
	}

	/**
	 * Waits for the visibility of a WebElement on the web page
	 * 
	 * @param 
	 */
	public void waitForVisibilityOfElement(WebElement element) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Waits for a WebElement to become clickable on the web page.
	 *
	 * @param element 
	 */
	public void waitForElementToBeClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Waits for a WebElement to become clickable on the web page, which means it is
	 * both visible and enabled, allowing it to receive user interactions like
	 * clicks.
	 *
	 * @param element
	 */
	public void waitForPresenceOfElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		By locator = getByFromWebElement(element);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * Waits for the visibility of a WebElement on the web page with a custom
	 * timeout.
	 *
	 * @param element          
	 * @param timeoutInSeconds 
	 * 
	 */
	public void waitForElement(WebElement element, int timeoutInSeconds) {
		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeoutInSeconds))
				.pollingEvery(Duration.ofMillis(500))
				.ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOf(element));

	}

	
	

	/**
	 * Generates a random string by appending a random number to the prefix "screenShot".
	 *
	 * @return 
	 */
	public String randomString() {
		String randomNumber = "ScreenShot" + Math.random();
		return randomNumber;

	}
	
	/**
	 * Waits for the web page URL to match the expected URL.
	 *
	 * @param driver     
	 * @param expectedUrl 
	 */
	public void waitForUrlToLoad(String expectedUrl) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.urlToBe(expectedUrl));

	}

	/**
	 * Waits for the web page title to contain the specified text.
	 *
	 * @param title 
	 */
	public void waitForTitle(String title) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleContains(title));
	}
	
	
	/**
	 * Waits for the visibility of a WebElement on the web page with a custom timeout and polling frequency.
	 * 
	 * @param element
	 * @param timeoutInSeconds
	 * @param freq
	 */

	public void waitForElementWithFrequency(WebElement element, int timeoutInSeconds, int freq) {
		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeoutInSeconds))
				.pollingEvery(Duration.ofSeconds(freq)) // Adjust the polling interval as needed
				.ignoring(NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOf(element));

	}

}
