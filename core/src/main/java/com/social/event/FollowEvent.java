package com.social.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowEvent {
    private FollowEventType type;
    private Long userId;
    private Long targetUserId;
    private Instant createdAt;
}
