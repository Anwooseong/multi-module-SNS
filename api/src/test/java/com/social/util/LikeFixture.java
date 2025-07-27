package com.social.util;

import com.social.domain.Likes;
import com.social.domain.Posts;
import com.social.domain.Users;

public class LikeFixture {
    public static Likes like(Users user, Posts post) {
        return Likes.builder()
                .user(user)
                .post(post)
                .build();
    }
}
