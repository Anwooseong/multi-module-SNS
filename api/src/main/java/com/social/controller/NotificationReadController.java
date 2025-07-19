package com.social.controller;

import com.social.controller.response.SetLastReadAtResponse;
import com.social.service.NotificationLastReadAtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/user-notifications")
public class NotificationReadController implements NotificationReadControllerSpec{

    private final NotificationLastReadAtService service;

    public NotificationReadController(NotificationLastReadAtService service) {
        this.service = service;
    }

    @PutMapping("/{userId}/read")
    public ResponseEntity<SetLastReadAtResponse> setLastReadAt(
            @PathVariable(value = "userId") long userId
    ) {
        Instant lastReadAt = service.setLastReadAt(userId);
        return ResponseEntity.ok(SetLastReadAtResponse.of(lastReadAt));
    }
}
