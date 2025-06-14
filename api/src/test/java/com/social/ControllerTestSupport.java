package com.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.controller.AuthController;
import com.social.repository.UsersRepository;
import com.social.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles({"local", "jwt"})
@WebMvcTest(controllers = {
        AuthController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;


    @MockitoBean
    protected AuthService authService;

    @MockitoBean
    protected UsersRepository usersRepository;

    @MockitoBean
    protected PasswordEncoder passwordEncoder;


    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .defaultRequest(MockMvcRequestBuilders.post("/api/v1/auth/signup").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .build();
    }

}
