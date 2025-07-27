package com.social.util;

import com.social.domain.Posts;
import com.social.domain.Users;

public class PostFixture {
    public static Posts post(Users user, String caption) {
        return Posts.builder()
                .user(user)
                .caption(caption)
                .build();
    }
}
