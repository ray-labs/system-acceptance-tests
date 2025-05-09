package com.getray.tests.system_acceptance;

import com.getray.tests.system_acceptance.configuration.CucumberTestConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@EnableConfigurationProperties({CucumberTestConfiguration.class})
@Configuration
public class SystemAcceptanceTestsConfiguration {

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }


}
