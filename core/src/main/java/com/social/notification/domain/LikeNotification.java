package com.social.notification.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

import java.time.Instant;
import java.util.List;

@Getter
@TypeAlias("LikeNotification")
@NoArgsConstructor
public class LikeNotification extends Notification{
    private Long postId;
    private List<Long> likerIds;

    public LikeNotification(
            String id,
            Long userId,
            NotificationType type,
            Instant occurredAt,
            Instant createdAt,
            Instant lastUpdatedAt,
            Instant deletedAt,
            Long postId,
            List<Long> likerIds
    ) {
        super(id, userId, type, occurredAt, createdAt, lastUpdatedAt, deletedAt);
        this.postId = postId;
        this.likerIds = likerIds;
    }

    public void addLiker(Long likerId, Instant occurredAt, Instant now, Instant retention) {
        this.likerIds.add(likerId);
        this.setOccurredAt(occurredAt);
        this.setLastUpdatedAt(now);
        this.setDeletedAt(retention);
    }

    public void removeLiker(Long likerId, Instant now) {
        this.likerIds.remove(likerId);
        this.setLastUpdatedAt(now);
    }

}
