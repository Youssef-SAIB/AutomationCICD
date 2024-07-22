package WebAutomation.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import WebAutomation.Resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {

	// Create ExtentReports object => to create and configure Report object
	ExtentReports extent = ExtentReporterNG.getReportObject();
	// Create ExtentTest object => to contain the report of a specific test
	ExtentTest testReport;
	// Create a ThreadLocal to separate the using of ExtentTest by methods(@Test)
	ThreadLocal<ExtentTest> testThread = new ThreadLocal<ExtentTest>(); // Thread safe

	@Override
	public void onTestStart(ITestResult result) {
//        System.out.println("Test started: " + result.getName());
//		'result' gives all information about the test related to this method(onTestStart)
//		extent.createTest => to create an instance of Report Object for a specific test
		testReport = extent.createTest(result.getMethod().getMethodName());
		testThread.set(testReport); // unique Thread id
	}

	@Override
	public void onTestSuccess(ITestResult result) {
//		System.out.println("Test passed: " + result.getName());
		testThread.get().log(Status.PASS, "Test success");
	}

	@Override
	public void onTestFailure(ITestResult result) {
//        System.out.println("Test failed: " + result.getName());
		// Print Exception
		testThread.get().fail(result.getThrowable());

		// Get driver used in a test where we want to apply getScreenShot
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		// Take a Screenshot | Attach to report
		String screenshotPath = null;
		try {
			screenshotPath = getScreenShot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testThread.get().addScreenCaptureFromPath(screenshotPath, result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
//    		System.out.println("Test skipped: " + result.getName());
		testThread.get().log(Status.SKIP, "Test skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
//        System.out.println("Test failed but within success percentage: " + result.getName());
	}

	@Override
	public void onStart(ITestContext context) {
//        System.out.println("Test started: " + context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
//        System.out.println("Test finished: " + context.getName());
		// Stop 'extent' from monitoring the test and save the index.html
		extent.flush();
	}
}
