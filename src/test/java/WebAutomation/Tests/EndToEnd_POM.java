package WebAutomation.Tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import WebAutomation.TestComponents.BaseTest;
import WebAutomation.pageObjects.CartPage;
import WebAutomation.pageObjects.CheckoutPage;
import WebAutomation.pageObjects.LoginPage;
import WebAutomation.pageObjects.OrderConfirmPage;
import WebAutomation.pageObjects.ProductCataloguePage;

public class EndToEnd_POM extends BaseTest {

	@Test
	public void submitOrder() throws IOException, InterruptedException {
		WebDriver driver = initializeDriver();

		String userEmail = "Test2024@gmail.com";
		String password = "Abc123456789";
		String productName = "ADIDAS ORIGINAL";
		String countryName = "india";
		String confirmMessage = "THANKYOU FOR THE ORDER.";

		LoginPage loginPage = new LoginPage(driver);
		loginPage.goTo();
		ProductCataloguePage productCatalogue = loginPage.loginApplication(userEmail, password);
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry(countryName);
		OrderConfirmPage orderConfirmPage = checkoutPage.submitOrder();
		Assert.assertTrue(orderConfirmPage.getConfirmationDisplay().equalsIgnoreCase(confirmMessage));

		System.out.println("Test Passed");
		driver.close();

	}
}

//Relative XPath:
//Use a dot . to indicate a search relative to the current context:
//prod.findElement(By.xpath(".//div[@class='card-body']/button[2]")).click();

//in explicitWait -> invisibilityOfElementLocated has a performance issue it take too long time to be done
//instead we can use -> invisibilityOf(driver.findElement(BY.cssSelector("....")) it's better

// to minimize code we can create new object of second page in the end of the first page, 
// if we are sure that this end of first page will always go to the second page, if not we should'nt use this method
