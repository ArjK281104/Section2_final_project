package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // --- Locators ---
    private By profileLink = By.cssSelector("a[data-testid='profile']");
    private By deleteAccountButton = By.cssSelector("button[data-testid='delete-account']");
    
    // Locator for the Confirm Delete button inside the modal
    private By confirmDeleteButton = By.cssSelector("button[data-testid='note-delete-confirm']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Page Actions ---
    public void navigateToProfile() {
        WebElement profileBtn = wait.until(ExpectedConditions.presenceOfElementLocated(profileLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", profileBtn);
    }

    public void clickDeleteAccount() {
        WebElement deleteBtn = wait.until(ExpectedConditions.presenceOfElementLocated(deleteAccountButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn);
    }

    public void confirmDelete() {
        // Wait for the modal's confirm button to be visible, then click it
        WebElement confirmBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmDeleteButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmBtn);
    }
}