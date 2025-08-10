package com.social.repository.querydslDTO;

import com.social.domain.Comments;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GetCommentDTO {
    Long userId;
    String username;
    Long commentId;
    String content;
    LocalDateTime writeTime;

    public GetCommentDTO(Long userId, String username, Long commentId, String content, LocalDateTime writeTime) {
        this.userId = userId;
        this.username = username;
        this.commentId = commentId;
        this.content = content;
        this.writeTime = writeTime;
    }

    public GetCommentDTO(Comments comment) {
        if (comment.getUser() != null) {
            this.userId = comment.getUser().getId();
            this.username = comment.getUser().getUsername();
        }
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.writeTime = comment.getUpdateAt();
    }
}
