package utilities;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {
	// ==================================================|WebDriver|==================================================
	protected static ThreadLocal<WebDriver> threadLocalWebDriver = new ThreadLocal<>();

	public static synchronized WebDriver getWebDriver() {
		return threadLocalWebDriver.get();
	}

	public static WebDriver initializeWebDriver(String strBrowser) {
		WebDriver webDriver = null;

		switch (strBrowser.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();

			ChromeOptions chromeOptions = new ChromeOptions();

			if (configurationVariables("browserHeadless").equals("true"))
				chromeOptions.setHeadless(true);

			threadLocalWebDriver.set(new ChromeDriver(chromeOptions));

			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();

			FirefoxOptions firefoxOptions = new FirefoxOptions();

			if (configurationVariables("browserHeadless").equals("true"))
				firefoxOptions.setHeadless(true);

			threadLocalWebDriver.set(new FirefoxDriver(firefoxOptions));

			break;
		}

		webDriver = getWebDriver();

		webDriver.manage().window().setSize(new Dimension(Integer.parseInt(Base.configurationVariables("browserWidth")),
				Integer.parseInt(Base.configurationVariables("browserHeight"))));

		return webDriver;
	}

	// ==================================================|Properties_File|==================================================
	private static void setPropertiesVariableValue(String strPropertiesFilePath, String strVariableName,
			String strVariableValue) {
		PropertiesConfiguration propertiesConfiguration = null;
		Boolean savePropertiesFile = true;

		try {
			propertiesConfiguration = new PropertiesConfiguration(strPropertiesFilePath);

			propertiesConfiguration.setProperty(strVariableName, strVariableValue);
		} catch (ConfigurationException configurationException) {
			configurationException.printStackTrace();
			savePropertiesFile = false;
		}

		try {
			if (savePropertiesFile) {
				propertiesConfiguration.save();
			}
		} catch (ConfigurationException configurationException) {
			configurationException.printStackTrace();
		}
	}

	private static String getPropertiesVariableValue(String strPropertiesFilePath, String strVariableName) {
		String strVariableValue = "";

		try {
			PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration(strPropertiesFilePath);
			strVariableValue = propertiesConfiguration.getProperty(strVariableName).toString();
		} catch (ConfigurationException configurationException) {
			configurationException.printStackTrace();
		}

		return strVariableValue;
	}

	public static String configurationVariables(String strVariableName) {
		String strPropertiesFilePath = System.getProperty("user.dir") + "/src/test/resources/configuration.properties";

		return getPropertiesVariableValue(strPropertiesFilePath, strVariableName);
	}
}