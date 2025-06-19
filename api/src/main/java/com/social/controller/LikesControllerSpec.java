package com.social.controller;

import com.social.controller.request.LikesRequest;
import com.social.controller.response.LikesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "좋아요 관련 API")
public interface LikesControllerSpec {

    @Operation(summary = "좋아요 추가 삭제")
    ResponseEntity<LikesResponse> like(
            LikesRequest likesRequest
    );
}
