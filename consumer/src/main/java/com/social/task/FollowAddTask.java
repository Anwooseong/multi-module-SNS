package com.social.task;

import com.social.notification.domain.FollowNotification;
import com.social.notification.domain.NotificationType;
import com.social.event.FollowEvent;
import com.social.service.NotificationSaveService;
import com.social.utils.NotificationIdGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class FollowAddTask {

    private final NotificationSaveService notificationSaveService;

    public void processEvent(FollowEvent event) {
        notificationSaveService.insert(createFollowNotification(event));
    }

    @NotNull
    private static FollowNotification createFollowNotification(FollowEvent event) {
        Instant now = Instant.now();
        Instant retention = now.plus(90, ChronoUnit.DAYS);

        return new FollowNotification(
                NotificationIdGenerator.generate(),
                event.getUserId(),
                NotificationType.FOLLOW,
                event.getCreatedAt(),
                now,
                now,
                retention,
                event.getToUserId()
        );
    }
}
