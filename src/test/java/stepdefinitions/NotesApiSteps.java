package stepdefinitions;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class NotesApiSteps {

    // Note ID to share between note creation and retrieval/deletion
    private static String noteId;

    @When("I send a POST request to {string} with the following note details:")
    public void i_send_a_post_request_to_notes(String endpoint, DataTable dataTable) {
        Map<String, String> noteDetails = dataTable.asMaps().get(0);
        AuthApiSteps.response = RestAssured.given()
                .header("x-auth-token", AuthApiSteps.authToken)
                .contentType(ContentType.JSON)
                .body(noteDetails)
                .post(AuthApiSteps.baseUrl + endpoint);
    }

    @Then("I extract the note ID from the response")
    public void i_extract_the_note_id_from_the_response() {
        noteId = AuthApiSteps.response.jsonPath().getString("data.id");
        Assert.assertNotNull(noteId, "Note ID should not be null");
    }

    @Given("I have created a note with title {string}, description {string}, category {string} via API")
    public void i_have_created_a_note(String title, String description, String category) {
        Map<String, String> noteDetails = new HashMap<>();
        noteDetails.put("title", title);
        noteDetails.put("description", description);
        noteDetails.put("category", category);

        AuthApiSteps.response = RestAssured.given()
                .header("x-auth-token", AuthApiSteps.authToken)
                .contentType(ContentType.JSON)
                .body(noteDetails)
                .post(AuthApiSteps.baseUrl + "/notes");

        noteId = AuthApiSteps.response.jsonPath().getString("data.id");
        Assert.assertNotNull(noteId, "Failed to precondition a note creation");
    }

    @When("I send a GET request to {string} for the created note ID")
    public void i_send_a_get_request_for_note_id(String endpoint) {
        AuthApiSteps.response = RestAssured.given()
                .header("x-auth-token", AuthApiSteps.authToken)
                .get(AuthApiSteps.baseUrl + endpoint + "/" + noteId);
    }

    @Then("the API response should contain the note title {string}")
    public void the_api_response_should_contain_the_note_title(String expectedTitle) {
        String actualTitle = AuthApiSteps.response.jsonPath().getString("data.title");
        Assert.assertEquals(actualTitle, expectedTitle, "Note title mismatch!");
    }

    @When("I send a DELETE request to {string} for the created note ID")
    public void i_send_a_delete_request_for_note_id(String endpoint) {
        AuthApiSteps.response = RestAssured.given()
                .header("x-auth-token", AuthApiSteps.authToken)
                .delete(AuthApiSteps.baseUrl + endpoint + "/" + noteId);
    }
}