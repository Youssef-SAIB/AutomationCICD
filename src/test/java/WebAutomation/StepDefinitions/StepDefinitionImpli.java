package WebAutomation.StepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import WebAutomation.TestComponents.BaseTest;
import WebAutomation.pageObjects.CartPage;
import WebAutomation.pageObjects.CheckoutPage;
import WebAutomation.pageObjects.LoginPage;
import WebAutomation.pageObjects.OrderConfirmPage;
import WebAutomation.pageObjects.ProductCataloguePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImpli extends BaseTest {

	LoginPage loginPage;
	ProductCataloguePage productCatalogue;
	CartPage cartPage;
	CheckoutPage checkoutPage;
	OrderConfirmPage orderConfirmPage;

	@Given("I landed to Ecommerce Page")
	public void I_landed_to_Ecommerce_Page() throws IOException {
		loginPage = launchApplication();
//		loginPage.loginApplication(null, null)

	}

	// To use regular expression (.+) we shoyld add ^$ to the string
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_username_and_password(String username, String password) throws IOException {
		productCatalogue = loginPage.loginApplication(username, password);

	}

	@When("^I add product (.+) to Cart$")
	public void i_add_product_to_cart(String productName) throws InterruptedException {
		productCatalogue.addProductToCart(productName);
	}

	@When("^Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName) throws InterruptedException {
		CartPage cartPage = productCatalogue.goToCartPage();
		boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		orderConfirmPage = checkoutPage.submitOrder();
	}

	@Then("{string} message is displayed on Confirmation Page")
	public void confirmation_message(String string) {
		Assert.assertTrue(orderConfirmPage.getConfirmationDisplay().equalsIgnoreCase(string));
		driver.close();
	}

	@Then("{string} message is displayed on Login Page")
	public void error_message(String string) {
		Assert.assertEquals(loginPage.getErrorMessage(), string);
		driver.close();
	}

}
