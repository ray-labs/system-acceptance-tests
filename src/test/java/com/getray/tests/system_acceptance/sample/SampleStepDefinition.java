//package com.getray.tests.system_acceptance.sample;
//
//import com.jayway.jsonpath.JsonPath;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.json.BasicJsonTester;
//import org.springframework.boot.test.json.JsonContent;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestClient;
//
//import java.util.Base64;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class SampleStepDefinition {
//
//    private final RestClient restClient;
//    private final BasicJsonTester jsonTester;
//    private ResponseEntity<String> responseEntity;
//    private String token;
//    private String username;
//    private String password;
//
//    @Autowired
//    public SampleStepDefinition(
//            RestClient restClient
//    ) {
//        this.restClient = restClient;
//        jsonTester = new BasicJsonTester(getClass());
//    }
//
//    @When("calls an API")
//    public void callApi() {
//        responseEntity = restClient.get()
//                .uri("https://httpbin.org/get")
//                .retrieve()
//                .toEntity(String.class);
//    }
//
//    @Then("should be working")
//    public void test() {
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        JsonContent<Object> jsonContent = jsonTester.from(responseEntity.getBody());
//        assertThat(jsonContent).extractingJsonPathStringValue("$.legacyBackend").isEqualTo("https://httpbin.org/get");
//    }
//
//    //bearer authentication with captured token
//
//    @When("send bearer {string}")
//    public void sendToken(String token) {
//        responseEntity = restClient.post()
//                .uri("https://httpbin.org/anything")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body("{\"token\": \"" + token + "\"}")
//                .retrieve()
//                .toEntity(String.class);
//    }
//
//    @Then("capture token")
//    public void captureToken() {
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        token = JsonPath.read(responseEntity.getBody(), "$.json.token");
//    }
//
//    @When("authenticating with captured token")
//    public void authenticatedWithCapturedToken() {
//        responseEntity = restClient.get()
//                .uri("https://httpbin.org/bearer")
//                .header("Authorization","Bearer "+token)
//                .retrieve()
//                .toEntity(String.class);
//    }
//
//    @Then("successful bearer authentication")
//    public void successfulBearerAuthentication() {
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//
//
//    @Given("username {string} and password {string}")
//    public void storeCredentials(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//    // basic authentication
//    @When("authenticate with credentials")
//    public void authenticate() {
//        String creds = username + ":" + password;
//        String encodedCreds = Base64.getEncoder().encodeToString(creds.getBytes());
//        responseEntity = restClient.get()
//                .uri("http://httpbin.org/basic-auth/user/passwd")
//                .header("Authorization","Basic "+encodedCreds)
//                .retrieve()
//                .toEntity(String.class);
//    }
//
//    @Then("successful basic authentication")
//    public void successfulBasicAuthentication() {
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        JsonContent<Object> jsonContent = jsonTester.from(responseEntity.getBody());
//        assertThat(jsonContent).extractingJsonPathStringValue("$.user").isEqualTo("user");
//    }
//}
