package com.getray.tests.system_acceptance.configuration;


public record UserConfigurationModel(
        Integer id,
        String username,
        String password
) {
}
