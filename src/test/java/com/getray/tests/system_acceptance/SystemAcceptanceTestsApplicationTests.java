package com.getray.tests.system_acceptance;

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
