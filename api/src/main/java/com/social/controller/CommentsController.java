package com.social.controller;

import com.social.controller.request.CommentRequest;
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
public class CommentsController implements CommentsControllerSpec {

    private final CommentsService commentsService;

    @Override
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CreateCommentsResponse> createComment(
            @PathVariable("postId") Long postId,
            @Valid @RequestBody CommentRequest commentRequest
    ) {
        return ResponseEntity.ok(
                CreateCommentsResponse.of(commentsService.createComment(getCurrentUserId(), postId, commentRequest))
        );
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CreateCommentsResponse> modifyComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody CommentRequest commentRequest
    ) {
        return ResponseEntity.ok(
                CreateCommentsResponse.of(commentsService.modifyComment(getCurrentUserId(), postId, commentId, commentRequest))
        );
    }
}
