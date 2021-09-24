package stepDefinitions;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import utilities.Base;

public class ScenarioHooks {
	WebDriver webDriver = Base.getWebDriver();
	
	@After
	public void scenarioAfter() {
		webDriver.quit();
	}
}