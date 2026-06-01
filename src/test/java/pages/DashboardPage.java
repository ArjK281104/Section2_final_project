package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // --- Locators ---
    private By addNoteButton = By.cssSelector("button[data-testid='add-new-note']");
    
    // Modal Locators
    private By categorySelect = By.cssSelector("select[data-testid='note-category']");
    private By completedCheckbox = By.cssSelector("input[data-testid='note-completed']");
    private By titleInput = By.cssSelector("input[data-testid='note-title']");
    private By descriptionInput = By.cssSelector("textarea[data-testid='note-description']");
    private By createButton = By.cssSelector("button[data-testid='note-submit']");

    // Constructor
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Page Actions ---
    public void clickAddNoteButton() {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(addNoteButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public void selectCategory(String categoryText) {
        WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(categorySelect));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(categoryText);
    }

    public void enterTitle(String title) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(titleInput)).clear();
        driver.findElement(titleInput).sendKeys(title);
    }

    public void enterDescription(String description) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(descriptionInput)).clear();
        driver.findElement(descriptionInput).sendKeys(description);
    }

    public void checkCompleted() {
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(completedCheckbox));
        if (!checkbox.isSelected()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        }
    }

    public void clickCreateButton() {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(createButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }
    
    // --- Verification Methods ---
    public boolean isModalClosed() {
        // Wait until the modal disappears from the screen
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(createButton));
    }
}