package WebAutomation.Tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import WebAutomation.TestComponents.BaseTest;
import WebAutomation.TestComponents.Retry;
import WebAutomation.pageObjects.CartPage;
import WebAutomation.pageObjects.ProductCataloguePage;

public class ErrorValidationTest extends BaseTest {

	@Test(retryAnalyzer = Retry.class)
	public void loginErrorValidation() {
		String userEmail = "Test2024@gmail.com";
		String wrongPassword = "AAABBBCCC";
//		String errorMessage = "Incorrect email or password."; 
		String errorMessage = "Incorrect email or password.XX"; // cause Test fail
		loginPage.loginApplication(userEmail, wrongPassword);
		Assert.assertEquals(loginPage.getErrorMessage(), errorMessage);
	}

	@Test(groups = { "ErrorHandling" }, retryAnalyzer = Retry.class)
	public void addProductErrorValidation() throws IOException, InterruptedException {
		String userEmail = "Test2030@gmail.com";
		String password = "Abc1234567890";
		String productName = "ADIDAS ORIGINAL";

		ProductCataloguePage productCatalogue = loginPage.loginApplication(userEmail, password);
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		boolean match = cartPage.verifyProductDisplay("ZARA COAT 3");
//		boolean match = cartPage.verifyProductDisplay("ADIDAS ORIGINAL");  // cause Test fail
		Assert.assertFalse(match);
	}

}
