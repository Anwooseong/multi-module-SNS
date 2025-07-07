package com.social.controller;

import com.social.controller.request.CreateFollowRequest;
import com.social.controller.response.CreateFollowResponse;
import com.social.controller.response.DeleteFollowResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "팔로우 관련 API")
public interface FollowsControllerSpec {

    @Operation(summary = "팔로우 추가")
    ResponseEntity<CreateFollowResponse> createFollow(
            CreateFollowRequest createFollowRequest
    );

    @Operation(summary = "팔로우 취소")
    ResponseEntity<DeleteFollowResponse> deleteFollow(
            Long toUserId
    );
}
