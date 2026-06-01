package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // --- Locators ---
    private By nameInput = By.cssSelector("input[data-testid='register-name']");
    private By emailInput = By.cssSelector("input[data-testid='register-email']");
    private By passwordInput = By.cssSelector("input[data-testid='register-password']");
    private By confirmPasswordInput = By.cssSelector("input[data-testid='register-confirm-password']");
    private By registerButton = By.cssSelector("button[data-testid='register-submit']");
    
    // Locator for the success message shown after registration
    private By successAlert = By.cssSelector(".alert.alert-success");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Page Actions ---
    public void navigateToRegister() {
        driver.get("https://practice.expandtesting.com/notes/app/register");
    }

    public void userenterName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).clear();
        driver.findElement(nameInput).sendKeys(name);
    }

    public void userenterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void userenterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void userenterConfirmPassword(String confirmPassword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordInput)).clear();
        driver.findElement(confirmPasswordInput).sendKeys(confirmPassword);
    }

    public void clickRegisterButton() {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(registerButton));
        // Use JavascriptExecutor to bypass overlapping ads or footers
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }
    
    // --- Verification Methods ---
    public boolean isSuccessMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successAlert)).isDisplayed();
    }
}