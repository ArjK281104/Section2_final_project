package utils;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver initDriver() {
        try {
            ChromeOptions options = new ChromeOptions();
            // Optional: Run headlessly if you want them in the background
            // options.addArguments("--headless"); 
            options.addArguments("--start-maximized");
            
            // The default URL where Selenium Grid standalone runs
            URL gridUrl = new URL("http://localhost:4444");
            
            // Initialize RemoteWebDriver instead of local ChromeDriver
            driver.set(new RemoteWebDriver(gridUrl, options));
            
        } catch (Exception e) {
            System.out.println("Could not initialize RemoteWebDriver for Grid: " + e.getMessage());
            e.printStackTrace();
        }
        return getDriver();
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            return initDriver();
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // Prevents memory leaks in ThreadLocal
        }
    }
}