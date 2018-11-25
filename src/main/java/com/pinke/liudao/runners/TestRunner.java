package com.pinke.liudao.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = {"classpath:features"},
        glue={"com.pinke.liudao.steps"},
        plugin = {"pretty", "html:target/cucumber-report/html", "json:target/cucumber-report/api-test-report.json"},
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
