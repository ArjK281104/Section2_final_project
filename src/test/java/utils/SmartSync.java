package utils;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SmartSync {

    private final WebDriverWait wait;
    private final JavascriptExecutor jsExecutor;

    // Private constructor to force the use of the fluent entry point
    private SmartSync(WebDriver driver, int timeoutInSeconds) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    /**
     * Entry point for the fluent wait. 
     * Usage: SmartSync.observe(driver)...
     */
    public static SmartSync observe(WebDriver driver) {
        return new SmartSync(driver, 20);
    }

    /**
     * Waits for the HTML document to reach the "complete" ready state.
     */
    public SmartSync untilDomIsReady() {
        wait.until(d -> jsExecutor.executeScript("return document.readyState").equals("complete"));
        return this;
    }

    /**
     * Waits for active jQuery/AJAX network calls to drop to 0.
     */
    public SmartSync untilNetworkIsIdle() {
        try {
            wait.until(d -> (Boolean) jsExecutor.executeScript("return window.jQuery == undefined || jQuery.active == 0"));
        } catch (Exception e) {
            System.out.println("Network sync interrupted or jQuery not present, proceeding cautiously...");
        }
        return this;
    }

    /**
     * Agentic action: Combines both DOM and Network synchronization.
     */
    public void awaitFullStabilization() {
        this.untilDomIsReady().untilNetworkIsIdle();
    }
}