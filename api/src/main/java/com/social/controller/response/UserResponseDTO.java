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
    // 필요에 따라 다른 필드 추가 (예: profileImageUrl 등)

    // Users 엔티티로부터 UserResponseDTO 객체를 생성하는 정적 팩토리 메소드
    public static UserResponseDTO of(Users users) {
        return UserResponseDTO.builder()
                .id(users.getId())
                .username(users.getUsername())
                .email(users.getEmail())
                // 필요에 따라 다른 필드 매핑
                .build();
    }
}
