package runners;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(glue = { "stepDefinitions" }, features = "src/test/resources/features/", tags = "@Regression")
public class TestRunner extends TestNGParallelScenarioTesting {

}