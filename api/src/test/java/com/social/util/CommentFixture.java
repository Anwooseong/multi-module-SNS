package com.social.util;

import com.social.domain.Comments;
import com.social.domain.Posts;
import com.social.domain.Users;

public class CommentFixture {
    public static Comments comment(Users user, Posts post, String content) {
        return Comments.builder()
                .user(user)
                .post(post)
                .content(content)
                .build();
    }
}
