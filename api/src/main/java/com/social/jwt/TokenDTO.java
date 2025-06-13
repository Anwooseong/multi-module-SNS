package com.social.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn; // Date.getTime() 결과는 long 타입입니다.
    private String refreshToken;

}