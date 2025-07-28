package com.social.controller;

import com.social.controller.request.CreatePostsRequest;
import com.social.controller.response.CreatePostsResponse;
import com.social.controller.response.SliceResponse;
import com.social.repository.querydslDTO.GetPostsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "게시글 관련 API")
public interface PostsControllerSpec {

    @Operation(summary = "게시글 등록")
    ResponseEntity<CreatePostsResponse> createBoard(CreatePostsRequest createPostsRequest);

    @Operation(summary = "게시글 조회")
    ResponseEntity<SliceResponse<GetPostsDTO>> getFeed(
            @Parameter(example = "0") int page,
            @Parameter(example = "2") int size
    );
}