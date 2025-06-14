package com.social.controller;

import com.social.ControllerTestSupport;
import com.social.controller.request.SignupRequestDTO;
import com.social.controller.response.UserResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class AuthControllerTest extends ControllerTestSupport {

    @DisplayName("회원가입 성공")
    @Test
    @WithMockUser
    void signup_success() throws Exception{
        // given
        String username = "홍길동";
        String email = "test1234@gmail.com";
        String password = "test123@";
        String profileImageUrl = "홍길동.jpg";
        SignupRequestDTO request = SignupRequestDTO.builder()
                .username(username)
                .email(email)
                .password(password)
                .profileImageUrl(profileImageUrl)
                .build();

        // when
        BDDMockito.when(authService.signup(ArgumentMatchers.any()))
                .thenReturn(UserResponseDTO.builder()
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
}