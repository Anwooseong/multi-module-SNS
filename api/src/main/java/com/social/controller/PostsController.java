package com.social.controller;

import com.social.controller.request.CreatePostsRequest;
import com.social.service.PostsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostsController implements PostsControllerSpec{

    private final PostsService postsService;

    @PostMapping
    public ResponseEntity<Long> createBoard(@Valid @RequestBody CreatePostsRequest createPostsRequest) {
        return ResponseEntity.ok(postsService.createPost(createPostsRequest));
    }
}
