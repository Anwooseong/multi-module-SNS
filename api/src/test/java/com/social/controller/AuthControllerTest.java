package com.social.controller;

import com.social.ControllerTestSupport;
import com.social.controller.request.LoginRequest;
import com.social.controller.request.SignupRequest;
import com.social.controller.response.LoginResponse;
import com.social.controller.response.SignupResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;

class AuthControllerTest extends ControllerTestSupport {

    @DisplayName("회원가입 성공")
    @Test
    @WithMockUser
    void signup_success() throws Exception{
        // given
        String username = "홍길동";
        String email = "test1234@gmail.com";
        String password = "test123@";
        SignupRequest request = SignupRequest.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();

        // when
        BDDMockito.when(authService.signup(any()))
                .thenReturn(SignupResponse.builder()
                        .id(1L)
                        .username(username)
                        .email(email)
                        .build());

        // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/auth/signup")
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email));

    }

    @DisplayName("로그인 성공")
    @Test
    @WithMockUser
    void login_success() throws Exception{
        // given
        String username = "홍길동";
        String email = "test1234@gmail.com";
        String password = "test123@";
        LoginRequest request = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        // when
        BDDMockito.when(authService.login(any()))
                .thenReturn(LoginResponse.builder()
                        .accessToken("accessToken")
                        .refreshToken("refreshToken")
                        .build());

        // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/auth/login")
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").isString());
    }
}