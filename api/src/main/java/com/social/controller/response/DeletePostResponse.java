package com.social.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeletePostResponse {

    @Schema(description = "삭제된 게시글 ID")
    private Long postId;

    public static DeletePostResponse of(Long postId) {
        return DeletePostResponse.builder()
                .postId(postId)
                .build();
    }

}
