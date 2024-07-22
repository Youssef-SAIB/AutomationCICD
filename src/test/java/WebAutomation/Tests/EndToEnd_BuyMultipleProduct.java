package WebAutomation.Tests;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EndToEnd_BuyMultipleProduct {

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
		List<String> productToBuy = Arrays.asList("ZARA COAT 3", "IPHONE 13 PRO");

// Req1: find all product belong to list productToBuy and click on it
		productToBuy.forEach(productName -> {
			products.stream().filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName))
					.findFirst()
					.ifPresent(s -> s.findElement(By.cssSelector(".card-body button:last-of-type")).click());
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
			wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		});

// Req 2: click on 'Cart' then check if products have been added successfully
		driver.findElement(By.cssSelector("[routerlink*='/dashboard/cart']")).click();
		List<String> productAddedToCart = driver.findElements(By.cssSelector(".cartSection h3")).stream()
				.map(s -> s.getText()).collect(Collectors.toList());
		Assert.assertTrue(productAddedToCart.equals(productToBuy));

// Req 3: click checkout button
		// Scroll until the button is view, to avoid Exception:element click intercepted

		// Solution1: scrool into view
//		WebElement checkoutBtn = driver.findElement(By.cssSelector(".totalRow button"));
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		js.executeScript("arguments[0].scrollIntoView(true);", checkoutBtn);
//		Thread.sleep(500);
//		checkoutBtn.click();

		// Solution2: use JavaScript click directly
		WebElement checkoutBtn = driver.findElement(By.cssSelector(".totalRow button"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", checkoutBtn);

// Req 4: Select Country
		// Scroll until textBox is view, to avoid Exception:element click intercepted
		WebElement selectCountryTxt = driver.findElement(By.cssSelector("[placeholder='Select Country']"));
		js.executeScript("arguments[0].scrollIntoView(true);", selectCountryTxt);
		Thread.sleep(500);
		Actions a = new Actions(driver);
		a.sendKeys(selectCountryTxt, "india").build().perform();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".ta-results"))));

		// cssSelector
//		driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();
		// xpath
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

//in Req3 -> I can't click on checkout button when use xpath locator 
//CSS Selectors are generally faster than XPath expressions because browsers are optimized for CSS queries.

//After scrolling we should always give a threap.sleep(XXXX) so we let the browser Scroll 
