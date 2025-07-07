package com.social.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteFollowResponse {

    private Long followId;

    public static DeleteFollowResponse of(Long followId) {
        return DeleteFollowResponse.builder()
                .followId(followId)
                .build();
    }
}
