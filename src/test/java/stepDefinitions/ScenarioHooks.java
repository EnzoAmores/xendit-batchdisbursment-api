package stepDefinitions;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import utilities.Base;

public class ScenarioHooks {
	WebDriver webDriver = Base.getWebDriver();

	// ==================================================|Hooks|==================================================
	@After
	public void scenarioAfter() {
		quitBrowser();
	}

	// ==================================================|Helpers|==================================================

	private void quitBrowser() {
		if (webDriver != null)
			webDriver.quit();
	}
}