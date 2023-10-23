package utility;


import java.time.Duration;
import java.time.Instant;

import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import carwash.base.BaseClass;

import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.FluentWait;

public class Utility extends BaseClass {
	WebDriver driver;

	public Utility(WebDriver driver) {
		this.driver = driver;
	}

	public void waitUntillVisibilityof(WebElement el) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(getByFromWebElement(el)));
		  }

	private By getByFromWebElement(WebElement element) {
		String elementAsString = element.toString();
		String locatorString = elementAsString.substring(elementAsString.indexOf("-> ") + 3);
		return By.xpath(locatorString);
	}

	public void waitForVisibilityOfElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementToBeClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForPresenceOfElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		By locator = getByFromWebElement(element);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void waitForElement(WebDriver driver, WebElement element, int timeoutInSeconds) {
		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeoutInSeconds))
				.pollingEvery(Duration.ofMillis(500)) // Adjust the polling interval as needed
				.ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public String timeStamp() {
		Instant timestamp = Instant.now();

		// Define a custom timestamp format pattern
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy,MM,dd HH.mm.ss.SSS");

		// Format the timestamp as a string
		String timestampString = timestamp.atZone(java.time.ZoneId.systemDefault()).format(formatter);

		return timestampString;

	}

	/*
	 * public static void main(String[] args) { System.out.println(timeStamp()); }
	 */
	public String randomString() {
		String randomNumber = "s" + Math.random();
		return randomNumber;

	}

	public void waitForUrlToLoad(String url) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.urlToBe(url));
	}

	public void waitForTitle(String title) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleContains(title));
	}

	public void waitForElementWithFrequency(WebElement element, int timeoutInSeconds, int freq) {
		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeoutInSeconds))
				.pollingEvery(Duration.ofSeconds(freq)) // Adjust the polling interval as needed
				.ignoring(NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOf(element));

	}

}
