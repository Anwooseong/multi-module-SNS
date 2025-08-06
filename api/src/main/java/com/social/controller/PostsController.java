package com.social.controller;

import com.social.controller.request.CreatePostsRequest;
import com.social.controller.request.UpdatePostsRequest;
import com.social.controller.response.CreatePostsResponse;
import com.social.controller.response.DeletePostResponse;
import com.social.controller.response.SliceResponse;
import com.social.controller.response.UpdatePostsResponse;
import com.social.repository.querydslDTO.GetPostsDTO;
import com.social.service.PostsService;
import com.social.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.social.util.SecurityUtil.getCurrentUserId;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostsController implements PostsControllerSpec {

    private final PostsService postsService;

    @Override
    @PostMapping
    public ResponseEntity<CreatePostsResponse> createBoard(@Valid @RequestBody CreatePostsRequest createPostsRequest) {
        return ResponseEntity.ok(CreatePostsResponse.of(postsService.createPost(createPostsRequest)));
    }

    @Override
    @GetMapping
    public ResponseEntity<SliceResponse<GetPostsDTO>> getFeed(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(postsService.getFeed(SecurityUtil.getCurrentUserId(), PageRequest.of(page, size)));
    }

    @Override
    @PutMapping("/{postId}")
    public ResponseEntity<UpdatePostsResponse> updateBoard(@PathVariable("postId") Long postId, @Valid @RequestBody UpdatePostsRequest updatePostsRequest) {
        return ResponseEntity.ok(UpdatePostsResponse.of(postsService.updatePost(getCurrentUserId(), postId, updatePostsRequest)));
    }

    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<DeletePostResponse> deleteBoard(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(DeletePostResponse.of(postsService.deletePost(getCurrentUserId(), postId)));
    }
}
