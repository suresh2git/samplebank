package com.demo.accounts.controller.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/account-api-controller-cucumber.json"},
        features = "src/test/resources/features/account-controller-integration.feature",
        glue = "com.demo.accounts.controller"
)
public class AccountApiControllerCucumberRunnerTest {

}
