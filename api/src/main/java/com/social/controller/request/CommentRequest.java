package com.social.controller.request;

import com.social.domain.Comments;
import com.social.domain.Posts;
import com.social.domain.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequest {

    @NotNull
    @Schema(type = "String", description = "댓글 내용", example = "감성 좋네요!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Builder
    public CommentRequest(Long postId, String content) {
        this.content = content;
    }

    public Comments toCommentsEntity(Users user, Posts post) {
        return Comments.builder()
                .user(user)
                .post(post)
                .content(content)
                .build();
    }
}
