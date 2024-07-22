package WebAutomation.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import WebAutomation.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent {

	WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Elements Locator
	@FindBy(css = "[placeholder='Select Country']")
	WebElement selectCountry;

	@FindBy(xpath = "(//button[contains(@class,'ta-item')])[2]")
	WebElement country;

	@FindBy(css = ".action__submit")
	WebElement submit;

	By suggestList = By.cssSelector(".ta-results");

	public void selectCountry(String countryName) {
		Actions a = new Actions(driver);
		a.sendKeys(selectCountry, countryName).build().perform();
		waitForElementToAppear(suggestList);
		country.click();
	}

	public OrderConfirmPage submitOrder() {
		submit.click();
		return new OrderConfirmPage(driver);

	}

}
