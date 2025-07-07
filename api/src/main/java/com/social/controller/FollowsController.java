package com.social.controller;

import com.social.controller.response.CreateFollowResponse;
import com.social.controller.request.CreateFollowRequest;
import com.social.controller.response.DeleteFollowResponse;
import com.social.service.FollowsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowsController implements FollowsControllerSpec {

    private final FollowsService followsService;

    @PostMapping
    public ResponseEntity<CreateFollowResponse> createFollow(
            @Valid @RequestBody CreateFollowRequest createFollowRequest
    ) {
        return ResponseEntity.ok(
                CreateFollowResponse.of(followsService.createFollow(createFollowRequest))
        );
    }

    @DeleteMapping
    public ResponseEntity<DeleteFollowResponse> deleteFollow(
            @RequestParam("toUserId") Long toUserId
    ) {
        return ResponseEntity.ok(
                DeleteFollowResponse.of(followsService.deleteFollow(toUserId))
        );
    }
}
