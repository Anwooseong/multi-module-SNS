package com.social.controller.response;

import com.social.domain.Comments;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "게시글 댓글 삭제 응답")
public class DeleteCommentResponse {

    @Schema(description = "게시글 댓글 삭제된 ID")
    private Long commentId;

    public static DeleteCommentResponse of(Comments comment) {
        return DeleteCommentResponse.builder()
                .commentId(comment.getId())
                .build();
    }
}
