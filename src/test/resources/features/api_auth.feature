Feature: API Authentication and User Management

  Scenario: Register a new user via API
    Given the API base URL is "https://practice.expandtesting.com/notes/api"
    When I send a POST request to "/users/register" with the following details:
      | name        | email                           | password  |
      | Arjun Korde | arjun.korde.api@test.com        | Test@1234 |
    Then the API response status code should be 201
    And the API response should contain "User account created successfully"

  Scenario: Login successfully via API
    Given the API base URL is "https://practice.expandtesting.com/notes/api"
    When I send a POST request to "/users/login" with the following credentials:
      | email                    | password  |
      | arjun.korde.api@test.com | Test@1234 |
    Then the API response status code should be 200
    And I extract the auth token from the response

  Scenario: Delete user account successfully via API
    Given I am an authenticated API user with email "arjun.korde.api@test.com" and password "Test@1234"
    When I send a DELETE request to "/users/delete-account"
    Then the API response status code should be 200
    And the API response should contain "Account successfully deleted"