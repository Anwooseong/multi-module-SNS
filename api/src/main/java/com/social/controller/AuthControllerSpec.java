package com.social.controller;

import com.social.controller.request.LoginRequest;
import com.social.controller.request.SignupRequest;
import com.social.controller.request.TokenRequest;
import com.social.controller.response.LoginResponse;
import com.social.controller.response.SignupResponse;
import com.social.jwt.TokenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "유저 회원가입, 회원 정보 수정, 회원 정보 찾기 API")
public interface AuthControllerSpec {

    @Operation(summary = "유저 회원 가입")
    ResponseEntity<SignupResponse> signup(
            SignupRequest signupRequest
    );

    @Operation(summary = "유저 로그인")
    ResponseEntity<LoginResponse> login(
            LoginRequest loginRequest
    );

    @Operation(summary = "유저 토큰 재발급")
    ResponseEntity<TokenDTO> token(
            TokenRequest tokenRequest
    );
}
