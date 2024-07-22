package WebAutomation.Tests;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EndToEnd_BuyOneProduct {

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");

		driver.findElement(By.id("userEmail")).sendKeys("Test2024@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Abc123456789");
		driver.findElement(By.id("login")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		String productName = "ADIDAS ORIGINAL";

		// Req1: find product with title 'ADIDAS ORIGINAL' and click on it
		// cssSelector
		WebElement productToBuy = products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);
		productToBuy.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		// xpath
//		List<WebElement> products =	driver.findElements(By.xpath("//div[contains(@class,'mb-3')]"));
//		WebElement productToBuy = products.stream()
//				.filter(product -> product.findElement(By.xpath(".//b")).getText().equals("ADIDAS ORIGINAL"))
//				.findFirst().orElse(null);
//		productToBuy.findElement(By.xpath(".//div[@class='card-body']/button[2]")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

		// Req 2: click on 'Cart' then check if product have been added successfully
		driver.findElement(By.cssSelector("[routerlink*='/dashboard/cart']")).click();
		List<WebElement> productAddedToCart = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = productAddedToCart.stream().anyMatch(s -> s.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);

		driver.findElement(By.xpath("//*[@class='totalRow']/button")).click();

		// Req 4: Select Country
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".ta-results"))));
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();

		driver.findElement(By.cssSelector(".action__submit")).click();
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

		System.out.println("Test Passed");

	}
}

//Relative XPath:
//Use a dot . to indicate a search relative to the current context:
//prod.findElement(By.xpath(".//div[@class='card-body']/button[2]")).click();

//in explicitWait -> invisibilityOfElementLocated has a performance issue it take too long time to be done
//instead we can use -> invisibilityOf(driver.findElement(BY.cssSelector("....")) it's better
