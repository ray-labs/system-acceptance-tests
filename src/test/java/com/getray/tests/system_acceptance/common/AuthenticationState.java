package com.getray.tests.system_acceptance.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthenticationState {

    public static final String host = "https://api-test.getray.com";
    private String xSession;
    private final RestClient restClient;

    @Autowired
    public AuthenticationState(RestClient restClient) {
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
                new AuthenticationRequestBody("admin@getray.com", "1234562");

        ResponseEntity<String> response = restClient.post()
                .uri(host + "/cms/v1/auth/Login")
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
