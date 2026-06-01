Feature: Add Note
  As a logged-in user
  I want to add a new note
  So that I can save my thoughts and tasks

  Background:
    Given I am on the login page
    When I enter an email "arjun.korde340@gmail.com" and password "Arjun@123"
    And I click the login button
    Then I should be successfully logged in

  Scenario Outline: Successfully add a new note
    When I click the Add Note button
    And I select the category "<category>"
    And I enter the title "<title>" and description "<description>"
    And I check the completed checkbox
    And I click the Create Note button
    Then the note modal should be closed successfully

    Examples:
      | category | title                 | description                                |
      | Personal | Capstone Automation   | Automating the Notes App using Selenium.   |
      | Work     | Meeting Notes         | Discuss sprint planning and CI/CD setup.   |