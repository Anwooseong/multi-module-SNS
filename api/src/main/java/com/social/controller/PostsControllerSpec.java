package com.social.controller;

import com.social.controller.request.CreatePostsRequest;
import com.social.controller.response.CreatePostsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "게시글 관련 API")
public interface PostsControllerSpec {

    @Operation(summary = "게시글 등록")
    ResponseEntity<CreatePostsResponse> createBoard(CreatePostsRequest createPostsRequest);

}