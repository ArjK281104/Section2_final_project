package stepdefinitions;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.ProfilePage;
import utils.DriverFactory;

public class DeleteAccountSteps {

    WebDriver driver = DriverFactory.getDriver();
    ProfilePage profilePage = new ProfilePage(driver);

    @When("I navigate to the Profile page")
    public void i_navigate_to_the_profile_page() {
        profilePage.navigateToProfile();
    }

    @And("I click the Delete Account button")
    public void i_click_the_delete_account_button() {
        profilePage.clickDeleteAccount();
    }

    @And("I confirm the deletion in the modal")
    public void i_confirm_the_deletion_in_the_modal() {
        profilePage.confirmDelete();
    }

    @Then("my account should be deleted and I should be redirected to the login page")
    public void my_account_should_be_deleted_and_redirected() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Verify that the application kicks the user out to the login screen
        wait.until(ExpectedConditions.urlContains("login"));
        
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("login"), 
                "Account deletion may have failed. Expected to be on the login page but was on: " + currentUrl);
    }
}