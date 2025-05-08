package com.getray.tests.system_acceptance.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("legacy")
public record LegacyBackendConfiguration(
        String url
) {
}
