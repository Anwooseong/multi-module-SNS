package com.social.controller;

import com.social.controller.response.CheckNewNotificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "사용자 알림센터 API")
public interface CheckNewNotificationControllerSpec {

    @Operation(summary = "사용자 신규 알림 여부 조회")
    ResponseEntity<CheckNewNotificationResponse> checkNewNotification(
            @Parameter(example = "1") long userId
    );
}
