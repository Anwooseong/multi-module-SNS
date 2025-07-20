package com.social.controller;

import com.social.controller.response.CheckNewNotificationResponse;
import com.social.service.CheckNewNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-notifications")
public class CheckNewNotificationController implements CheckNewNotificationControllerSpec{

    private final CheckNewNotificationService service;

    public CheckNewNotificationController(CheckNewNotificationService service) {
        this.service = service;
    }

    @Override
    @GetMapping("/{userId}/new")
    public ResponseEntity<CheckNewNotificationResponse> checkNewNotification(
            @PathVariable(value = "userId") long userId
    ) {
        boolean hasNew = service.checkNewNotification(userId);
        return ResponseEntity.ok(CheckNewNotificationResponse.of(hasNew));
    }
}
