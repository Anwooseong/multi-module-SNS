package com.social.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotNull
    @Schema(type = "String", description = "유저 이메일", example = "test1234@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @NotNull
    @Schema(type = "String", description = "유저 비밀번호", example = "test1234@", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}

