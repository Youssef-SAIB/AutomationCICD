package WebAutomation.Tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import WebAutomation.TestComponents.BaseTest;
import WebAutomation.pageObjects.CartPage;
import WebAutomation.pageObjects.CheckoutPage;
import WebAutomation.pageObjects.OrderConfirmPage;
import WebAutomation.pageObjects.OrdersPage;
import WebAutomation.pageObjects.ProductCataloguePage;

public class SubmitOrderTest extends BaseTest {
	String countryName = "india";
	String confirmMessage = "THANKYOU FOR THE ORDER.";

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {
		ProductCataloguePage productCatalogue = loginPage.loginApplication(input.get("email"), input.get("password"));
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();
		boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry(countryName);
		OrderConfirmPage orderConfirmPage = checkoutPage.submitOrder();
		Assert.assertTrue(orderConfirmPage.getConfirmationDisplay().equalsIgnoreCase(confirmMessage));

	}

	// Create new Test method but this time with dependency to an other Test method
	// Requirement: Verify if 'orders' product list contains productName
	@Test(dependsOnMethods = { "submitOrder" })
	public void orderHistoryTest() {
		ProductCataloguePage productCatalogue = loginPage.loginApplication("Test2024@gmail.com", "Abc123456789");
		OrdersPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.verifyOrderDisplay("ADIDAS ORIGINAL"));

	}

//	Method3: create HashMap from Json file
	@DataProvider()
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\WebAutomation\\data\\PurchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

//	Method2: return HashMap
//	@DataProvider()
//	public Object[][] getData() {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email", "Test2024@gmail.com");
//		map.put("password", "Abc123456789");
//		map.put("product", "ADIDAS ORIGINAL");
//
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("email", "Test2030@gmail.com");
//		map1.put("password", "Abc1234567890");
//		map1.put("product", "ZARA COAT 3");
//
//		return new Object[][] { { map }, { map1 } };
//	}

//	Method1: return data object array
//	@DataProvider()
//	public Object[][] getData() {
//		return new Object[][] { { "Test2024@gmail.com", "Abc123456789", "ADIDAS ORIGINAL" },
//				{ "Test2030@gmail.com", "Abc1234567890", "ZARA COAT 3" } };
//	}

}

//Relative XPath:
//Use a dot . to indicate a search relative to the current context:
//prod.findElement(By.xpath(".//div[@class='card-body']/button[2]")).click();

//in explicitWait -> invisibilityOfElementLocated has a performance issue it take too long time to be done
//instead we can use -> invisibilityOf(driver.findElement(BY.cssSelector("....")) it's better

// to minimize code we can create new object of second page in the end of the first page, 
// if we are sure that this end of first page will always go to the second page, if not we should'nt use this method
