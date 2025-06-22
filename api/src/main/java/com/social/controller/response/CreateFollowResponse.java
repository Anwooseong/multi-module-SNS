package com.social.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFollowResponse {

    private Long followId;

    public static CreateFollowResponse of(Long followId) {
        return CreateFollowResponse.builder()
                .followId(followId)
                .build();
    }
}
