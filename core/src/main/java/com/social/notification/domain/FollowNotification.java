package com.social.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("FollowNotification")
public class FollowNotification extends Notification {
    private Long toUserId;

    public FollowNotification(
            String id,
            Long userId,
            NotificationType type,
            Instant occurredAt,
            Instant createdAt,
            Instant lastUpdatedAt,
            Instant deletedAt,
            Long toUserId
    ) {
        super(id, userId, type, occurredAt, createdAt, lastUpdatedAt, deletedAt);
        this.toUserId = toUserId;
    }
}
