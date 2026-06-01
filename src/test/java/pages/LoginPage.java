package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By emailInput = By.id("email"); 
    private By passwordInput = By.id("password"); 
    private By loginButton = By.xpath("//button[@type='submit']"); 
    
    private By logoutButton = By.cssSelector("button[data-testid='logout']");
    private By addNoteButton = By.cssSelector("button[data-testid='add-new-note']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToLogin() {
        driver.get("https://practice.expandtesting.com/notes/app/login"); 
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(loginButton));
        // Use JavascriptExecutor to bypass overlapping ads or footers
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public boolean isLogoutButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton)).isDisplayed();
    }
    
    public boolean isAddNoteButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(addNoteButton)).isDisplayed();
    }
}