package com.social.task;

import com.social.notification.domain.LikeNotification;
import com.social.notification.domain.Notification;
import com.social.notification.domain.NotificationType;
import com.social.event.LikeEvent;
import com.social.service.NotificationGetService;
import com.social.service.NotificationRemoveService;
import com.social.service.NotificationSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeRemoveTask {

    private final NotificationGetService notificationGetService;
    private final NotificationRemoveService notificationRemoveService;
    private final NotificationSaveService notificationSaveService;

    public void processEvent(LikeEvent event) {
        Optional<Notification> optionalNotification = notificationGetService.getNotificationByTypeAndPostId(NotificationType.LIKE, event.getPostId());
        if (optionalNotification.isEmpty()) {
            log.error("No notification with postId:{}", event.getPostId());
        }

        // likers 에서 event.userId 제거 1. likers 가 비었으면 삭제 2. 남아있으면 알림 업데이트
        LikeNotification notification = (LikeNotification) optionalNotification.get();
        removeLikerAndNotification(event, notification);
    }

    private void removeLikerAndNotification(LikeEvent event, LikeNotification notification) {
        notification.removeLiker(event.getUserId(), Instant.now());

        if (notification.getLikerIds().isEmpty()) {
            notificationRemoveService.deleteById(notification.getId());
        } else {
            notificationSaveService.upsert(notification);
        }
    }
}
