package com.getray.interview.system_acceptance_tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class SystemAcceptanceTestsApplicationTests {

    @Test
    void contextLoads() {
    }

}
