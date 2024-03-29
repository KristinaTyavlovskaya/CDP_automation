package ui.driver;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Driver {
	private static final String DEFAULT_WEB_DRIVER = "DEFAULT_WEB_DRIVER";

	private static WebDriverTypes defaultDriverType = WebDriverTypes.CHROME;

	private static HashMap<String, org.openqa.selenium.WebDriver> instances;

	static {
		instances = new HashMap<String, WebDriver>();
	}

	public static WebDriver getWebDriverInstance(String name,
			WebDriverTypes type) throws Exception {
		WebDriver driver;
		if (!instances.containsKey(name)) {
			switch (type) {
			case FIREFOX: {
				driver = new FirefoxDriver();
				break;
			}
			case IE: {
				System.setProperty("webdriver.ie.driver",
						"../MailAutomation/ie/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				break;
			}
			case CHROME: {
				System.setProperty("webdriver.chrome.driver",
						"../MailAutomation/chromedriver/chromedriver.exe");
				driver = new ChromeDriver();
				break;
			}
			default:
				throw new Exception("Unknown web driver specified: " + type);
			}
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			instances.put(name, driver);
		} else {
			driver = instances.get(name);
		}
		return driver;
	}

	public static WebDriver getWebDriverInstance(String name) throws Exception {
		return getWebDriverInstance(name, defaultDriverType);
	}

	public static WebDriver getWebDriverInstance() throws Exception {
		return getWebDriverInstance(DEFAULT_WEB_DRIVER, defaultDriverType);
	}

	public static void setDefaultWebDriverType(WebDriverTypes type) {
		defaultDriverType = type;
	}

}
