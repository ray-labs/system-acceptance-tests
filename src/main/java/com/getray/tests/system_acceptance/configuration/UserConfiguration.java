package com.getray.tests.system_acceptance.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("users")
public record UserConfiguration(
        Map<String, UserConfigurationModel> user
) {

}
