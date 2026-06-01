package base;

import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public class BaseTest {
    public static WebDriver driver;

    public void initDriver() {
        // Fetch the RemoteWebDriver from the factory instead of a local ChromeDriver
        driver = DriverFactory.initDriver();
        
        if (driver != null) {
            driver.manage().window().maximize();
        }
    }

    public void quitDriver() {
        // Delegate teardown to the factory to properly clear the ThreadLocal instance
        DriverFactory.quitDriver();
    }
}