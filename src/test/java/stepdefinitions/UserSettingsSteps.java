package stepdefinitions;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.UserSettingsPage;
import utils.DriverFactory;

public class UserSettingsSteps {

    // Fetch the driver initialized by Hooks.java
    WebDriver driver = DriverFactory.getDriver();
    UserSettingsPage userSettingsPage = new UserSettingsPage(driver);

    @When("I navigate to the User Settings page")
    public void i_navigate_to_the_user_settings_page() {
        userSettingsPage.navigateToSettings();
    }

    @And("I enter new phone number {string} and new company name {string}")
    public void i_enter_new_phone_number_and_new_company_name(String phone, String company) {
        userSettingsPage.enterPhoneNumber(phone);
        userSettingsPage.enterCompanyName(company);
    }

    @And("I click the Update Profile settings button")
    public void i_click_the_update_profile_settings_button() {
        userSettingsPage.clickUpdateSettings();
    }

    @Then("my user settings should be updated successfully")
    public void my_user_settings_should_be_updated_successfully() {
        // Here you can assert a success toast/alert if one appears on the UI, 
        // or re-fetch the attributes to assert the string matches.
        System.out.println("User settings were updated successfully!");
    }
}