package com.social.controller;

import com.social.controller.request.CreateCommentsRequest;
import com.social.controller.response.CreateCommentsResponse;
import com.social.service.CommentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.social.util.SecurityUtil.getCurrentUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class CommentsController implements CommentsControllerSpec{

    private final CommentsService commentsService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CreateCommentsResponse> createComment(
            @PathVariable("postId") Long postId,
            @Valid @RequestBody CreateCommentsRequest createCommentsRequest
    ) {
        return ResponseEntity.ok(
                CreateCommentsResponse.of(commentsService.createComment(getCurrentUserId(), postId, createCommentsRequest))
        );
    }
}
