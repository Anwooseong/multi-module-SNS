package com.social.util;

import com.social.domain.Users;

public class UserFixture {
    public static Users user(String username) {
        return Users.builder()
                .username(username)
                .email(username + "@test.com")
                .password("password")
                .build();
    }
}
