package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class AuthApiSteps {

    // Public static variables so the Notes steps can share this data
    public static String baseUrl = "https://practice.expandtesting.com/notes/api";
    public static Response response;
    public static String authToken;

    @Given("the API base URL is {string}")
    public void the_api_base_url_is(String url) {
        baseUrl = url;
        RestAssured.baseURI = url;
    }

    @When("I send a POST request to {string} with the following details:")
    public void i_send_a_post_request_to_register(String endpoint, DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(data)
                .post(baseUrl + endpoint);
    }

    @When("I send a POST request to {string} with the following credentials:")
    public void i_send_a_post_request_to_login(String endpoint, DataTable dataTable) {
        Map<String, String> credentials = dataTable.asMaps().get(0);
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .post(baseUrl + endpoint);
    }

    @Given("I am an authenticated API user with email {string} and password {string}")
    public void i_am_an_authenticated_api_user(String email, String password) {
        RestAssured.baseURI = baseUrl;
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        // Try to log in first
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .post("/users/login");

        // SMART FIX: If login fails (user was deleted by a previous test), register them and log in again
        if (response.getStatusCode() != 200) {
            Map<String, String> registerDetails = new HashMap<>();
            registerDetails.put("name", "API Test User");
            registerDetails.put("email", email);
            registerDetails.put("password", password);

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(registerDetails)
                    .post("/users/register");

            // Retry the login after registration
            response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(credentials)
                    .post("/users/login");
        }

        authToken = response.jsonPath().getString("data.token");
        Assert.assertNotNull(authToken, "Failed to authenticate user. API Response: " + response.getBody().asString());
    }

    @When("I send a DELETE request to {string}")
    public void i_send_a_delete_request_to(String endpoint) {
        response = RestAssured.given()
                .header("x-auth-token", authToken)
                .delete(baseUrl + endpoint);
    }

    @Then("I extract the auth token from the response")
    public void i_extract_the_auth_token_from_the_response() {
        authToken = response.jsonPath().getString("data.token");
        Assert.assertNotNull(authToken, "Auth token should not be null");
    }

    @Then("the API response status code should be {int}")
    public void the_api_response_status_code_should_be(int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code mismatch!");
    }

    @Then("the API response should contain {string}")
    public void the_api_response_should_contain(String expectedMessage) {
        Assert.assertTrue(response.getBody().asString().contains(expectedMessage), 
                "Response did not contain expected text: " + expectedMessage);
    }
}