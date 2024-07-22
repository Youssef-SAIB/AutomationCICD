package Cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//To run Cucumber runner we should use TestNG or JUnit
@CucumberOptions(features = "src/test/java/Cucumber", glue = "WebAutomation.StepDefinitions", monochrome = true, tags = "@ErrorValidation", plugin = {
		"html:target/cucumber.html" })
public class TestNGTestRunner extends AbstractTestNGCucumberTests {

}
