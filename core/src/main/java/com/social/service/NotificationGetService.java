package com.social.service;

import com.social.notification.domain.Notification;
import com.social.notification.domain.NotificationType;
import com.social.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationGetService {

    private final NotificationRepository notificationRepository;

    public Optional<Notification> getNotificationByTypeAndCommentId(NotificationType type, Long commentId) {
        return notificationRepository.findByTypeAndCommentId(type, commentId);
    }

    public Optional<Notification> getNotificationByTypeAndPostId(NotificationType type, Long postId) {
        return notificationRepository.findByTypeAndPostId(type, postId);
    }

    public Optional<Notification> getNotificationByTypeAndUserIdAndToUserId(NotificationType type, long userId, long toUserId) {
        return notificationRepository.findByTypeAndUserIdAndToUserId(type, userId, toUserId);
    }

    public Instant getLatestUpdatedAt(Long userId) {
        Optional<Notification> notification = notificationRepository.findFirstByUserIdOrderByLastUpdatedAtDesc(userId);

        return notification.map(Notification::getLastUpdatedAt).orElse(null);
    }
}
