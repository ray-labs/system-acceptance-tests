package com.getray.tests.system_acceptance.common;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public record AuthenticationRequestBody(
        String username,
        String password
) {
}
