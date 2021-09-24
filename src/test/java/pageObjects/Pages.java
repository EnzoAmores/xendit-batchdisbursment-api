package pageObjects;

import org.openqa.selenium.WebDriver;

public class Pages {
	WebDriver webDriver;

	public Pages(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	// ==================================================|Initialize|==================================================
	private Page_General page_General;
	private Page_Login page_Login;

	// ==================================================|Return|==================================================
	public Page_General getPageGeneral() {
		return (page_General == null) ? page_General = new Page_General(webDriver) : page_General;
	}

	public Page_Login getPageLogin() {
		return (page_Login == null) ? page_Login = new Page_Login(webDriver) : page_Login;
	}
}