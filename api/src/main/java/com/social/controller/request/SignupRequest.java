package com.social.controller.request;

import com.social.domain.Role;
import com.social.domain.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
public class SignupRequest {
    @NotNull
    @Schema(type = "String", description = "유저 이름", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @NotNull
    @Schema(type = "String", description = "유저 이메일", example = "test1234@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @NotNull
    @Schema(type = "String", description = "유저 비밀번호", example = "test1234@", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Builder
    public SignupRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Users toUsersEntity(PasswordEncoder passwordEncoder) {
        return Users.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.ROLE_USER)
                .build();
    }
}
