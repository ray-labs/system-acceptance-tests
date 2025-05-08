package com.getray.tests.system_acceptance.common;

import com.getray.tests.system_acceptance.configuration.LegacyBackendConfiguration;
import com.getray.tests.system_acceptance.configuration.UserConfiguration;
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

    private final LegacyBackendConfiguration backendConfiguration;
    private final RestClient restClient;
    private String xSession;
    private UserConfigurationModel admin;


    @Autowired
    public AuthenticationState(LegacyBackendConfiguration backendConfiguration, UserConfiguration userConfiguration, RestClient restClient) {
        this.backendConfiguration = backendConfiguration;
        admin = userConfiguration.user().get("admin");
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
                .uri(backendConfiguration.url() + "/cms/v1/auth/Login")
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
