package com.getray.tests.system_acceptance.sample;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

@CucumberContextConfiguration
@SpringBootTest
public class CucumberSampleConfiguration {

    private final RestClient restClient;
    private final BasicJsonTester jsonTester;
    private ResponseEntity<String> responseEntity;

    @Autowired
    public CucumberSampleConfiguration(
            RestClient restClient
    ) {
        this.restClient = restClient;
        jsonTester = new BasicJsonTester(getClass());
    }

    @When("calls an API")
    public void callApi() {
        responseEntity = restClient.get()
                .uri("https://httpbin.org/get")
                .retrieve()
                .toEntity(String.class);
    }

    @Then("should be working")
    public void test() {
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        JsonContent<Object> jsonContent = jsonTester.from(responseEntity.getBody());
        assertThat(jsonContent).extractingJsonPathStringValue("$.url").isEqualTo("https://httpbin.org/get");
    }

}
