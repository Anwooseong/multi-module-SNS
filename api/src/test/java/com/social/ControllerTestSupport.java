package com.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.controller.AuthController;
import com.social.controller.PostsController;
import com.social.repository.PhotosRepository;
import com.social.repository.PostsRepository;
import com.social.repository.UsersRepository;
import com.social.service.AuthService;
import com.social.service.PostsService;
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

@ActiveProfiles({"test", "secret"})
@WebMvcTest(controllers = {
        AuthController.class,
        PostsController.class
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
    protected PostsService postsService;

    @MockitoBean
    protected PostsRepository postsRepository;

    @MockitoBean
    protected PhotosRepository photosRepository;


    @MockitoBean
    protected PasswordEncoder passwordEncoder;


    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .defaultRequest(MockMvcRequestBuilders.post("/api/v1/auth/signup").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .defaultRequest(MockMvcRequestBuilders.post("/api/v1/auth/login").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .defaultRequest(MockMvcRequestBuilders.post("/api/v1/posts").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .build();
    }

}
