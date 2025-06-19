package com.social.controller.request;

import com.social.domain.Likes;
import com.social.domain.Posts;
import com.social.domain.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikesRequest {

    @NotNull
    @Schema(type = "Long", description = "게시글 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long postId;

    @Builder
    public LikesRequest(Long postId) {
        this.postId = postId;
    }

    public Likes toLikesEntity(Users user, Posts post) {
        return Likes.builder()
                .user(user)
                .post(post)
                .build();
    }
}
