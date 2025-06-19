package com.social.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesResponse {
    private Long likeId;

    public static LikesResponse of(Long likeId){
        return LikesResponse.builder()
                .likeId(likeId)
                .build();
    }
}
