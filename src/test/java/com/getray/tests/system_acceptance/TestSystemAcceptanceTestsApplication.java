package com.getray.tests.system_acceptance;

import org.springframework.boot.SpringApplication;

public class TestSystemAcceptanceTestsApplication {

    public static void main(String[] args) {
        SpringApplication.from(SystemAcceptanceTestsApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
