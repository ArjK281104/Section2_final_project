package stepdefinitions;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.JavascriptExecutor;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pages.LoginPage;
import utils.DriverFactory;
import java.time.Duration;

public class ComprehensiveHybridSteps {

    // Webdriver and Pages
    private WebDriver driver = DriverFactory.getDriver();
    private LoginPage loginPage = new LoginPage(driver);
    
    // API State Variables shared across this specific scenario
    private String apiToken;
    private String hybridNoteId;
    private final String BASE_URL = "https://practice.expandtesting.com/notes/api"; // Update if your API base URL is different

    // ==========================================
    // API STEPS
    // ==========================================

    @Given("I authenticate via API with email {string} and password {string}")
    public void i_authenticate_via_api(String email, String password) {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .post(BASE_URL + "/users/login");

        Assert.assertEquals(response.getStatusCode(), 200, "API Login failed");
        apiToken = response.jsonPath().getString("data.token");
    }

    @Given("I create a note via API with title {string}, description {string}, category {string}")
    public void i_create_a_note_via_api(String title, String description, String category) {
        Map<String, String> notePayload = new HashMap<>();
        notePayload.put("title", title);
        notePayload.put("description", description);
        notePayload.put("category", category);

        Response response = RestAssured.given()
                .header("x-auth-token", apiToken)
                .contentType(ContentType.JSON)
                .body(notePayload)
                .post(BASE_URL + "/notes");

        Assert.assertEquals(response.getStatusCode(), 200, "Failed to create note via API");
        hybridNoteId = response.jsonPath().getString("data.id");
        Assert.assertNotNull(hybridNoteId, "Note ID was not generated");
    }

    @Then("I verify via API that the created note no longer exists")
    public void i_verify_via_api_note_deleted() {
        Response response = RestAssured.given()
                .header("x-auth-token", apiToken)
                .get(BASE_URL + "/notes/" + hybridNoteId);

        // Expecting a 400 or 404 because the note was deleted via the UI
        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 404, 
            "Expected note to be deleted (404/400 status), but got: " + statusCode);
    }

    // ==========================================
    // UI STEPS
    // ==========================================

   @When("I navigate to the application and log in with email {string} and password {string}")
    public void i_navigate_and_log_in_ui(String email, String password) {
        // Assuming baseUrl is handled in properties, but we navigate directly to login here
        driver.get("https://practice.expandtesting.com/notes/app/login");
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton(); // <--- Changed to match the method in LoginPage.java
    }

    @Then("I should see the note titled {string} on the UI dashboard")
    public void i_should_see_note_on_ui(String expectedTitle) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String xpath = "//*[contains(text(), '" + expectedTitle + "')]";
        
        WebElement noteElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        Assert.assertTrue(noteElement.isDisplayed(), "Note title was not found on the UI dashboard!");
    }

   @When("I delete the note titled {string} via the UI")
    public void i_delete_the_note_via_ui(String title) {
        // Standard XPath to find a delete button relative to the note's title
        String deleteBtnXpath = "//*[contains(text(), '" + title + "')]/ancestor::div[contains(@class, 'card')]//button[contains(text(), 'Delete')]";
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement deleteButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(deleteBtnXpath)));
        
        // 1. Use JavascriptExecutor to bypass any overlapping ads, footers, or toasts
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        // Optional: Scroll the element into view first
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", deleteButton);
        
        // Click the delete button on the card
        js.executeScript("arguments[0].click();", deleteButton);
        
        // 2. Handle the confirmation modal that pops up
        try {
            // Wait for the confirmation 'Delete' button in the modal to appear
            WebElement confirmDelete = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='modal-content']//button[text()='Delete']")));
            // Click it using JS to prevent further interception issues
            js.executeScript("arguments[0].click();", confirmDelete);
            
            // Give the UI a brief moment to process the deletion before moving to the API verification
            Thread.sleep(1000); 
        } catch (Exception e) {
            System.out.println("Confirmation modal did not appear or could not be clicked.");
        }
    }
}