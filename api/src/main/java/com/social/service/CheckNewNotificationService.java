package com.social.service;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CheckNewNotificationService {

    private final NotificationGetService notificationGetService;
    private final NotificationLastReadAtService notificationLastReadAtService;

    public CheckNewNotificationService(
            NotificationGetService notificationGetService,
            NotificationLastReadAtService notificationLastReadAtService
    ) {
        this.notificationGetService = notificationGetService;
        this.notificationLastReadAtService = notificationLastReadAtService;
    }

    public boolean checkNewNotification(long userId) {
        Instant latestUpdatedAt = notificationGetService.getLatestUpdatedAt(userId);
        if (latestUpdatedAt == null) {
            return false;
        }

        Instant lastReadAt = notificationLastReadAtService.getLastReadAt(userId);
        if (lastReadAt == null) {
            return true;
        }

        return latestUpdatedAt.isAfter(lastReadAt);
    }
}
