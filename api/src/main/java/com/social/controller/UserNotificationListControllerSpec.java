package com.social.controller;

import com.social.controller.response.UserNotificationListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

@Tag(name = "사용자 알림센터 API")
public interface UserNotificationListControllerSpec {

    @Operation(summary = "사용자 알림 목록 조회")
    ResponseEntity<UserNotificationListResponse> getNotifications(
            @Parameter(example = "1") long userId,
            @Parameter(example = "2024-01-01T00:11:22.382Z") Instant pivot
    );
}

