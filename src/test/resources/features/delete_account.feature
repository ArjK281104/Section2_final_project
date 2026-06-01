Feature: Delete Account
  As a logged-in user
  I want to delete my profile
  So that my personal data is permanently removed from the application

  Background:
    Given I am on the login page
    # Ensure you use an account specifically meant for deletion testing
    When I enter an email "arjun.test14588@gmail.com" and password "Arjun@123  "
    And I click the login button
    Then I should be successfully logged in

  Scenario: Successfully delete user account
    When I navigate to the Profile page
    And I click the Delete Account button
    And I confirm the deletion in the modal
    Then my account should be deleted and I should be redirected to the login page