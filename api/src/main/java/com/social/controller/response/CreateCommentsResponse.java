package com.social.controller.response;

import com.social.domain.Comments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCommentsResponse {

    private Long commentId;

    public static CreateCommentsResponse of(Comments comment) {
        return CreateCommentsResponse.builder()
                .commentId(comment.getId())
                .build();
    }
}
