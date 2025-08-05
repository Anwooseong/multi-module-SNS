package com.social.controller.response;

import com.social.domain.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePostsResponse {
    private Long postId;
    private String caption;

    public static UpdatePostsResponse of(Posts posts) {
        return UpdatePostsResponse.builder()
                .postId(posts.getId())
                .caption(posts.getCaption())
                .build();
    }

}
