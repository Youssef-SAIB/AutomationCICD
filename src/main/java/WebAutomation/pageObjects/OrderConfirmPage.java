package WebAutomation.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import WebAutomation.AbstractComponents.AbstractComponent;

public class OrderConfirmPage extends AbstractComponent {
	WebDriver driver;

	public OrderConfirmPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Elements Locator
	@FindBy(css = ".hero-primary")
	WebElement confirmMessage;

	// Action Methods
	public String getConfirmationDisplay() {
		return confirmMessage.getText();

	}
}
