package stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utils.DriverFactory;
import utils.SmartSync;

public class LoginSteps {
    
    // Fetch the driver initialized by Hooks.java
    WebDriver driver = DriverFactory.getDriver();
    LoginPage loginPage = new LoginPage(driver);

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        loginPage.navigateToLogin();
        // Agentic Wait: Wait for page load
        SmartSync.observe(driver).awaitFullStabilization();
    }

    @When("I enter an email {string} and password {string}")
    public void i_enter_an_email_and_password(String email, String password) {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        loginPage.clickLoginButton();
        // Agentic Wait: Wait for authentication network calls and dashboard load
        SmartSync.observe(driver).awaitFullStabilization();
    }

    @Then("I should be successfully logged in")
    public void i_should_be_successfully_logged_in() {
        // Asserting that post-login elements from the DOM are visible
        Assert.assertTrue(loginPage.isLogoutButtonDisplayed(), 
                "The Logout button is not displayed. Login may have failed.");
                
        Assert.assertTrue(loginPage.isAddNoteButtonDisplayed(), 
                "The '+ Add Note' button is not displayed.");
    }
}