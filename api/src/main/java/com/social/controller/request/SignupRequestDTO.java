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
public class SignupRequestDTO {
    @NotNull
    @Schema(type = "String", description = "유저 이름", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @NotNull
    @Schema(type = "String", description = "유저 이메일", example = "test1234@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @NotNull
    @Schema(type = "String", description = "유저 비밀번호", example = "test1234@", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    @Schema(type = "String", description = "유저 프로필 사진", example = "홍길동.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String profileImageUrl;

    @Builder
    public SignupRequestDTO(String username, String email, String password, String profileImageUrl) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
    }

    public Users toUsersEntity(PasswordEncoder passwordEncoder) {
        return Users.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .profileImageUrl(profileImageUrl)
                .role(Role.ROLE_USER)
                .build();
    }
}
