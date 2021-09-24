package utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Keywords {
	WebDriver webDriver;

	public Keywords(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	/**
	 * Submit a Rest API Post Request and will return a response output. If
	 * requestBody is not needed just leave the parameter as blank a string.
	 * 
	 * @param strRequestURL  - Set Rest request URL.
	 * @param strRequestBody - Set Json body as string.
	 * @return Response type as output.
	 */
	public Response restPost(String strRequestURL, HashMap<String, String> requestHeader, String strRequestBody) {
		RequestSpecification request = RestAssured.given();

		if (!requestHeader.isEmpty()) {
			for (HashMap.Entry<String, String> entry : requestHeader.entrySet()) {
				request.header(entry.getKey(), entry.getValue());
			}
		}

		request.body(strRequestBody);

		Response response = request.post(strRequestURL);

		return response;
	}

	/**
	 * Gets the current date and time and returns it depending on the given format.
	 * 
	 * @param strFormat - the format of the date and time string. Example is
	 *                  "_ddMMyy_HHmm".
	 * @return Returns the formatted current date and time.
	 */
	public String getStringDateTimeNow(String strFormat) {
		String strFormattedDateTimeNow = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
		strFormattedDateTimeNow = simpleDateFormat.format(calendar.getTime());

		return strFormattedDateTimeNow;
	}

	/**
	 * Send keyboard inputs on the focused element or frame.
	 * 
	 * @param strKeys - keys/characters to be sent.
	 */
	public void actionSendKeys(String strKeys) {
		Actions actions = new Actions(webDriver);
		actions.sendKeys(strKeys);
	}

	/**
	 * Navigates to the provided URL.
	 * 
	 * @param strURL - web page URL to navigate to.
	 */
	public void navigateToUrl(String strURL) {
		webDriver.navigate().to(strURL);
	}

	/**
	 * Switch frame by web element.
	 * 
	 * @param webElement - web element to find.
	 */
	public void switchFrameByWebElement(WebElement webElement) throws Exception {
		Integer intNumberOfFrames = webDriver.findElements(By.tagName("iframe")).size();

		for (int i = 0; i <= intNumberOfFrames; i++) {
			webDriver.switchTo().frame(i);

			if (isWebElementDisplayed(webElement))
				break;

			webDriver.switchTo().defaultContent();
		}
	}

	/**
	 * Switches the focus to the parent frame.
	 */
	public void switchToParentFrame() {
		webDriver.switchTo().parentFrame();
	}

	/**
	 * Waits for the web element to be visible then clicks the web element.
	 * 
	 * @param webElement - web element to perform the action to.
	 */
	public void webElementClick(WebElement webElement) throws Exception {
		waitUntilWebElementVisible(webElement);

		if (isWebElementDisplayed(webElement) && isWebElementEnabled(webElement)) {
			webElement.click();
		}
	}

	/**
	 * Waits for the web element to be visible then sends the keys/characters to the
	 * web element.
	 * 
	 * @param webElement - web element to perform the action to.
	 * @param strKeys    - keys/characters to be sent.
	 */
	public void webElementSendKeys(WebElement webElement, String strKeys) throws Exception {
		waitUntilWebElementVisible(webElement);

		if (isWebElementDisplayed(webElement) && isWebElementEnabled(webElement)) {
			// webElement.sendKeys(strKeys);

			for (int i = 0; i < strKeys.length(); i++) {
				webElement.sendKeys(strKeys.substring(i, i + 1));
				// wait(50, false);
			}
		}
	}

	/**
	 * Checks if the web element is displayed or not.
	 * 
	 * @param webElement - web element to check.
	 * @return True if the options are displayed, false otherwise.
	 * @implNote Mostly used in Asserts.assertTrue, or in IF ELSE conditions.
	 */
	public boolean isWebElementDisplayed(WebElement webElement) throws Exception {
		try {
			return webElement.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Checks if the web element is enabled or not.
	 * 
	 * @param webElement - web element to check.
	 * @return True if the options are displayed, false otherwise.
	 * @implNote Mostly used in Asserts.assertTrue, or in IF ELSE conditions.
	 */
	public boolean isWebElementEnabled(WebElement webElement) throws Exception {
		try {
			return webElement.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Gets the web element attribute value.
	 * 
	 * @param webElement   - web element to check.
	 * @param strAttribute - attribute of the web element to get the value from.
	 * @return Returns the value of the web element attribute.
	 * @implNote Mostly used in Asserts.assertEquals, or use in IF ELSE conditions.
	 */
	public String getWebElementAttributeValue(WebElement webElement, String strAttribute) {
		return webElement.getAttribute(strAttribute).toString();
	}

	/**
	 * Gets the web element text.
	 * 
	 * @param webElement - web element to check.
	 * @return Returns the text of the web element.
	 * @implNote Mostly used in Asserts, or use in IF ELSE conditions.
	 */
	public String getWebElementText(WebElement webElement) {
		return webElement.getText();
	}

	// ==================================================|Wait|==================================================
	/**
	 * Wait until the time duration runs out.
	 * 
	 * @param intDuration  - number of minutes or milliseconds.
	 * @param blnIsMinutes - flag if duration is in minutes or in milliseconds.
	 * @implNote Mostly used in special conditions. Avoid using at all cost.
	 */
	public void wait(Integer intDuration, Boolean blnIsMinutes) throws Exception {
		if (blnIsMinutes) {
			intDuration = intDuration * 60000;
		}

		Thread.sleep(intDuration);
	}

	/**
	 * Wait until a browser alert popup is present.
	 */
	public void waitUntilAlertPresent() {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.alertIsPresent());
	}

	/**
	 * Wait until text is present in the web element.
	 * 
	 * @param webElement - web element to check.
	 * @param strText    - text to expect in the web element.
	 */
	public void waitUntilTextPresentInWebElement(WebElement webElement, String strText) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.textToBePresentInElement(webElement, strText));
	}

	/**
	 * Wait until web browser title contains the expected string.
	 * 
	 * @param strTitle - string to be expected.
	 */
	public void waitUntilWebBrowserTitleContains(String strTitle) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.titleContains(strTitle));
	}

	/**
	 * Wait until web element attribute value contains the expected value.
	 * 
	 * @param webElement   - web element to check.
	 * @param strAttribute - attribute of the web element to check.
	 * @param strValue     - expected attribute value.
	 */
	public void waitUntilWebElementAttributeValueContains(WebElement webElement, String strAttribute, String strValue) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.attributeContains(webElement, strAttribute, strValue));
	}

	/**
	 * Wait until web element attribute value does not contain the expected value.
	 * 
	 * @param webElement   - web element to check.
	 * @param strAttribute - attribute of the web element to check.
	 * @param strValue     - value to expect not in the attribute.
	 */
	public void waitUntilWebElementAttributeValueDoesNotContains(WebElement webElement, String strAttribute,
			String strValue) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(
				ExpectedConditions.not(ExpectedConditions.attributeContains(webElement, strAttribute, strValue)));
	}

	/**
	 * Wait until web element can be clicked.
	 * 
	 * @param webElement - web element to check.
	 */
	public void waitUntilWebElementClickable(WebElement webElement) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
	}

	/**
	 * Wait until web element is invisible.
	 * 
	 * @param webElement - web element to check.
	 */
	public void waitUntilWebElementInvisible(WebElement webElement) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.invisibilityOf(webElement));
	}

	/**
	 * Wait until web element is not existing in the page.
	 * 
	 * @param strWebElementXPath - web element XPath to check.
	 */
	public void waitUntilWebElementXPathNotExisting(String strWebElementXPath) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(strWebElementXPath)));
	}

	/**
	 * Wait until web element is selected or not.
	 * 
	 * @param webElement    - web element to check.
	 * @param blnIsSelected - selection state of the web element.
	 */
	public void waitUntilWebElementSelectionStateToBe(WebElement webElement, Boolean blnIsSelected) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.elementSelectionStateToBe(webElement, blnIsSelected));
	}

	/**
	 * Wait until web element is visible.
	 * 
	 * @param webElement - web element to check.
	 */
	public void waitUntilWebElementVisible(WebElement webElement) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
	}
}