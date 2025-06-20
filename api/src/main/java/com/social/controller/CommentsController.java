package com.social.controller;

import com.social.controller.request.CreateCommentsRequest;
import com.social.controller.response.CreateCommentsResponse;
import com.social.service.CommentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentsController implements CommentsControllerSpec{

    private final CommentsService commentsService;

    @PostMapping
    public ResponseEntity<CreateCommentsResponse> createComment(
            @Valid @RequestBody CreateCommentsRequest createCommentsRequest
    ) {
        return ResponseEntity.ok(
                CreateCommentsResponse.of(commentsService.createComment(createCommentsRequest))
        );
    }
}
