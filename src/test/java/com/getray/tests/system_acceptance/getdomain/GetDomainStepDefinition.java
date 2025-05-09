package com.getray.tests.system_acceptance.getdomain;

import com.getray.tests.system_acceptance.common.AuthenticationState;
import com.getray.tests.system_acceptance.configuration.LegacyBackendConfiguration;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GetDomainStepDefinition {
    private final RestClient restClient;
    private final LegacyBackendConfiguration backendConfiguration;
    private final String xSession;
    private final BasicJsonTester jsonTester = new BasicJsonTester(getClass());
    private ResponseEntity<String> responseEntity;
    private int domainId;


    @Autowired
    public GetDomainStepDefinition(RestClient restClient, LegacyBackendConfiguration backendConfiguration, AuthenticationState authenticationState) {
        this.restClient = restClient;
        this.backendConfiguration = backendConfiguration;
        xSession = authenticationState.getAdminSession();
    }

    @When("get domain with id {int}")
    public void getDomainWithId(int domainId) {
        this.domainId = domainId;
        String uri = UriComponentsBuilder
                .fromUriString(backendConfiguration.url())
                .path("/cms/v1/Domains/GetDomain")
                .queryParam("domain_id", domainId)
                .toUriString();

        responseEntity = restClient.get()
                .uri(uri)
                .header("x-session", xSession)
                .retrieve()
                .toEntity(String.class);
    }

    @Then("domain is returned")
    public void domainIsValid() {
        JsonContent<Object> jsonContent = jsonTester.from(responseEntity.getBody());
        assertThat(jsonContent).extractingJsonPathStringValue("$.response.status").isEqualTo("OK");
        assertThat(jsonContent).extractingJsonPathNumberValue("$.response.Domain.id").isEqualTo(domainId);
    }

    @Then("domain is null")
    public void domainIsNull() {
        JsonContent<Object> jsonContent = jsonTester.from(responseEntity.getBody());
        assertThat(jsonContent).extractingJsonPathStringValue("$.response.status").isEqualTo("OK");
        assertThat(jsonContent).extractingJsonPathStringValue("$.response.Domain").isNull();
    }
}
