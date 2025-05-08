package com.getray.tests.system_acceptance.broadcast;

import com.getray.tests.system_acceptance.common.AuthenticationState;
import com.getray.tests.system_acceptance.broadcast.requestBody.BroadcastRequestBody;
import com.getray.tests.system_acceptance.broadcast.requestBody.TranslationList;
import io.cucumber.datatable.DataTable;
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

import static com.getray.tests.system_acceptance.common.AuthenticationState.host;
import static org.assertj.core.api.Assertions.assertThat;


@CucumberContextConfiguration
@SpringBootTest
public class BroadcastStepDefinition {
    private final RestClient restClient;
    private final BasicJsonTester jsonTester;
    private ResponseEntity<String> responseEntity;
    private final String xSession;
    private List<Integer> userIdList = new ArrayList<>();

    @Autowired
    public BroadcastStepDefinition(RestClient restClient, AuthenticationState authenticationState) {
        this.restClient = restClient;
        xSession = authenticationState.getAdminSession();
        jsonTester = new BasicJsonTester(getClass());

    }

//    @ParameterType(".*")
//    public List<Integer> intList(String input){
//
//    }

    @Given("users with ids:")
    public void setUserIdList(DataTable dataTable) {
        List<String> userIds = dataTable.asList();
        userIdList = userIds.stream()
                .map(Integer::parseInt)
                .toList();
    }

    @When("message {string} is sent")
    public void broadcastMessageToUsers(String message) {
        BroadcastRequestBody broadcastRequestBody = new BroadcastRequestBody(
                "1",
                "message",
                userIdList,
                0,
                new TranslationList(Map.of("message",message))
        );
        responseEntity = restClient
                .post()
                .uri(host + "/cms/v1/Bot/Broadcast")
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
