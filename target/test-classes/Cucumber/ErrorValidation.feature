
@tag
Feature: Error Validation
  I want to use this template for my feature file


  @ErrorValidation
  Scenario Outline: Positive Test of Error Validation
  	Given I landed to Ecommerce Page
    When  Logged in with username <username> and password <password>
    Then  "Incorrect email or password." message is displayed on Login Page

    Examples: 
      | username             | password       | 
      | Test2024@gmail.com   | ABB123456789   | 
      | Test2030@gmail.com   | ABB1234567890  | 