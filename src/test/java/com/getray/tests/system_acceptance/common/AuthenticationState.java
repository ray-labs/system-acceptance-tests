package com.getray.tests.system_acceptance.common;

import com.getray.tests.system_acceptance.configuration.CucumberTestConfiguration;
import com.getray.tests.system_acceptance.configuration.UserConfigurationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthenticationState {

    private final CucumberTestConfiguration testConfiguration;
    private final RestClient restClient;
    private String xSession;
    private UserConfigurationModel admin;


    @Autowired
    public AuthenticationState(CucumberTestConfiguration testConfiguration, RestClient restClient) {
        this.testConfiguration = testConfiguration;
        admin = testConfiguration.users().get("admin");
        this.restClient = restClient;
    }

    public String getAdminSession() {
        if (xSession != null) {
            return xSession;
        }
        ResponseEntity<String> response = sendAuthenticationRequest();
        xSession = response.getHeaders().getFirst("x-session");
        return xSession;
    }

    private ResponseEntity<String> sendAuthenticationRequest() {
        AuthenticationRequestBody requestBody =
                new AuthenticationRequestBody(admin.username(), admin.password());

        ResponseEntity<String> response = restClient.post()
                .uri(testConfiguration.legacyBackend() + "/cms/v1/auth/Login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toEntity(String.class);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new ResponseStatusException(response.getStatusCode(), response.getBody());
        }
        return response;
    }
}
