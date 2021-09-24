package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_General {
	WebDriver webDriver;

	public Page_General(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	// ==================================================|Page_Objects|==================================================
	@FindBy(xpath = "//i[contains(@class, 'sign-out')]/parent::a")
	public WebElement button_Logout;

	@FindBy(xpath = "//div[@class='user-info']//span[@data-expression]")
	public WebElement label_Username;
}