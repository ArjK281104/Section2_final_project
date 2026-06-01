package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserSettingsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // --- Locators ---
    private By navProfileLink = By.cssSelector("a[data-testid='profile']");
    private By phoneInputField = By.cssSelector("input[data-testid='user-phone']");
    private By companyInputField = By.cssSelector("input[data-testid='user-company']");
    private By submitUpdateButton = By.cssSelector("button[data-testid='update-profile']");

    // Constructor
    public UserSettingsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Page Actions ---
    public void navigateToSettings() {
        WebElement profileBtn = wait.until(ExpectedConditions.presenceOfElementLocated(navProfileLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", profileBtn);
    }

    public void enterPhoneNumber(String phone) {
        WebElement phoneField = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInputField));
        phoneField.clear();
        phoneField.sendKeys(phone);
    }

    public void enterCompanyName(String company) {
        WebElement companyField = wait.until(ExpectedConditions.visibilityOfElementLocated(companyInputField));
        companyField.clear();
        companyField.sendKeys(company);
    }

    public void clickUpdateSettings() {
        WebElement updateBtn = wait.until(ExpectedConditions.elementToBeClickable(submitUpdateButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", updateBtn);
    }
}