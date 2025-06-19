package com.social.controller;

import com.social.controller.request.LikesRequest;
import com.social.controller.response.LikesResponse;
import com.social.service.LikesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class LikesController implements LikesControllerSpec{

    private final LikesService likesService;

    @PostMapping
    public ResponseEntity<LikesResponse> like(@Valid @RequestBody LikesRequest likesRequest) {
        return ResponseEntity.ok(LikesResponse.of(likesService.like(likesRequest)));
    }
}
