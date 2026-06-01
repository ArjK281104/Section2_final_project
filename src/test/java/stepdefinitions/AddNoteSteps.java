package stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.DashboardPage;
import utils.DriverFactory;
import utils.SmartSync;

public class AddNoteSteps {
    
    // Fetch the driver initialized by Hooks.java
    WebDriver driver = DriverFactory.getDriver();
    DashboardPage dashboardPage = new DashboardPage(driver);

    @When("I click the Add Note button")
    public void i_click_the_add_note_button() {
        dashboardPage.clickAddNoteButton();
        // Agentic Wait: Wait for the modal animation/DOM to load
        SmartSync.observe(driver).untilDomIsReady(); 
    }

    @And("I select the category {string}")
    public void i_select_the_category(String category) {
        dashboardPage.selectCategory(category);
    }

    @And("I enter the title {string} and description {string}")
    public void i_enter_the_title_and_description(String title, String description) {
        dashboardPage.enterTitle(title);
        dashboardPage.enterDescription(description);
    }

    @And("I check the completed checkbox")
    public void i_check_the_completed_checkbox() {
        dashboardPage.checkCompleted();
    }

    @And("I click the Create Note button")
    public void i_click_the_create_note_button() {
        dashboardPage.clickCreateButton();
        // Agentic Wait: Wait for note creation API call and UI update
        SmartSync.observe(driver).awaitFullStabilization();
    }

    @Then("the note modal should be closed successfully")
    public void the_note_modal_should_be_closed_successfully() {
        // Assert that the modal is no longer visible, meaning creation was successful
        Assert.assertTrue(dashboardPage.isModalClosed(), 
                "The note modal did not close, the note might not have been created.");
    }
}