Feature: User Registration
  As a new user
  I want to register an account in the Notes App
  So that I can create and manage my personal notes

  Scenario Outline: Successful user registration
    Given I am on the registration page
    When I enter name "<name>", email "<email>", password "<password>" and confirm password "<confirmPassword>"
    And I click the register button
    Then I should be successfully registered

    Examples:
      | name        | email                     | password   | confirmPassword |
      | Arjun Korde | arjun.test14588@gmail.com   | Arjun@123  | Arjun@123       |