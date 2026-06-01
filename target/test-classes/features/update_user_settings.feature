Feature: Update User Settings
  As a logged-in user
  I want to update my account information
  So that my contact and company details are current

  Background:
    Given I am on the login page
    When I enter an email "arjun.korde340@gmail.com" and password "Arjun@123"
    And I click the login button
    Then I should be successfully logged in

  Scenario: Successfully update phone number and company name
    When I navigate to the User Settings page
    And I enter new phone number "9876543210" and new company name "Expand Testing"
    And I click the Update Profile settings button
    Then my user settings should be updated successfully