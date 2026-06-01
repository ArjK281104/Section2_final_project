package stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.RegisterPage;
import utils.DriverFactory;
import utils.SmartSync;

public class RegisterSteps {
    
    // Fetch the driver initialized by Hooks.java
    WebDriver driver = DriverFactory.getDriver();
    RegisterPage registerPage = new RegisterPage(driver);

    @Given("I am on the registration page")
    public void i_am_on_the_registration_page() {
        registerPage.navigateToRegister();
        // Agentic Wait
        SmartSync.observe(driver).awaitFullStabilization();
    }

    @When("I enter name {string}, email {string}, password {string} and confirm password {string}")
    public void i_enter_registration_details(String name, String email, String password, String confirmPassword) {
        registerPage.userenterName(name);
        registerPage.userenterEmail(email);
        registerPage.userenterPassword(password);
        registerPage.userenterConfirmPassword(confirmPassword);
    }

    @When("I click the register button")
    public void i_click_the_register_button() {
        registerPage.clickRegisterButton();
        // Agentic Wait: Wait for registration API call to complete
        SmartSync.observe(driver).awaitFullStabilization();
    }

    @Then("I should be successfully registered")
    public void i_should_be_successfully_registered() {
        // Assert that the success banner is visible on the screen
        Assert.assertTrue(registerPage.isSuccessMessageDisplayed(), 
                "The registration success message was not displayed!");
    }
}