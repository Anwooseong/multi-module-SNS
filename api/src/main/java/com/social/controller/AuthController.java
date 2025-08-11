package com.social.controller;

import com.social.controller.request.LoginRequest;
import com.social.controller.request.SignupRequest;
import com.social.controller.request.TokenRequest;
import com.social.controller.response.LoginResponse;
import com.social.controller.response.SignupResponse;
import com.social.jwt.TokenDTO;
import com.social.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController implements AuthControllerSpec {

    private final AuthService authService;

    @Override
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
            @Valid @RequestBody SignupRequest signupRequest
    ) {
        return ResponseEntity.ok(authService.signup(signupRequest));
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/token")
    public ResponseEntity<TokenDTO> token(
            @RequestBody TokenRequest tokenRequest
    ) {
        return ResponseEntity.ok(authService.reissue(tokenRequest));
    }
}

