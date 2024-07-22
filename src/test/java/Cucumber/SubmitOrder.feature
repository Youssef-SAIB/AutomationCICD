
@tag
Feature: Purchase the order from Ecommerce website
  I want to use this template for my feature file

	Background:
	Given I landed to Ecommerce Page

  @Regression
  Scenario Outline: Positive Test of Submitting the order
    Given  Logged in with username <username> and password <password>
    When   I add product <productName> to Cart
    And    Checkout <productName> and submit the order
    Then   "THANKYOU FOR THE ORDER." message is displayed on Confirmation Page

    Examples: 
      | username             | password       | productName     |
      | Test2024@gmail.com   | Abc123456789   | ADIDAS ORIGINAL |
      | Test2030@gmail.com   | Abc1234567890  | ZARA COAT 3     |
      
