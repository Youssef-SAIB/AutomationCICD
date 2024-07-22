package WebAutomation.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import WebAutomation.AbstractComponents.AbstractComponent;

public class ProductCataloguePage extends AbstractComponent {
	WebDriver driver;

	public ProductCataloguePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Elements Locator
	@FindBy(css = ".mb-3")
	List<WebElement> products;

	@FindBy(css = ".ng-animating")
	WebElement spinner;

	By prods = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");

	// Action Methods
	public List<WebElement> getProductList() {
		waitForElementToAppear(prods);
		return products;
	}

	public WebElement getProductByName(String productName) {
		WebElement product = getProductList().stream()
				.filter(s -> s.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return product;
	}

	public void addProductToCart(String productName) throws InterruptedException {
		WebElement product = getProductByName(productName);
		product.findElement(addToCart).click();
		waitForElementToAppear(toastMessage);
		// the next explicit wait slow down the execution because of the hidden spinner
		// I will replace it by simple Thread(2000)
//		waitForElementToDisappear(spinner, 5);
		Thread.sleep(2000);
	}

//	INFO!
//	instead of using :	 WebElement userEmail = driver.findElement(By.id("userEmail"));
//	we can use : 		 @FindBy(id="userEmail") 
//				 		 WebElement userEmail;
//	but we should call the method ".initElements(driver, this)" in the constructor
//	so the @FindBy annotation knows which driver will use to locate the elements

}
