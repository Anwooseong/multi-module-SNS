package com.social.controller;

import com.social.controller.request.SignupRequestDTO;
import com.social.controller.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "유저 회원가입, 회원 정보 수정, 회원 정보 찾기 API")
public interface AuthControllerSpec {

    @Operation(summary = "유저 회원 가입")
    ResponseEntity<UserResponseDTO> signup(
            SignupRequestDTO signupRequestDTO
    );
}
