package com.getray.tests.system_acceptance.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;


@ConfigurationProperties("cucumber")
public record CucumberTestConfiguration(
        String legacyBackend,
        Map<String, UserConfigurationModel> users,
        Map<String, DomainConfigurationModel> domains
) {
}
