package com.social.service;

import com.social.IntegrationTestSupport;
import com.social.controller.request.SignupRequest;
import com.social.controller.response.SignupResponse;
import com.social.domain.Role;
import com.social.domain.Users;
import com.social.repository.RefreshTokenRepository;
import com.social.repository.UsersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AuthServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @AfterEach
    void tearDown() {
        usersRepository.deleteAllInBatch();
        refreshTokenRepository.deleteAllInBatch();
    }

    @DisplayName("회원가입 성공")
    @Test
    void signup_success() {
        // given
        String username = "홍길동";
        String email = "test1234@gmail.com";
        String password = "test123@";
        String profileImageUrl = "홍길동.jpg";
        SignupRequest dto = SignupRequest.builder()
                .username(username)
                .email(email)
                .password(password)
                .profileImageUrl(profileImageUrl)
                .build();

        // when
        SignupResponse result = authService.signup(dto);
        Users findUser = usersRepository.findByEmail(email).get();

        // then
        Assertions.assertThat(result.getUsername()).isEqualTo(username);
        Assertions.assertThat(result.getEmail()).isEqualTo(email);
        Assertions.assertThat(findUser.getUsername()).isEqualTo(username);
        Assertions.assertThat(findUser.getEmail()).isEqualTo(email);
    }

    @DisplayName("기존유저 회원가입 시도시 예외처리")
    @Test
    void signup_existingUser() {
        // given
        String username = "홍길동";
        String email = "test1234@gmail.com";
        String password = "test123@";
        String profileImageUrl = "홍길동.jpg";
        SignupRequest dto = SignupRequest.builder()
                .username(username)
                .email(email)
                .password(password)
                .profileImageUrl(profileImageUrl)
                .build();

        Users user = Users.builder()
                .username(username)
                .email(email)
                .password(password)
                .profileImageUrl(profileImageUrl)
                .role(Role.ROLE_USER)
                .build();
        usersRepository.save(user);


        // when
        // then
        Assertions.assertThatThrownBy(() -> authService.signup(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("이미 가입되어 있는 유저입니다");
    }

}