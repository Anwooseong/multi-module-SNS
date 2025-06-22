package com.social.controller;

import com.social.controller.response.CreateFollowResponse;
import com.social.controller.request.CreateFollowRequest;
import com.social.service.FollowsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowsController implements FollowsControllerSpec{

    private final FollowsService followsService;

    @PostMapping
    public ResponseEntity<CreateFollowResponse> createFollow(
            @Valid @RequestBody CreateFollowRequest createFollowRequest
    ) {
        return ResponseEntity.ok(
                CreateFollowResponse.of(followsService.createFollow(createFollowRequest))
        );
    }
}
