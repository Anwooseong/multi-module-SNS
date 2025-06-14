package com.social;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"local", "jwt"})
@SpringBootTest
public abstract class IntegrationTestSupport {
}
