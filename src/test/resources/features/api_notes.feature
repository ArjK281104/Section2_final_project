Feature: API Notes Management

  Background:
    Given I am an authenticated API user with email "arjun.korde34@gmail.com" and password "Arjun@123"

  Scenario: Add a new note successfully via API
    When I send a POST request to "/notes" with the following note details:
      | title               | description                               | category |
      | Supercapacitor Test | Voltage starting at 17% for functionality | Work     |
    Then the API response status code should be 200
    And I extract the note ID from the response
    And the API response should contain "Note successfully created"

  Scenario: Retrieve an existing note via API
    Given I have created a note with title "Smart Plant Guardian", description "ESP32 moisture sensor project setup", category "Home" via API
    When I send a GET request to "/notes" for the created note ID
    Then the API response status code should be 200
    And the API response should contain the note title "Smart Plant Guardian"

  Scenario: Delete a note successfully via API
    Given I have created a note with title "Temp Note", description "To be deleted", category "Personal" via API
    When I send a DELETE request to "/notes" for the created note ID
    Then the API response status code should be 200
    And the API response should contain "Note successfully deleted"