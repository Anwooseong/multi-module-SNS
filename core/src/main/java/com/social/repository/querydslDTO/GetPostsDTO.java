package com.social.repository.querydslDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GetPostsDTO {
    Long postId;
    String caption;
    Boolean liked;
    Long commentCnt;
    LocalDateTime createAt;
    String thumbnailUrl;

    public GetPostsDTO(Long postId, String caption, Boolean liked, Long commentCnt, LocalDateTime createAt, String thumbnailUrl) {
        this.postId = postId;
        this.caption = caption;
        this.liked = liked;
        this.commentCnt = commentCnt;
        this.createAt = createAt;
        this.thumbnailUrl = thumbnailUrl;
    }
}
