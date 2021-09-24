package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_Login {
	WebDriver webDriver;

	public Page_Login(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	// ==================================================|Page_Objects|==================================================
	@FindBy(xpath = "//button[@type = 'submit']")
	public WebElement button_Login;

	@FindBy(xpath = "//input[@name = 'password']")
	public WebElement input_Password;

	@FindBy(xpath = "//input[@name = 'email']")
	public WebElement input_Email;
}