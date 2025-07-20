package com.social.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "사용자 신규 알림 여부 조회 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckNewNotificationResponse {
    @Schema(description = "신규 알림 존재 여부")
    private Boolean hasNew;

    public static CheckNewNotificationResponse of(boolean hasNew) {
        return CheckNewNotificationResponse.builder()
                .hasNew(hasNew)
                .build();
    }
}
