package com.social.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Schema(description = "사용자 알림 목록 읽은 시간 기록 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetLastReadAtResponse {

    @Schema(description = "기록된 읽은 시간")
    private Instant lastReadAt;

    public static SetLastReadAtResponse of(Instant lastReadAt) {
        return SetLastReadAtResponse.builder()
                .lastReadAt(lastReadAt)
                .build();
    }
}
