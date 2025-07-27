package com.social.controller;

import com.social.controller.request.CreatePostsRequest;
import com.social.controller.response.CreatePostsResponse;
import com.social.controller.response.SliceResponse;
import com.social.service.PostsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostsController implements PostsControllerSpec{

    private final PostsService postsService;

    @PostMapping
    public ResponseEntity<CreatePostsResponse> createBoard(@Valid @RequestBody CreatePostsRequest createPostsRequest) {
        return ResponseEntity.ok(CreatePostsResponse.of(postsService.createPost(createPostsRequest)));
    }

    @GetMapping
    public ResponseEntity<SliceResponse<?>> getFeed() {

        return null;
    }

}
