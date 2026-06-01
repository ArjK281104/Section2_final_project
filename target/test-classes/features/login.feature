Feature: User Login
  As a registered user
  I want to log in to the Notes App
  So that I can manage my personal notes

  Scenario Outline: Successful login with valid credentials
    Given I am on the login page
    When I enter an email "<email>" and password "<password>"
    And I click the login button
    Then I should be successfully logged in

    Examples:
      | email                    | password   |
      | arjun.korde340@gmail.com | Arjun@123  |