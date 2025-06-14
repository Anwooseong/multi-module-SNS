package com.social.controller.response;

import com.social.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;

    public static UserResponseDTO of(Users users) {
        return UserResponseDTO.builder()
                .id(users.getId())
                .username(users.getUsername())
                .email(users.getEmail())
                .build();
    }
}
