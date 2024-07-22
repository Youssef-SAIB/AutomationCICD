package WebAutomation.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import WebAutomation.AbstractComponents.AbstractComponent;

public class LoginPage extends AbstractComponent {
	WebDriver driver;

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Elements Locator
	@FindBy(id = "userEmail")
	WebElement Email;

	@FindBy(id = "userPassword")
	WebElement Password;

	@FindBy(id = "login")
	WebElement submit;

	@FindBy(css = "[class*='toast-error']")
	WebElement errorMessage;

	// Action Methods
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");
	}

	public ProductCataloguePage loginApplication(String email, String password) {
		Email.sendKeys(email);
		Password.sendKeys(password);
		submit.click();
		return new ProductCataloguePage(driver);
	}

	public String getErrorMessage() {
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}

//	INFO!
//	instead of using :	 WebElement userEmail = driver.findElement(By.id("userEmail"));
//	we can use : 		 @FindBy(id="userEmail") 
//				 		 WebElement userEmail;
//	but we should call the method ".initElements(driver, this)" in the constructor
//	so the @FindBy annotation knows which driver will use to locate the elements

}
