package com.social.controller;

import com.social.controller.response.UserNotificationListResponse;
import com.social.service.GetUserNotificationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user-notifications")
public class UserNotificationListController implements UserNotificationListControllerSpec {

    private final GetUserNotificationsService getUserNotificationsService;

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<UserNotificationListResponse> getNotifications(
            @PathVariable(value = "userId") long userId,
            @RequestParam(value = "pivot", required = false) Instant pivot
    ) {
        return ResponseEntity.ok(UserNotificationListResponse.of(
                getUserNotificationsService.getUserNotificationByPivot(userId, pivot)
        ));
    }
}