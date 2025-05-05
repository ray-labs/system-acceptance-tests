package com.getray.tests.system_acceptance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class SystemAcceptanceTestsConfiguration {

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }


}
