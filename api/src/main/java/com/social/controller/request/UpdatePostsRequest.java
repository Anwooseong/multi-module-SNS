package com.social.controller.request;

import com.social.domain.Posts;
import com.social.domain.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdatePostsRequest {

    @NotNull
    @Schema(type = "String", description = "게시글 내용", requiredMode = Schema.RequiredMode.REQUIRED)
    private String caption;

    @NotNull
    @Schema(type = "List<String>", description = "게시글 이미지 배열", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> imageUrls;


    @Builder
    public UpdatePostsRequest(String caption, List<String> imageUrls) {
        this.caption = caption;
        this.imageUrls = imageUrls;
    }

    public Posts toPostsEntity(Users user) {
        return Posts.builder()
                .user(user)
                .caption(caption)
                .build();
    }

}
