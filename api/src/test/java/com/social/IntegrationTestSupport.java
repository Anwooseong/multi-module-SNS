package com.social;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test", "secret"})
@SpringBootTest
public abstract class IntegrationTestSupport {
}
