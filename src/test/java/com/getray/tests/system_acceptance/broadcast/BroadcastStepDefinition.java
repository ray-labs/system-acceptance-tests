package com.getray.tests.system_acceptance.broadcast;

import com.getray.tests.system_acceptance.common.AuthenticationState;
import com.getray.tests.system_acceptance.broadcast.requestBody.BroadcastRequestBody;
import com.getray.tests.system_acceptance.broadcast.requestBody.TranslationList;
import com.getray.tests.system_acceptance.configuration.LegacyBackendConfiguration;
import com.getray.tests.system_acceptance.configuration.UserConfiguration;
import com.getray.tests.system_acceptance.configuration.UserConfigurationModel;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@CucumberContextConfiguration
@SpringBootTest
public class BroadcastStepDefinition {
    private final RestClient restClient;
    private final UserConfiguration userConfiguration;
    private final BasicJsonTester jsonTester;
    private final String xSession;
    private final LegacyBackendConfiguration backendConfiguration;
    private ResponseEntity<String> responseEntity;
    private List<Integer> userIds = new ArrayList<>();


    @Autowired
    public BroadcastStepDefinition(RestClient restClient, AuthenticationState authenticationState, LegacyBackendConfiguration backendConfiguration,
                                   UserConfiguration userConfiguration) {
        this.restClient = restClient;
        this.userConfiguration = userConfiguration;
        xSession = authenticationState.getAdminSession();
        this.backendConfiguration = backendConfiguration;
        jsonTester = new BasicJsonTester(getClass());

    }

    @ParameterType(".*")
    public UserConfigurationModel user(String userName) {
        return userConfiguration.user().get(userName);
    }

    @DataTableType
    public UserConfigurationModel userTable(String userName) {
        return userConfiguration.user().get(userName);
    }

    @Given("users:")
    public void setUserIdList(List<UserConfigurationModel> users) {
        userIds = users.stream().map(UserConfigurationModel::id).toList();
    }

    @When("broadcast message {string} is sent")
    public void broadcastMessageToUsers(String message) {
        BroadcastRequestBody broadcastRequestBody = new BroadcastRequestBody(
                "1",
                "message",
                userIds,
                0,
                new TranslationList(Map.of("message", message))
        );
        responseEntity = restClient
                .post()
                .uri(backendConfiguration.url() + "/cms/v1/Bot/Broadcast")
                .header("x-session", xSession)
                .contentType(MediaType.APPLICATION_JSON)
                .body(broadcastRequestBody)
                .retrieve()
                .toEntity(String.class);
    }

    @Then("broadcast recieved")
    public void broadCastRecieved() {
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        JsonContent<Object> jsonContent = jsonTester.from(responseEntity.getBody());
        assertThat(jsonContent).extractingJsonPathStringValue("$.meta.status").isEqualTo("OK");
        assertThat(jsonContent).extractingJsonPathStringValue("$.response.status").isEqualTo("OK");

    }
}
