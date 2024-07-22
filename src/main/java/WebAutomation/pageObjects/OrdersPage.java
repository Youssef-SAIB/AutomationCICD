package WebAutomation.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import WebAutomation.AbstractComponents.AbstractComponent;

public class OrdersPage extends AbstractComponent {

	WebDriver driver;

	public OrdersPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	@FindBy(xpath = "//tr/td[2]")
	List<WebElement> productNames;

	// Action Methods
	public boolean verifyOrderDisplay(String productName) {
		Boolean match = productNames.stream().anyMatch(s -> s.getText().equalsIgnoreCase(productName));
		return match;
	}

}
