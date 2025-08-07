package com.social.controller;

import com.social.controller.request.CreateCommentsRequest;
import com.social.controller.response.CreateCommentsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "댓글 관련 API")
public interface CommentsControllerSpec {

    @Operation(summary = "댓글 생성")
    ResponseEntity<CreateCommentsResponse> createComment(
            @Parameter(example = "1") Long poseId,
            CreateCommentsRequest createCommentsRequest
    );
}
