package com.getray.tests.system_acceptance.sample;

import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration
public class CucumberSampleConfiguration {

    @When("runs a test")
    public void test() throws Throwable {
        System.out.println("test success");
    }

}
