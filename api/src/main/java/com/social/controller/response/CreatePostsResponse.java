package com.social.controller.response;

import com.social.domain.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostsResponse {
    private Long postId;

    public static CreatePostsResponse of(Posts post) {
        return CreatePostsResponse.builder()
                .postId(post.getId())
                .build();
    }

}